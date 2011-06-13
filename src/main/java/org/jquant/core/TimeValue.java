package org.jquant.core;

import org.joda.time.DateTime;
import org.jquant.serie.TimeSerie.Variable;


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

	public abstract double getValue(Variable var);
	
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
