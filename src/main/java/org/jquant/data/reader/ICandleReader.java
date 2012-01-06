package org.jquant.data.reader;

import org.joda.time.DateTime;
import org.jquant.exception.MarketDataReaderException;
import org.jquant.serie.Candle;
import org.jquant.serie.CandleSerie;


/**
 * Interface to be implemented by objects that can read a <b>candle</b> serie from an Instrument
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
 * @see Candle 
 * @see CandleSerie
 * @deprecated
 */
@Deprecated
public interface ICandleReader {

	/**
	 * 
	 * @param instrumentId
	 * @return the {@link CandleSerie} associated with the instrument whole lifetime 
	 * @throws MarketDataReaderException
	 */
	public CandleSerie fetchAllCandle(String instrumentId) throws MarketDataReaderException;
	
	
	/**
	 * 
	 * @param instrumentId
	 * @param start a {@link DateTime} that marks the begining of the serie
	 * @param end a {@link DateTime} that marks the end of the serie
	 * @return a {@link CandleSerie} between start and end 
	 * @throws MarketDataReaderException
	 */
	public CandleSerie fetchAllCandle(String instrumentId,DateTime start, DateTime end) throws MarketDataReaderException;
	
	/**
	 * 
	 * @param instrumentId
	 * @param date a market day 
	 * @return one {@link Candle} for date 
	 * @throws MarketDataReaderException
	 */
	public Candle fetchCandle(String instrumentId,DateTime date) throws MarketDataReaderException;
	
	
}
