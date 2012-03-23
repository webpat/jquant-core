package org.jquant.time.daycounter;

import org.joda.time.DateTime;

public class ActualActual extends DayCounter{

	// http://www.isda.org/c_and_a/pdf/mktc1198.pdf
	public enum Convention {
		ISDA,
		ISMA,
		AFB
	}

	@Override
	public double calculateYearFraction(DateTime startDate, DateTime endDate) {
		return 0;
	}
}
