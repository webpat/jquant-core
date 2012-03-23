package org.jquant.execution;

import org.apache.log4j.Logger;
import org.jquant.data.MarketManager;
import org.jquant.order.Order;
import org.jquant.order.Order.OrderSide;
import org.jquant.order.OrderStatus;
import org.jquant.portfolio.Portfolio;
import org.jquant.portfolio.PortfolioException;
import org.jquant.portfolio.Trade;
import org.jquant.portfolio.Trade.TradeSide;
import org.jquant.serie.Candle;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author patrick.merheb
 * TODO : Time of Execution for a market Order ? current candle ? next candle ? worst case scenario? 
 *
 */
public class PaperBroker implements IBroker {

	/** logger */
	private static final Logger logger = Logger.getLogger(PaperBroker.class);
	
	private Portfolio ptf;
	
	
	@Autowired
	private MarketManager mktManager;
	
	private double slippage;
	
	private double flatFee;
	
	private double sizeFee;
	
	

	
	public PaperBroker(){
		
	}
	
	public PaperBroker(double slippage, double flatFee, double sizeFee) {
		super();
		this.slippage = slippage;
		this.flatFee = flatFee;
		this.sizeFee = sizeFee;
	}

	@Override
	public void sendOrder(Order order) {
		
		Candle last = mktManager.getCandle(order.getInstrument().getId(),order.getCreationTime());
		
		TradeSide side = OrderSide.BUY.equals(order.getSide())?TradeSide.BUY:TradeSide.SELL;
		double dealPrice = last.getClose(); //TODO : CLOSE, LOW, HIGH , OPEN
		double amount = order.getQuantity() * dealPrice;
		Trade tr = new Trade(side, order.getInstrument(), order.getQuantity(),amount, last.getDate());
		order.setFilledPrice(last.getClose());
		order.setFilledQuantity(order.getQuantity());
		order.setStatus(OrderStatus.FILLED);
		
		try {
			ptf.addTransaction(tr);
		} catch (PortfolioException e) {
			
			logger.warn("Can not update Portfolio: " +e.getMessage());
		}
		

	}

	@Override
	public OrderStatus getOrderStatus(Order order) {
		return null;
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
	public void onOrderFilled(Order filledOrder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOrderPartiallyFilled(Order filledOrder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOrderCancelled(Order cancelledOrder) {
		// TODO Auto-generated method stub
		
	}
	
	

}
