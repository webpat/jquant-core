package org.jquant.time.daycounter;

import org.joda.time.DateTime;

public abstract class DayCounter {

	public DayCounter()	{

	}
	
	public abstract double calculateYearFraction(DateTime startDate, DateTime endDate);

}
