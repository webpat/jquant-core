package org.jquant.instrument.rate;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.jquant.model.Currency;
import org.jquant.time.daycounter.DayCounter;


public class Euribor extends IborIndex{

	public Euribor(DateTime fixingDate, double rate, Currency currency, Period period, DayCounter dayCounter) {
		super(fixingDate, rate, currency, period, dayCounter,2);
	}
}
