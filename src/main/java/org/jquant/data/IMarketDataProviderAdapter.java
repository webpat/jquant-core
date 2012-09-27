package org.jquant.data;

import org.joda.time.DateTime;
import org.jquant.instrument.GenericFuture;
import org.jquant.model.InstrumentId;
import org.jquant.serie.CandleSerie;
import org.jquant.serie.QuoteSerie;

/**
 * Core interface to extend the functionnalities of JQuant Market Data Provider Layer 
 * <p>Interface that must be implemented for each reader type to read Series from InstrumentId InstrumentId.
 * @author patrick.merheb
 * @see InstrumentId
 * @see CandleSerie
 * @see QuoteSerie
 * <p>
 * TODO : Split between historicalMarketDataProvider and LiveMarketDataProvider
 */
public interface IMarketDataProviderAdapter {

	/**
	 * @param reader The Legacy/Proprietary MarketData Reader 
	 * @return <code>true</code> if the JQuantDataProvider provider is supported
	 */
	public boolean supports(Object reader);
	
	/**
	 * 
	 * @param symbol the InstrumentId {@link InstrumentId}
	 * @param start begining of the TimeSerie
	 * @param end end of the TimeSerie
	 * @param reader The legacy Market Data Reader
	 * @return a {@link CandleSerie} from the InstrumentId {@link InstrumentId} between start and end 
	 */
	public CandleSerie readCandleSerie(InstrumentId symbol,DateTime start, DateTime end, Object reader);
	
	/**
	 * 
	 * @param symbol
	 * @param start
	 * @param end
	 * @param reader The legacy Market Data Reader
	 * @return a {@link QuoteSerie} from the InstrumentId {@link InstrumentId}
	 */
	public QuoteSerie readQuoteSerie(InstrumentId symbol,DateTime start, DateTime end, Object reader);
	
	/**
	 * 
	 * @param symbol
	 * @param reader The legacy Market Data Reader
	 * @return a {@link CandleSerie} from the InstrumentId {@link InstrumentId}
	 */
	public CandleSerie readCandleSerie(InstrumentId symbol, Object reader);
	
	/**
	 * 
	 * @param future the {@link InstrumentId} of the Future  
	 * @param start begining of the TimeSerie
	 * @param end end of the TimeSerie
	 * @param reader The legacy Market Data Reader
	 * @return a time ordered  List of {@link CandleSerie}
	 */
	public GenericFuture readGenericFuture(InstrumentId future,DateTime start, DateTime end, Object reader);
		
	
	/**
	 * 
	 * @param symbol
	 * @param reader The legacy Market Data Reader
	 * @return a {@link QuoteSerie}
	 */
	public QuoteSerie readQuoteSerie(InstrumentId symbol, Object reader);

	
	
}
