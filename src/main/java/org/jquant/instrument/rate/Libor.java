package org.jquant.instrument.rate;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.jquant.model.Currency;
import org.jquant.time.daycounter.Actual365Fixed;


public class Libor extends IborIndex {

	public Libor(DateTime fixingDate, double rate, Currency currency, Period term) {
		super(fixingDate, rate, currency, term, new Actual365Fixed(),0);
		// TODO Auto-generated constructor stub
	}

}
