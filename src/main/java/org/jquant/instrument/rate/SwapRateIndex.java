package org.jquant.instrument.rate;

import org.joda.time.DateTime;
import org.jquant.model.Currency;
import org.jquant.time.TimeFrame;


/**
 * Concrete class for market swap rates
 *
 * @author  JQUANT TEAM
 */
public class SwapRateIndex extends InterestRateIndex{

	public SwapRateIndex(DateTime fixingDate, double rate, Currency currency, TimeFrame timeFrame) {
		super(fixingDate, rate, currency, timeFrame);
		
	}
	
	public RateType getRateType() {
		return RateType.SWAP;
	}
	
	public double getTimeToMaturity() {
		return this.timeFrame.length;
	}
	

	


}
