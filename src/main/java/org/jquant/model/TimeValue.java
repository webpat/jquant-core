package org.jquant.model;

import org.joda.time.DateTime;


/**
 * Temporal Value
 * 
 * @author patrick.merheb
 *
 *@see Candle
 *@see Quote
 *
 */
public abstract class TimeValue implements ITimeValue{

	protected DateTime date;
	protected boolean padValue;
	protected boolean isPercent;   
	
	/**
	 * @return moment in time where the Value stands 
	 */
	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public void setPadValue(boolean padValue) {
		this.padValue = padValue;
	}

	/**
	 * 
	 */
	public abstract double getValue(); 

	/**
	 * @return Is it a Padding Value ? 
	 */
	public boolean isPadValue() {
		
		return padValue;
	}
	
	/**
	 * 
	 * @return is it a yield or an absolute value ? 
	 */
	public boolean isPercent() {
		return isPercent;
	}

	public void setPercent(boolean isYield) {
		this.isPercent = isYield;
	}



}
