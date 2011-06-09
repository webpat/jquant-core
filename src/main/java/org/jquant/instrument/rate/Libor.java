package org.jquant.instrument.rate;

import org.joda.time.DateTime;
import org.jquant.model.Currency;
import org.jquant.time.TimeFrame;
import org.jquant.time.daycounter.Actual365Fixed;


public class Libor extends IborIndex {

	public Libor(DateTime fixingDate, double rate, Currency currency, TimeFrame timeFrame) {
		super(fixingDate, rate, currency, timeFrame, new Actual365Fixed(),0);
		// TODO Auto-generated constructor stub
	}

}
