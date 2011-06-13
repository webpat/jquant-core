package org.jquant.data.reader;

import java.util.List;

import org.joda.time.DateTime;
import org.jquant.core.Candle;
import org.jquant.core.CandleSerie;
import org.jquant.exception.MarketDataReaderException;


public interface ICandleReader {

	/**
	 * 
	 * @param instrumentId
	 * @return
	 * @throws MarketDataReaderException
	 */
	public CandleSerie fetchAllCandle(String instrumentId) throws MarketDataReaderException;
	
	
	/**
	 * 
	 * @param instrumentId
	 * @param start
	 * @param end
	 * @return 
	 * @throws MarketDataReaderException
	 */
	public CandleSerie fetchAllCandle(String instrumentId,DateTime start, DateTime end) throws MarketDataReaderException;
	
	/**
	 * 
	 * @param instrumentId
	 * @param date
	 * @return one {@link Candle}
	 * @throws MarketDataReaderException
	 */
	public Candle fetchCandle(String instrumentId,DateTime date) throws MarketDataReaderException;
	
	
}