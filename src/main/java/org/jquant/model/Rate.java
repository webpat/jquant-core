package org.jquant.model;

import org.joda.time.DateTime;
import org.jquant.instrument.rate.InterestRateIndex.RateType;


/**
 * A Rate quotation with a {@link RateType} 
 * @author JQUANT TEAM 
 *
 */
public class Rate extends Quote {

	
	private RateType rateType;
	
	public Rate(){
		super();
	}
	
	public Rate(DateTime date,double rate,RateType rateType){
		super(date,rate);
		this.rateType = rateType;
	}


	public RateType getRateType() {
		return rateType;
	}



//	public InterestRateIndex toInterestRateIndex(Currency currency) {
//		InterestRateIndex interestRate=null;
//		double rate=0.0;
//		if (this.isPercent()) rate = this.quote/100;
//		else rate = this.quote;
//		switch (this.rateType) {
//		case DEPOSIT:
//			interestRate = new DepositIndex(this.date, rate, currency, rateType);
//			break;
//		case SWAP:
//			interestRate = new SwapRateIndex(this.date, rate, currency, this.pillar);
//			break;
//		}
//		return interestRate;
//	}
	
}
