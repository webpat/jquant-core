package org.jquant.serie;

import org.joda.time.DateTime;



/**
 * Temporal Value
 * 
 * @author patrick.merheb
 *
 *@see Candle
 *@see BBBA
 *
 */
public class TimeValue extends AbstractTimeValue implements ITimeValue{

	public TimeValue(DateTime date, double value) {
		super(date);
		this.value = value;
	}

	private final double value;   
	
	/**
	 * 
	 */
	public  double getValue(){
		return value; 
	}

	@Override
	public String toString() {
		return "TimeValue [date=" + date + ", value=" + value + "]";
	}



}
