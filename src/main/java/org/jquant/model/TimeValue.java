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
	
	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public void setPadValue(boolean padValue) {
		this.padValue = padValue;
	}

//	public abstract double getValue(Variable var);
	
	public abstract double getValue(); 

	public boolean isPadValue() {
		
		return padValue;
	}
	
	public boolean isPercent() {
		return isPercent;
	}

	public void setPercent(boolean isYield) {
		this.isPercent = isYield;
	}



}
