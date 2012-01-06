package org.jquant.serie;

import org.joda.time.DateTime;
import org.jquant.instrument.rate.InterestRateIndex.RateType;


/**
 * A Rate quotation with a {@link RateType} 
 * @author JQUANT TEAM 
 *
 */
public class Rate extends AbstractTimeValue {

	
	private final RateType rateType;
	private final double rate; 

	
	/**
	 * FIXME : Voir si le taux est exprimé en % par convention 
	 * @param date
	 * @param rate
	 * @param rateType
	 */
	public Rate(DateTime date,double rate,RateType rateType){
		super(date);
		this.rateType = rateType;
		this.rate = rate;
	}


	public RateType getRateType() {
		return rateType;
	}

	
	

	public double getRate() {
		return rate;
	}


	@Override
	public double getValue() {
		
		return getRate();
	}


	
}
