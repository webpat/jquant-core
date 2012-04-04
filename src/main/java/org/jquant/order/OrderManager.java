package org.jquant.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;
import org.jquant.data.MarketManager;
import org.jquant.model.InstrumentId;
import org.jquant.order.Order.OrderSide;
import org.jquant.portfolio.Portfolio;
import org.jquant.portfolio.PortfolioException;
import org.jquant.portfolio.Trade;
import org.jquant.portfolio.Trade.TradeSide;
import org.jquant.serie.Candle;
import org.jquant.strategy.IStrategy;
import org.springframework.beans.factory.annotation.Autowired;

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
	
	

	
	public OrderManager(){
		
	}
	
	public OrderManager(double slippage, double flatFee, double sizeFee) {
		super();
		this.slippage = slippage;
		this.flatFee = flatFee;
		this.sizeFee = sizeFee;
	}

	@Override
	public Order sendOrder(Order order) {
		
		if (order.getQuantity()>0){

			if (order instanceof MarketOrder){
				marketOrder(order);
			}else if (order instanceof LimitOrder){
				limitOrder((LimitOrder)order);
				
			}else if (order instanceof StopOrder){
				stopOrder((StopOrder)order);
			}
			
		}
		return order;
	}
	
	private void marketOrder(Order order){
		/*
		 * We fetch the candle that was received by the time the order was created 
		 */
		Candle last = mktManager.getCandle(order.getInstrument(),order.getCreationTime()); //TODO : A dégager
		
		/*
		 * Order is transmitted to the Execution Provider 
		 * Next step is OnOrderFilled  
		 * TODO: externalize in the Simulation Execution Provider (PaperBroker) and call with this as a Callback  
		 */
		order.setFilledPrice(last.getClose());//TODO : CLOSE, LOW, HIGH , OPEN
		order.setFilledQuantity(order.getQuantity());
		order.setStatus(OrderStatus.FILLED);
		order.setExecutionTime(last.getDate());// TODO Devient un paramètre de l'ExecutionProvider
		
		
		onOrderFilled(order);
	}
	
	private void limitOrder(LimitOrder order){
		
	}
	
	private void stopOrder(StopOrder order){
		
	}
	
	
	

	@Override
	public void onCandle(InstrumentId instrument, Candle candle) {
		// TODO Auto-generated method stub
		
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
			
			logger.info(order.getSide() + "Order Filled for "+ order.getFilledQuantity()+ " of " + instrument.getCode() + " @ " + order.getFilledPrice()  + " on date " + order.getExecutionTime().toString("dd/MM/yyyy"));
			
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
