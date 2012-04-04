package org.jquant.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jquant.model.InstrumentId;
import org.jquant.model.MarketDataPrecision;
import org.jquant.order.IOrderManager;
import org.jquant.order.LimitOrder;
import org.jquant.order.MarketOrder;
import org.jquant.order.Order;
import org.jquant.order.Order.OrderSide;
import org.jquant.order.StopOrder;
import org.jquant.portfolio.Portfolio;
import org.jquant.portfolio.Trade.TradeSide;
import org.jquant.serie.Candle;
import org.jquant.serie.CandleSerie;

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
	 * Reference to a Map of Growing CandleSeries 
	 */
	private Map<InstrumentId, CandleSerie> candleSeries;
	
	/**
	 * The {@link IOrderManager}
	 */
	private IOrderManager orderManager;
	
	
	/**
	 * The strategy portfolio 
	 */
	protected Portfolio portfolio;
	
		
	/**
	 * L'identifiant de la stratégie
	 */
	private String id;
	
	/**
	 * {@link #addInstrument(InstrumentId)}
	 */
	private final List<InstrumentId> market = new ArrayList<InstrumentId>();

	@Override
	abstract public void initMarket();
		
	@Override 
	public void onPositionOpened(TradeSide side, InstrumentId instrumentId){
		logger.info("Position opened for Instrument " + instrumentId );
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
	 * Add an instrument in the strategy market 
	 * @param symbol {@link InstrumentId}
	 */
	protected void addInstrument(InstrumentId symbol){
		market.add(symbol);
	}

	/**
	 * @return The Strategy market
	 */
	public List<InstrumentId> getMarket() {
		return market;
	}
	
	
	
	/**
	 * Return the candleSerie for the given InstrumentId 
	 * @param symbol a InstrumentId
	 * @return a {@link CandleSerie}
	 */
	protected CandleSerie getCandleSerie(InstrumentId symbol){
		return candleSeries.get(symbol);
	}
	
	protected void setCandleSerieMap(Map<InstrumentId,CandleSerie> map){
		this.candleSeries = map;
	}


	/**
	 * 
	 * @param instrument
	 * @param side
	 * @param qty
	 * @param text
	 * @return The submitted {@link Order}
	 */
	protected Order sendMarketOrder(InstrumentId instrument, OrderSide side,double qty,String text){
		
		MarketOrder marketOrder;
		CandleSerie cs = candleSeries.get(instrument);
		if (cs != null){
			// Simulation Order 
			Candle last = cs.getLast();
			marketOrder = new MarketOrder(side, instrument, qty, text,last.getDate());
		}else {
			// Live Order 
			marketOrder = new MarketOrder(side, instrument, qty, text);
		}
		
		orderManager.sendOrder(marketOrder);
		return marketOrder;
		
	}
	
	/**
	 * @param instrument
	 * @param side
	 * @param qty
	 * @param price
	 * @param text
	 * @return The submitted {@link Order}
	 */
	protected Order sendLimitOrder(InstrumentId instrument, OrderSide side,double qty,double price,String text){
		LimitOrder limitOrder;
		
		CandleSerie cs = candleSeries.get(instrument);
		if (cs != null){
			// Simulation Order 
			Candle last = cs.getLast();
			limitOrder = new LimitOrder(side, instrument, qty,price, text,last.getDate());
		}else {
			// Live Order 
			limitOrder = new LimitOrder(side, instrument, qty,price, text);
		}
		
		
		orderManager.sendOrder(limitOrder);
		return limitOrder;
		
	}
	
	/**
	 * @param instrument
	 * @param side
	 * @param qty
	 * @param price
	 * @param text
	 * @return The submitted {@link Order}
	 */
	protected Order sendStopOrder(InstrumentId instrument, OrderSide side,double qty,double price,String text){
		StopOrder stopOrder;
		
		CandleSerie cs = candleSeries.get(instrument);
		if (cs != null){
			// Simulation Order 
			Candle last = cs.getLast();
			stopOrder = new StopOrder(side, instrument, qty,price, text,last.getDate());
		}else {
			// Live Order 
			stopOrder = new StopOrder(side, instrument, qty,price, text);
		}
		
		
		orderManager.sendOrder(stopOrder);
		return stopOrder;
		
	}


	protected void setOrderManager(IOrderManager execProvider) {
		this.orderManager = execProvider;
		
	}


	protected void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}
	
	protected boolean hasPosition(InstrumentId instrument){
		return portfolio.getPosition(instrument)>0;
	}
	
	
}
