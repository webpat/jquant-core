package org.jquant.data;

import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;
import org.jquant.exception.MarketDataReaderException;
import org.jquant.model.InstrumentId;
import org.jquant.model.StitchingMethod;
import org.jquant.serie.Candle;

public interface IMarketManager {

	/**
	 *  Add an InstrumentId in the simulation scope
	 *  <ul>
	 *  <li>Load all Historical Data</li>
	 *  <li>Subscribe for new Market Data Events concerning this instrument</li>
	 *  </ul>
	 * @param symbol the {@link InstrumentId} of the InstrumentId you want to add to the simulation  
	 * @param from {@link DateTime} begining of the historical data  
	 * @param to {@link DateTime} end of the historical data  
	 * @throws MarketDataReaderException 
	 */
	public abstract void addInstrument(InstrumentId symbol, DateTime from, DateTime to) throws MarketDataReaderException;

	/**
	 * 
	 * @param future Future (ex: CRUDE OIL, COPPER MINI, NATURAL GAS, GOLD 100 ...)
	 * @param from {@link DateTime} begining of the historical data   
	 * @param to {@link DateTime} end of the historical data  
	 * @param method {@link StitchingMethod} (RETURN, CONTINUOUS)
	 * @throws MarketDataReaderException
	 */
	public abstract void addGenericFuture(InstrumentId future, DateTime from, DateTime to,StitchingMethod method) throws MarketDataReaderException;
	
	/**
	 * 
	 * @param future {@link InstrumentId} Future (ex: CRUDE OIL, COPPER MINI, NATURAL GAS, GOLD 100 ...)
	 * @param deliveryMonth {@link Integer} {@link Calendar#MONTH} of delivery 
	 * @param deliveryYear {@link Integer} year of Delivery
	 * @throws MarketDataReaderException
	 */
	public abstract void addFuture(InstrumentId future, Integer deliveryMonth,Integer deliveryYear) throws MarketDataReaderException;

	/**
	 * 
	 * @param timestamp a {@link DateTime}
	 * @return The Collection of Candles in the market at a precise time 
	 */
	public abstract Map<InstrumentId, Candle> getMarketSlice(DateTime timestamp);

	public abstract Candle getCandle(InstrumentId symbol, DateTime timestamp);

	/**
	 * used by the simulation stragy runner to determine 
	 * which date is the oldest candle and which date is the youngest candle in the historical market 
	 * @return First and Last DateTime for the current Market
	 */
	public abstract ImmutablePair<DateTime, DateTime> getFirstLast();

}