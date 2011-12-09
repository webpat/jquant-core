package org.jquant.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jquant.data.JQuantDataProvider;
import org.jquant.data.MarketDataReaderAdapter;
import org.jquant.data.MarketDataReaderMapping;
import org.jquant.exception.MarketDataReaderException;
import org.jquant.model.MarketDataPrecision;
import org.jquant.model.Symbol;
import org.jquant.serie.CandleSerie;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * Instrument Repository for simulation and backtesting
 * <p> The MarketManager holds the instruments that are used during a simulation or at Runtime. 
 
 *  The StrategyRunner read the series and dispatch Quotes and Candles to the strategies. 
 * @author pat
 *@see MarketDataPrecision
 *TODO: Chargement from To  
 *TODO: Gestion du cache avec une clé composite (Symbol:from:to)
 */
@Component
public class MarketManager implements InitializingBean, ApplicationContextAware {

	private List<CandleSerie> candleSeries;

	private ApplicationContext applicationContext;
	
	private Map<String, MarketDataReaderMapping> mappings;
	
	private Map<String, MarketDataReaderAdapter> adapters;
	
	
	
	/**
	 *  Add an Instrument in the simulation scope
	 *  <ul>
	 *  <li>Load all Historical Data</li>
	 *  <li>Subscribe for new Market Data Events concerning this instrument</li>
	 *  </ul>
	 * @param symbol the {@link Symbol} of the Instrument you want to add to the simulation  
	 * @param precision The MarketData resolution (CANDLE,TRADE,QUOTE)
	 * @throws MarketDataReaderException 
	 */
	public void addInstrument(Symbol symbol,MarketDataPrecision precision) throws MarketDataReaderException{
		
		Object reader = findMarketDataReader(symbol.getProvider());
		
		if (reader == null ) throw new MarketDataReaderException("No MarketData reader for provider " + symbol.getProvider());
		
		MarketDataReaderAdapter readerAdapter = findReaderAdapter(reader);
		
		switch (precision){
		case CANDLE: 
			// Read All historical market data
			CandleSerie candles = readerAdapter.readCandleSerie(symbol, reader);
			candles.setSymbol(symbol);
			
			// TODO: Manage Implied Volatility If Any
			// TODO : Subscribe for New CandleEvents
			
			//Add Serie to the MarketMgr space (TODO: Manage cache)
			candleSeries.add(candles);
			break;
			
		case QUOTE:
			throw new UnsupportedOperationException() ;
			// TODO : Read All historical market data 			
			// TODO : Subscribe for new QuoteEvents
			// TODO : Add Serie to the MarketMgr space
			
		case TRADE: 
			throw new UnsupportedOperationException() ;
		
		}
		
		
	}
	
@Override
	public void afterPropertiesSet() throws Exception {
		candleSeries = new ArrayList<CandleSerie>();
		initMarketDataProviderReaderMappings();
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
	}
	
	private void initMarketDataProviderReaderMappings(){
		mappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, MarketDataReaderMapping.class, true, false);
		adapters = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, MarketDataReaderAdapter.class, true, false);
	}
	
	/**
	 * Return the adapter for the reader 
	 * @param reader Legacy/Proprietary MarketDataReader
	 * @return the adapter {@link MarketDataReaderAdapter}
	 */
	private MarketDataReaderAdapter findReaderAdapter(Object reader) {
		if (adapters != null){
			// Parse all adapters 
			for (MarketDataReaderAdapter adapter :adapters.values()){
				if (adapter.supports(reader)){
					return adapter;
				}
			}
		}
		return null;
	}

	/**
	 * return the legacy/proprietary Market Data Provider  
	 * @param provider
	 * @return
	 */
	private Object findMarketDataReader(JQuantDataProvider provider){
		if (mappings != null){
			// Parse all mappings 
			for (MarketDataReaderMapping mapping : mappings.values()){
				if (mapping.getHandler(provider)!= null){
					return mapping.getHandler(provider);
				}
			}
		}
		return null;
	}

}
