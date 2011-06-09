package org.jquant.time.daycounter;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class Actual365Fixed extends DayCounter{

	@Override
	public double calculateYearFraction(DateTime startDate, DateTime endDate) {

		return Days.daysBetween(startDate, endDate).getDays()/(double)365;
	}

}
