package org.jquant.strategy;

import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.jquant.model.InstrumentId;
import org.jquant.model.MarketDataPrecision;
import org.jquant.order.IOrderManager;
import org.jquant.order.LimitOrder;
import org.jquant.order.MarketOrder;
import org.jquant.order.Order;
import org.jquant.order.Order.OrderSide;
import org.jquant.order.StopOrder;
import org.jquant.order.TrailingStopOrder;
import org.jquant.portfolio.Portfolio;
import org.jquant.portfolio.Trade.TradeSide;
import org.jquant.serie.Candle;
import org.jquant.serie.Candle.CandleData;

/**
 * Base Class for a Strategy
 * <p>
 *  The Strategies adds the instrument to the market Manager with the {@link #initMarket()} method. 
 * 
 * <p>
 * @author patrick.merheb
 *
 */
public abstract class AbstractStrategy implements IStrategy {

	
	/** logger */
	protected static final Logger logger = Logger.getLogger("org.jquant.strategy");
	
	
	private MarketDataPrecision frequency;
	
	/**
	 * Internal tick time of the strategy
	 */
	private DateTime now;
	
	/**
	 * The strategy universe
	 */
	private List<InstrumentId> market;
	
	
	/**
	 * The {@link IOrderManager}
	 */
	protected IOrderManager orderManager;
	
	
	/**
	 * The strategy portfolio 
	 */
	protected Portfolio portfolio;
	
		
	/**
	 * L'identifiant de la stratégie
	 */
	private String id;
	
	


	@Override 
	public void onPositionOpened(TradeSide side, InstrumentId instrument){
		logger.debug("Position opened for Instrument " + instrument );
	}
	
	@Override
	public void onCandleOpen(InstrumentId instrument, Candle candle){
		logger.debug("Candle opened for Instrument " + instrument );
	}
	
	@Override
	public void onCandle(InstrumentId instrument, Candle candle){
		logger.debug("Candle closed for Instrument " + instrument );
	}
	
	/**
	 *  ex : CANDLE TRADE or QUOTE
	 * @return The Strategy {@link MarketDataPrecision}  
	 */
	protected MarketDataPrecision getFrequency() {
		return frequency;
	}



	/**
	 * Identifiant de la stratégie
	 * @return unique dans le contexte applicatif
	 */
	protected String getId() {
		return id;
	}

	/**
	 * 
	 * @param id The Id of the Strategy 
	 */
	protected void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Return <code>true</code> If It Has an open position on this instrument
	 * <code>false</code> otherwise 
	 * @param instrument tn {@link InstrumentId}
	 * @return boolean
	 */
	protected boolean hasPosition(InstrumentId instrument){
		return portfolio.getPosition(instrument)!=0;
	}
	
	

	/**
	 * Convenience method : sends a Market Order to the OrderManager
	 * @param instrument  The asset you want to trade {@link InstrumentId} 
	 * @param side {@link OrderSide} BUY or SELL 
	 * @param qty the qty you want to trade
	 * @param data
	 * <ul>
	 * <li>if <code>CandleData.OPEN </code> is used then the order is executed at next day Opening (Next Day Open Market Order)</li> 
	 * <li>if <code>CandleData.CLOSE </code> is used then the order is executed at the end of Today (EOD Market Order)</li>
	 * </ul> 
	 * @param text comments on the order 
	 * @return The submitted {@link Order}
	 */
	protected Order sendMarketOrder(InstrumentId instrument, OrderSide side,double qty,CandleData data, String text){
		
		MarketOrder marketOrder = new MarketOrder(side, instrument, qty, data,text,now);
		orderManager.sendOrder(marketOrder);
		return marketOrder;
		
	}
	
	/**
	 * Convenience method : sends a Limit Order to the OrderManager
	 * @param instrument The asset you want to trade {@link InstrumentId} 
	 * @param side {@link OrderSide} BUY or SELL 
	 * @param qty the quantity you want to trade
	 * @param price Limit price 
	 * @param text comments on the order 
	 * @return The submitted {@link Order}
	 */
	protected Order sendLimitOrder(InstrumentId instrument, OrderSide side,double qty,double price,String text){
		

		LimitOrder limitOrder = new LimitOrder(side, instrument, qty,price, text,now);
		orderManager.sendOrder(limitOrder);
		return limitOrder;
		
	}
	
	/**
	 * Convenience method : sends a Stop Order to the OrderManager
	 * @param instrument The asset you want to trade {@link InstrumentId} 
	 * @param side {@link OrderSide} BUY or SELL 
	 * @param qty the quantity you want to trade
	 * @param price STOP price
	 * @param text comments on the order 
	 * @return The submitted {@link Order}
	 */
	protected Order sendStopOrder(InstrumentId instrument, OrderSide side,double qty,double price,String text){
		
		StopOrder stopOrder = new StopOrder(side, instrument, qty,price, text, now);
		orderManager.sendOrder(stopOrder);
		return stopOrder;
		
	}
	
	/**
	 * Convenience method : sends a Trailing Stop Order to the OrderManager
	 * @param instrument The asset you want to trade {@link InstrumentId} 
	 * @param side {@link OrderSide} BUY or SELL 
	 * @param qty the quantity you want to trade
	 * @param text comments on the order 
	 * @return The submitted {@link Order}
	 */
	protected Order sendTrailingStopOrder(InstrumentId instrument, OrderSide side,double qty, double percentage,String text){
		
		TrailingStopOrder stopOrder = new TrailingStopOrder(side, instrument, percentage,qty, text,now);
		orderManager.sendOrder(stopOrder);
		return stopOrder;
		
	}
	


	protected void setOrderManager(IOrderManager execProvider) {
		this.orderManager = execProvider;
		
	}


	protected void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}
	
	

	/**
	 * Returns the current trading system time 
	 * @return {@link DateTime} 
	 */
	protected DateTime getNow() {
		return now;
	}

	protected void setNow(DateTime now) {
		this.now = now;
	}

	@Override
	public List<InstrumentId> getMarket(){
		return market;
	}
	
	
	
}
