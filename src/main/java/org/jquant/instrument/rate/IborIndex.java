package org.jquant.instrument.rate;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Period;
import org.jquant.model.Currency;
import org.jquant.model.MarketIdentifierCode;
import org.jquant.time.daycounter.Actual365Fixed;
import org.jquant.time.daycounter.DayCounter;


public abstract class IborIndex extends InterestRateIndex{

	
	protected DayCounter dayCounter;
	protected int nbSettlementDays;
	
	// ajouter en arg:
	//	calendar
	public IborIndex(DateTime fixingDate, double rate, Currency currency, Period term, DayCounter dayCounter, int nbSettlementDays) {
		super(fixingDate, rate, currency, term);
		this.dayCounter = dayCounter;
		this.nbSettlementDays = nbSettlementDays;
	}
	
	public RateType getRateType() {
		return RateType.IBOR;
	}
	
	//TODO : peut etre  deplacé vers un "rateHelper"
//	public InterestRateIndex getZeroCoupon() {
//		InterestRateIndex zeroCoupon = null;
//		// get the maturity date
//		DateTime valueDate=getValueDate(this.fixingDate);
//		DateTime maturityDate=getMaturityDate(valueDate);
//		Actual365Fixed actual365Fixed = new Actual365Fixed();
//		
//		
//		// get the daycount fraction
//		double dayCountFraction = dayCounter.calculateYearFraction(valueDate, maturityDate);
//		double annualYearFraction = actual365Fixed.calculateYearFraction(valueDate, maturityDate);
//		
//		//MODIFY IT : take the convention ISDA or ISMA or AFB
//		double zc = java.lang.Math.pow(1+rate*dayCountFraction,1/annualYearFraction)-1;
//		zeroCoupon = new InterestRateIndex(zc,annualYearFraction,actual365Fixed);
//		return zeroCoupon;
//	}
	
	public MarketIdentifierCode getMICMarketPlace() {
		MarketIdentifierCode micMarketPlace=null;
		return micMarketPlace;
	}
	
	public DateTime getMaturityDate(final DateTime valueDate) {
		DateTime maturityDate= valueDate.plus(term);// CalendarUtils.getLastWorkDay(valueDate,this.timeFrame,getMICMarketPlace());
		return maturityDate;
	}
	
	public DateTime getValueDate(final DateTime fixingDate) {
		Period term = Days.days(nbSettlementDays).toPeriod();
		DateTime valueDate= fixingDate.plus(term);//CalendarUtils.getLastWorkDay(fixingDate,term,getMICMarketPlace());
		return valueDate;		
	}
	
	public double getTimeToMaturity() {
		Actual365Fixed actual365Fixed = new Actual365Fixed();
		return actual365Fixed.calculateYearFraction(this.fixingDate,getMaturityDate(getValueDate(this.fixingDate)));
	}
	
	
}
