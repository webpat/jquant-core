package org.jquant.data.reader;

import org.joda.time.DateTime;
import org.jquant.exception.MarketDataReaderException;
import org.jquant.model.Quote;
import org.jquant.serie.QuoteSerie;


/**
 * Interface to be implemented by objects that can read a <b>quote</b> serie from an Instrument
 * <p> 
 * Abstraction layer with the data provider (Louxor/Yahoo/IB/TENFORE etc...)
 * <ul>
 * <li>load Data from Data Layer</li>
 * <li>eventually apply transformations on the Transfer Objects List </li>
 * <li>build the TimeSerie </li>
 * 
 * </ul>
 * @author patrick.merheb
 * 
 * @see Quote
 * @see QuoteSerie
 * 
 * @deprecated
 */
@Deprecated
public interface IQuoteReader {

	/**
	 * Lit une série de quotations 
	 * @param instrumentId identifiant de l'instrument chez le provider 
	 * @return une {@link QuoteSerie}
	 * @throws MarketDataReaderException
	 */
	public QuoteSerie fetchAllQuote(String instrumentId) throws MarketDataReaderException;
	
	/**
	 * Lit une s�rie de quotations entre une date de début et une date de fin
	 * @param instrumentId identifiant de l'instrument chez le provider 
	 * @param debut a {@link DateTime} that marks the begining of the serie
	 * @param fin a {@link DateTime} that marks the end of the serie
	 * @return une {@link QuoteSerie}
	 * @throws MarketDataReaderException
	 */
	public QuoteSerie fetchAllQuote(String instrumentId,DateTime debut, DateTime fin) throws MarketDataReaderException;
	
	/**
	 * Lit une unique quotations 
	 * @param instrumentId identifiant de l'instrument chez le provider 
	 * @param date a market day 
	 * @return une {@link Quote}
	 * @throws MarketDataReaderException
	 */
	public Quote fetchQuote(String instrumentId,DateTime date) throws MarketDataReaderException;
	
	
}
