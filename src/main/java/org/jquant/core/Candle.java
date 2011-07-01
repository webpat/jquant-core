/*
 * Created on 12 juin 07
 */
package org.jquant.core;

import org.joda.time.DateTime;
import org.jquant.serie.TimeSerie.Variable;



/**
 * The usual Candle 
 * Open High Low Close
 * @author merhebp
 */
public class Candle extends TimeValue {

    
	/**
	 * Le chandelier est-il INTRADAY,DAILY,WEEKLY,MONTHLY
	 * @author patrick.merheb
	 *
	 */
    public enum CandlePeriod {
    	CUSTOM,DAILY,WEEKLY,MONTHLY,YEARLY
	}

	private double open;
    private double high;
    private double low;
    private double close;
    private double volume;
    private CandlePeriod period;
    
    
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
     * @param start 
	 * @param period 
	 * @param open Valeur à l'ouverture 
     * @param high Valeur haute
     * @param low Valeur basse
     * @param close Valeur à la fermeture  
	 * @param volume Volume des transactions pendant la période donnée 
     */
    public Candle(DateTime start,CandlePeriod period,double open, double high, double low, double close,double volume) {
        super();
        this.date = start;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.period = period;
    }

    public Variable getDefaultVariable() {
        // The close is the default 
        return Variable.CLOSE;
    }

    public double getValue(Variable var) {
    	switch (var) {
		case CLOSE:
			return close;
		case OPEN:
			return open;
		case HIGH:
			return high;
		case LOW:
			return low;
		case VOLUME:
			return volume;

		default:
			return Double.NaN;

		}
        
    }
	

	public double getValue() {
		return close;
	}
	
	
	public double getOpen() {
		return open;
	}

	

	public double getHigh() {
		return high;
	}

	

	public double getLow() {
		return low;
	}

	

	public double getClose() {
		return close;
	}

	

	public double getVolume() {
		return volume;
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
