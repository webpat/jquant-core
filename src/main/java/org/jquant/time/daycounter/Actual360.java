package org.jquant.time.daycounter;

import org.joda.time.DateTime;
import org.joda.time.Days;


public class Actual360 extends DayCounter{

	public Actual360()	{
		super();
	}

	@Override
	public double calculateYearFraction(DateTime startDate, DateTime endDate) {

		return Days.daysBetween(startDate, endDate).getDays()/(double)360;
	}
}
