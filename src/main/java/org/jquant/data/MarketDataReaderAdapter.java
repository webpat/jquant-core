package org.jquant.data;

import org.joda.time.DateTime;
import org.jquant.model.Symbol;
import org.jquant.serie.CandleSerie;
import org.jquant.serie.QuoteSerie;

/**
 * Core interface to extend the functionnalities of JQuant Market Data Layer 
 * <p>Interface that must be implemented for each reader type to read Series from Instrument Symbol.
 * @author patrick.merheb
 * @see Symbol
 * @see CandleSerie
 * @see QuoteSerie
 */
public interface MarketDataReaderAdapter {

	/**
	 * @param reader The Legacy/Proprietary MarketData Reader 
	 * @return <code>true</code> if the JQuantDataProvider provider is supported
	 */
	public boolean supports(Object reader);
	
	/**
	 * 
	 * @param symbol the Instrument {@link Symbol}
	 * @param start begining of the TimeSerie
	 * @param end end of the TimeSerie
	 * @param reader
	 * @return a {@link CandleSerie} from the Instrument {@link Symbol} between start and end 
	 */
	public CandleSerie readCandleSerie(Symbol symbol,DateTime start, DateTime end, Object reader);
	
	/**
	 * 
	 * @param symbol
	 * @param start
	 * @param end
	 * @param reader
	 * @return a {@link QuoteSerie} from the Instrument {@link Symbol}
	 */
	public QuoteSerie readQuoteSerie(Symbol symbol,DateTime start, DateTime end, Object reader);
	
	/**
	 * 
	 * @param symbol
	 * @param reader
	 * @return a {@link CandleSerie} from the Instrument {@link Symbol}
	 */
	public CandleSerie readCandleSerie(Symbol symbol, Object reader);
	
	public QuoteSerie readQuoteSerie(Symbol symbol, Object reader);
	
}
