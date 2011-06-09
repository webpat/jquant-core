package org.jquant.instrument.rate;

import org.joda.time.DateTime;
import org.jquant.model.Currency;
import org.jquant.time.TimeFrame;


/**
 * <b>Description : </b> :Abstract class for market interest rates 
 * fixingDate (au SPOT)  , valueDate (SPOT+settlementDate), maturityDate (valueDate + maturity)
 * @author  JQUANT TEAM
 */
abstract public class InterestRateIndex {

	protected double rate;
	protected Currency currency;
	protected DateTime fixingDate;
	protected TimeFrame timeFrame;

	/**
	 * Type de TAUX 
	 *<ul>
	 *<li>Deposit</li>
	 *<li>IBOR  </li>
	 *<li>Future </li>
	 *<li>Swap </li>
	 *</ul>
	 */
	public enum RateType {
		DEPOSIT,
		IBOR,
		FUTURE,
		SWAP;
	}
	
	public InterestRateIndex(DateTime fixingDate, double rate, Currency currency, TimeFrame timeFrame){
		this.fixingDate = fixingDate;
		this.rate = rate;
		this.currency = currency;
		this.timeFrame = timeFrame;
	}
	
	abstract public RateType getRateType();
	abstract public double getTimeToMaturity();
	
	public TimeFrame getTimeFrame() {
		return this.timeFrame;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
}
