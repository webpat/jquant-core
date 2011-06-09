/*
 * Created on 12 juin 07
 */
package org.jquant.core;

import org.jquant.core.TimeSerie.Variable;



/**
 * The usual Candle 
 * Open High Low Close
 * @author merhebp
 */
public class Candle extends TimeValue {

    
    private double open;
    private double high;
    private double low;
    private double close;
    private double volume;
    
    
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
	    this.open = candle.open;
	    this.high = candle.high;
	    this.low = candle.low;
	    this.close = candle.close;
	    this.volume = candle.volume;
	}

	/**
     * @param open
     * @param high
     * @param low
     * @param close
     */
    public Candle(double open, double high, double low, double close,double volume) {
        super();
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
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

	public void setOpen(double open) {
		this.open = open;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public Candle clone(){
		Candle clone = new Candle(this);
		return clone;
	}

}
