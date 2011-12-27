/*
 * Created on 12 juin 07
 */
package org.jquant.model;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.jquant.serie.CandleSerie;
import org.jquant.time.calendar.Periods;



/**
 * The usual Candle (aka Bar)
 * <p><b>date, Open, High, Low, Close, {@link Period}</b> 
 * @author merhebp
 */
public class Candle extends TimeValue {

    

	private double open;
    private double high;
    private double low;
    private double close;
    private double volume;
    private Period period;
    
    private CandleSerie serie;
    
    
    /**
     * Blank constructor
     */
    public Candle(){
    	
    }
    
    /**
	 * Copy Constructor
	 *
	 * @param candle a <code>Candle</code> object
	 */
	private Candle(Candle candle) 
	{
		this.date = candle.date;
        this.open = candle.open;
        this.high = candle.high;
        this.low = candle.low;
        this.close = candle.close;
        this.volume = candle.volume;
        this.period = candle.period;
	}

	/**
     * @param date Candle sampling start date  
	 * @param period Candle Sampling Period 
	 * @param open Opening Value
     * @param high Highest Value during the sampling
     * @param low Lowest value during the sampling
     * @param close Closing Value at the End of the sampling
	 * @param volume Transaction Volume during sampling 
     */
    public Candle(DateTime date,Period period,double open, double high, double low, double close,double volume) {
        super();
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.period = period;
    }

	/**
	 * 
	 */
    public double getValue() {
		return close;
	}
	
	/**
	 * 
	 * @return Opening Value
	 */
	public double getOpen() {
		return open;
	}

	
	/**
	 * 
	 * @return Highest Value during the sampling
	 */
	public double getHigh() {
		return high;
	}

	
	/**
	 * 
	 * @return Lowest value during the sampling
	 */
	public double getLow() {
		return low;
	}

	
	/**
	 * 
	 * @return Closing Value at the End of the sampling
	 */
	public double getClose() {
		return close;
	}

	
	/**
	 * 
	 * @return The exchanged volume during {@link #getDate()} + {@link #getPeriod()}
	 */
	public double getVolume() {
		return volume;
	}
	

	
	/**
	 * The Candle period (INTRADAY_360ms_CANDLE , ONE_DAY Candle, ONE_WEEK_CANDLE, ONE_MONTH_CANDLE ...)
	 * @return The Joda Time {@link Period}  ex : {@link Periods#ONE_DAY}, {@link Periods#BUSINESS_YEAR} 
	 */
	public Period getPeriod() {
		return period;
	}

	
	
	/**
	 *
	 * @return The owning {@link CandleSerie}
	 */
	public CandleSerie getSerie() {
		return serie;
	}

	public void setSerie(CandleSerie serie) {
		this.serie = serie;
	}

	public Candle clone(){
		Candle clone = new Candle(this);
		return clone;
	}

	@Override
	public String toString() {
		return "Candle [open=" + open + ", high=" + high + ", low=" + low
				+ ", close=" + close + ", volume=" + volume + "]";
	}

	
	
}
