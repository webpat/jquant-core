package org.jquant.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jquant.execution.IBroker;
import org.jquant.model.IInstrument;
import org.jquant.model.InstrumentId;
import org.jquant.model.MarketDataPrecision;
import org.jquant.order.LimitOrder;
import org.jquant.order.MarketOrder;
import org.jquant.order.Order;
import org.jquant.order.Order.OrderSide;
import org.jquant.order.StopOrder;
import org.jquant.portfolio.Portfolio;
import org.jquant.serie.Candle;
import org.jquant.serie.CandleSerie;

/**
 * Base Class for a Strategy
 * <p>
 *  The Strategies adds the instrument to the market Manager with the {@link #initMarket()} method. 
 * 
 * <p>
 * TODO : split Strategy in StrategyComponents to provide strategy modularity
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
	 * The {@link IBroker}
	 */
	private IBroker execProvider;
	
	
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
	public CandleSerie getCandleSerie(InstrumentId symbol){
		return candleSeries.get(symbol);
	}
	
	public void setCandleSerieMap(Map<InstrumentId,CandleSerie> map){
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
	public Order sendMarketOrder(IInstrument instrument, OrderSide side,double qty,String text){
		
		MarketOrder marketOrder;
		CandleSerie cs = candleSeries.get(instrument.getId());
		if (cs != null){
			// Simulation Order 
			Candle last = cs.getLast();
			marketOrder = new MarketOrder(side, instrument, qty, text,last.getDate());
		}else {
			// Live Order 
			marketOrder = new MarketOrder(side, instrument, qty, text);
		}
		
		execProvider.sendOrder(marketOrder);
		return marketOrder;
		
	}
	
	/**
	 * TODO : Fix date 
	 * @param instrument
	 * @param side
	 * @param qty
	 * @param price
	 * @param text
	 * @return The submitted {@link Order}
	 */
	public Order sendLimitOrder(IInstrument instrument, OrderSide side,double qty,double price,String text){
		LimitOrder limitOrder = new LimitOrder(side, instrument, qty,price, text);
		execProvider.sendOrder(limitOrder);
		return limitOrder;
		
	}
	
	/**
	 * TODO : Fix Date 
	 * @param instrument
	 * @param side
	 * @param qty
	 * @param price
	 * @param text
	 * @return The submitted {@link Order}
	 */
	public Order sendStopOrder(IInstrument instrument, OrderSide side,double qty,double price,String text){
		StopOrder stopOrder = new StopOrder(side, instrument, qty,price, text);
		execProvider.sendOrder(stopOrder);
		return stopOrder;
		
	}


	public void setExecutionProvider(IBroker execProvider) {
		this.execProvider = execProvider;
		
	}


	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}
	
	
	
	
}
