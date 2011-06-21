package org.jquant.instrument.rate;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.jquant.model.Currency;


/**
 * <b>Description : </b> :Abstract class for market interest rates 
 * fixingDate (au SPOT)  , valueDate (SPOT+settlementDate), maturityDate (valueDate + maturity)
 * @author  JQUANT TEAM
 */
abstract public class InterestRateIndex {

	protected double rate;
	protected Currency currency;
	protected DateTime fixingDate;
	protected Period term;

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
	
	public InterestRateIndex(DateTime fixingDate, double rate, Currency currency, Period term){
		this.fixingDate = fixingDate;
		this.rate = rate;
		this.currency = currency;
		this.term  = term;
	}
	
	abstract public RateType getRateType();
	abstract public double getTimeToMaturity();
	
	

	public Period getTerm() {
		return term;
	}

	public double getRate() {
		return rate;
	}

	
}
