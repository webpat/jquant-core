package org.jquant.instrument.rate;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.jquant.model.Currency;
import org.jquant.time.daycounter.Actual360;
import org.jquant.time.daycounter.Actual365Fixed;
import org.jquant.time.daycounter.DayCounter;


public class DepositIndex extends IborIndex{

	public DepositIndex(DateTime fixingDate, double rate, Currency currency, Period term, DayCounter dayCounter) {
		super(fixingDate, rate, currency, term, dayCounter, 2);
	
	}
	
	public DepositIndex(DateTime fixingDate, double rate, Currency currency, Period term) {
		super(fixingDate, rate, currency, term, null, 2);
		if (currency==Currency.GBP)
			this.dayCounter = new Actual365Fixed();
		else
			this.dayCounter = new Actual360();
	}

	public RateType getRateType() {
		return RateType.DEPOSIT;
	}
}
