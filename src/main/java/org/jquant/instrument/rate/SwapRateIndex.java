package org.jquant.instrument.rate;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.jquant.model.Currency;


/**
 * Concrete class for market swap rates
 *
 * @author  JQUANT TEAM
 */
public class SwapRateIndex extends InterestRateIndex{

	public SwapRateIndex(DateTime fixingDate, double rate, Currency currency, Period timeFrame) {
		super(fixingDate, rate, currency, timeFrame);
		
	}
	
	public RateType getRateType() {
		return RateType.SWAP;
	}
	
	/**
	 * (T-t) Time to Maturity in days 
	 */
	public double getTimeToMaturity() {
		return getTerm().getDays();
	}
	

	


}
