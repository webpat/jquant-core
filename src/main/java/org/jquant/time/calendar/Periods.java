package org.jquant.time.calendar;

import org.joda.time.Months;
import org.joda.time.Period;


/**
 * Common used Periods 
 * 
 * @author patrick.merheb
 *
 */
public interface Periods {

	public final static Period ONE_DAY = Period.days(1);
	
	public final static Period ONE_WEEK = Period.weeks(1);
	
	public final static Period ONE_YEAR = Period.years(1);
	
	public final static Period ONE_MONTH = Months.ONE.toPeriod();
	
	public final static Period SIX_MONTH = Months.SIX.toPeriod();
	
	public final static Period THREE_MONTH = Months.THREE.toPeriod();
	
	public final static Period BUSINESS_YEAR = Period.days(252);
	
	public final static Period BUSINESS_MONTH = Period.days(21);
	
	public final static Period BUSINESS_WEEK = Period.days(5);
	
	

	
	
}
