package org.jquant.instrument.rate;

import org.joda.time.DateTime;
import org.jquant.core.MICMarketPlace;
import org.jquant.model.Currency;
import org.jquant.time.TimeFrame;
import org.jquant.time.TimeUnit;
import org.jquant.time.calendar.CalendarUtils;
import org.jquant.time.daycounter.Actual365Fixed;
import org.jquant.time.daycounter.DayCounter;


public abstract class IborIndex extends InterestRateIndex{

	
	protected DayCounter dayCounter;
	protected int nbSettlementDays;
	
	// ajouter en arg:
	//	calendar
	public IborIndex(DateTime fixingDate, double rate, Currency currency, TimeFrame timeFrame, DayCounter dayCounter, int nbSettlementDays) {
		super(fixingDate, rate, currency, timeFrame);
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
	
	public MICMarketPlace getMICMarketPlace() {
		MICMarketPlace micMarketPlace=null;
		return micMarketPlace;
	}
	
	public DateTime getMaturityDate(final DateTime valueDate) {
		DateTime maturityDate=CalendarUtils.getLastWorkDay(valueDate,this.timeFrame,getMICMarketPlace());
		return maturityDate;
	}
	
	public DateTime getValueDate(final DateTime fixingDate) {
		TimeFrame timeFrame = new TimeFrame(this.nbSettlementDays,TimeUnit.DAY);
		DateTime valueDate=CalendarUtils.getLastWorkDay(fixingDate,timeFrame,getMICMarketPlace());
		return valueDate;		
	}
	
	public double getTimeToMaturity() {
		Actual365Fixed actual365Fixed = new Actual365Fixed();
		return actual365Fixed.calculateYearFraction(this.fixingDate,getMaturityDate(getValueDate(this.fixingDate)));
	}
	
	
}
