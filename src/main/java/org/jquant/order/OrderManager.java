package org.jquant.order;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.jquant.data.MarketManager;
import org.jquant.model.InstrumentId;
import org.jquant.order.Order.OrderSide;
import org.jquant.portfolio.Portfolio;
import org.jquant.portfolio.PortfolioException;
import org.jquant.portfolio.Trade;
import org.jquant.portfolio.Trade.TradeSide;
import org.jquant.serie.Candle;
import org.jquant.serie.Candle.CandleData;
import org.jquant.strategy.IStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OrderManager is the component that:
 * <ul>
 * <li>receives orders from the strategy layer</li>
 * <li>store delayed or conditional orders in a pending list</li>
 * <li>sends order to the execution provider</li>
 * <li>receive callbacks from the execution layer</li>
 * 
 * </ul> 
 * <p>
 * TODO :
 * <ul> 
 * <li> Subscribtion to market manager for pending orders's instruments </li>
 * <li> Delayed(Next Candle) Market Orders,  Limit, Trailing stop and stop orders</li>
 * <li> Order Cancellation</li>
 * </ul>
 * 
 * @author patrick.merheb
 */
@Service
public class OrderManager implements IOrderManager {

	/** logger */
	private static final Logger logger = Logger.getLogger(OrderManager.class);
	
	private Portfolio ptf;
	
	private final List<IStrategy> strategies = new ArrayList<IStrategy>();
	
	
	@Autowired
	private MarketManager mktManager;
	
	
	/*
	 * TODO: To move into ExecutionProvider
	 */
	private double slippage;
	
	private double flatFee;
	
	private double sizeFee;
	
	
	private final Queue<Order> pendingOrders = new ConcurrentLinkedQueue<Order>();
	
	private final List<InstrumentId> instruments = new LinkedList<InstrumentId>();
	
	

	
	public OrderManager(){
		
	}
	
	public OrderManager(double slippage, double flatFee, double sizeFee) {
		super();
		this.slippage = slippage;
		this.flatFee = flatFee;
		this.sizeFee = sizeFee;
	}

	/**
	 * Main Method (entry point) 
	 * The OrderManager receives an Order, process the order in the order queue 
	 */
	@Override
	public Order sendOrder(Order order) {
		
		if (order.getQuantity()>0){
			//Add the instrument in the perimeter 
			instruments.add(order.getInstrument());
			
			//Add the order in the 
			pendingOrders.add(order);
		}
		return order;
	}
	
	
	
	
	

	@Override
	public void onCandle(InstrumentId instrument, Candle candle) {
	
		if (pendingOrders.size()>0 && instruments.contains(instrument)){
			/* I Have a candle for an instrument i 
			 * What are the pending orders concerning i ? 
			 * Do they triggers ? 
			 * If yes send the order to the BrokerManager (PaperBroker) (pour l'instant in OrderFilled) 
			 */

			for (Order o : pendingOrders){

				if (o.getInstrument().equals(instrument)){
					if (o instanceof MarketOrder){
						processMarketOrder((MarketOrder) o,candle);
					}else if (o instanceof LimitOrder){
						processLimitOrder((LimitOrder)o,candle);
					}else if (o instanceof StopOrder){
						processStopOrder((StopOrder)o,candle);
					}else if (o instanceof TrailingStopOrder){
						processTrailingStopOrder((TrailingStopOrder)o,candle);
					}
				}
			}
		}
		
	}
	
	@Override
	public void onCandleOpen(InstrumentId instrument, Candle candle){
		/**
		 * Process OPEN MARKET orders only 
		 */
		for (Order o : pendingOrders){
			if (o.getInstrument().equals(instrument)){
				if (o instanceof MarketOrder){
					if (CandleData.OPEN.equals(((MarketOrder) o).getOhlc())){
					processMarketOrder((MarketOrder) o,candle);
					}
				}
			}
		}
	}

	private void processTrailingStopOrder(TrailingStopOrder o, Candle candle) {
		
		// Init trigger if needed  
		if (o.getTrigger() == 0){
			o.refresh(candle.getClose());
		}		
		
		//Test if the stop is triggered
		if (OrderSide.SELL.equals(o.getSide())){
			if (candle.getLow()<o.getTrigger()){
				double execPrice = candle.getHigh()<o.getTrigger()?candle.getOpen():o.getTrigger();
				popAndSendOrder(o, execPrice, candle.getDate());
			}
		}else {
			if (candle.getHigh()>o.getTrigger()){
				double execPrice = candle.getLow()>o.getTrigger()?candle.getOpen():o.getTrigger();
				popAndSendOrder(o, execPrice, candle.getDate());
			}
		}
		
		if (OrderSide.SELL.equals(o.getSide()) && candle.getClose()>o.getWatermark()){
			o.refresh(candle.getClose());
			o.setWatermark(candle.getClose());
		}
		
		if (OrderSide.BUY.equals(o.getSide()) && candle.getClose()<o.getWatermark()){
			o.refresh(candle.getClose());
			o.setWatermark(candle.getClose());
		}
		
		
	}

	private void processStopOrder(StopOrder o, Candle candle) {
		if (OrderSide.SELL.equals(o.getSide())){
			// Stop Loss
			if (candle.getLow()<o.getStopPrice()){
				// Is there a gap or not 
				double execPrice = candle.getHigh()<o.getStopPrice()?candle.getOpen():o.getStopPrice();
				// trigger order 
				popAndSendOrder(o,execPrice,  candle.getDate());
			}
		}else {
			// Stop loss on short pos
			if (candle.getHigh()>o.getStopPrice()){
				// Is there a gap or not 
				double execPrice = candle.getLow()>o.getStopPrice()?candle.getOpen():o.getStopPrice();
				popAndSendOrder(o,execPrice,  candle.getDate());
				
			}
		}
		
	}

	

	private void processLimitOrder(LimitOrder o, Candle candle) {
		if (OrderSide.SELL.equals(o.getSide())){
			// SELL Limit 
			if (candle.getHigh()>o.getLimitPrice()){
				// Is there a gap or not 
				double execPrice = candle.getLow()>o.getLimitPrice()?candle.getOpen():o.getLimitPrice();
				// trigger order 
				popAndSendOrder(o,execPrice,  candle.getDate());
			}
		}else {
			// BUY Limit
			if (candle.getLow()<o.getLimitPrice()){
				// Is there a gap or not 
				double execPrice = candle.getHigh()<o.getLimitPrice()?candle.getOpen():o.getLimitPrice();
				popAndSendOrder(o,execPrice,  candle.getDate());
				
			}
		}
		
	}

	private void processMarketOrder(MarketOrder o, Candle candle) {
		
		// Remove order from queue 
		pendingOrders.remove(o);
		// Remove instrument from perimeter
		instruments.remove(o.getInstrument());
		
		CandleData data = CandleData.CLOSE;
		
		if (o.getOhlc()!= null){
			data = o.getOhlc();
		}
		double execPrice = candle.getData(data);
		sendMarketOrderToBroker(o, execPrice,candle.getDate());
		
	}

	/**
	 * Remove the order from the Queue
	 * <p>
	 * Remove the instrument from the {@link OrderManager} scope 
	 * <p>
	 * Send the order to the Broker 
	 * @param o the {@link Order}
	 * @param execPrice The price you wish the order to be executed
	 * @param timestamp SIMULATION MODE ONLY 
	 */
	private void popAndSendOrder(Order o, double execPrice, DateTime timestamp) {
		// Remove order from queue 
		pendingOrders.remove(o);
		// Remove instrument from perimeter
		instruments.remove(o.getInstrument());
		
		//trigger order 
		sendMarketOrderToBroker(o, execPrice,timestamp);
	}
	
	
	/**
	 * Send to Broker Manager For the time being we mock a PaperBroker
	 * <p>
	 * TODO : Externalize to PaperBrokerManager
	 * @param o The {@link Order}
	 * @param candle the {@link Candle}
	 */
	private void sendMarketOrderToBroker(Order o, double executionPrice, DateTime replayTime) {

		o.setFilledPrice(executionPrice);//TODO : CLOSE, LOW, HIGH , OPEN
		o.setFilledQuantity(o.getQuantity());
		o.setStatus(OrderStatus.FILLED);
		o.setExecutionTime(replayTime);
		
		// Callback
		onOrderFilled(o);
	}

	public void onOrderFilled(Order order) {
		
		/*
		 * We build the Portfolio Trade 
		 */
		TradeSide side = OrderSide.BUY.equals(order.getSide())?TradeSide.BUY:TradeSide.SELL;
		double dealPrice = order.getFilledPrice(); 
		double amount = order.getQuantity() * dealPrice;
		Trade tr = new Trade(side, order.getInstrument(), order.getQuantity(),amount, order.getExecutionTime());
		InstrumentId instrument = tr.getInstrument();
		boolean openingPosition = false ;
		// Is it an opening position on Instrument i 
		if (ptf.getPosition(instrument)==0){
			openingPosition = true;
		}
		/*
		 * Add the trade to the portfolio 
		 */
		try {
			ptf.addTransaction(tr);
			
			logger.info(order.getSide() + " order filled for quantity "+ order.getFilledQuantity()+ " of " + instrument.getCode() + " @ " + order.getFilledPrice()  + " on date " + order.getExecutionTime().toString("dd/MM/yyyy"));
			
			/*
			 * Tells the strategy position has opened  
			 */
			for (IStrategy s: strategies){
				if (s.getMarket().contains(instrument)){
					if (openingPosition){
						s.onPositionOpened(tr.getSide(),instrument);
					}
				}
			}
			
		} catch (PortfolioException e) {

			logger.warn("Can not update Portfolio: " +e.getMessage());
		}
		
		
	}

	

	@Override
	public void onOrderCancelled(Order order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelOrder(Order order) {

	}

	public Portfolio getPtf() {
		return ptf;
	}

	public void setPtf(Portfolio ptf) {
		this.ptf = ptf;
	}

	public MarketManager getMktManager() {
		return mktManager;
	}

	public void setMktManager(MarketManager mktManager) {
		this.mktManager = mktManager;
	}

	public double getSlippage() {
		return slippage;
	}

	public void setSlippage(double slippage) {
		this.slippage = slippage;
	}

	public double getFlatFee() {
		return flatFee;
	}

	public void setFlatFee(double flatFee) {
		this.flatFee = flatFee;
	}

	public double getSizeFee() {
		return sizeFee;
	}

	public void setSizeFee(double sizeFee) {
		this.sizeFee = sizeFee;
	}

	@Override
	public void setPortfolio(Portfolio ptf) {
		this.ptf = ptf;
		
	}

	@Override
	public void addStrategy(IStrategy strategy) {
		strategies.add(strategy);
		
	}
	

}
