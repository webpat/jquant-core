package org.jquant.instrument.rate;

import org.joda.time.DateTime;
import org.jquant.model.Currency;
import org.jquant.time.TimeFrame;
import org.jquant.time.daycounter.DayCounter;


public class Euribor extends IborIndex{

	public Euribor(DateTime fixingDate, double rate, Currency currency, TimeFrame timeFrame, DayCounter dayCounter) {
		super(fixingDate, rate, currency, timeFrame, dayCounter,2);
		// TODO Auto-generated constructor stub
	}
}
