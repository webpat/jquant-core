package org.jquant.time.daycounter;

import org.joda.time.DateTime;

/**
 * Norme de comptage pour l'année
 * <p> TODO : transformer en enum 
 * @author patrick.merheb
 *
 */
public abstract class DayCounter {

	public DayCounter()	{

	}
	
	public abstract double calculateYearFraction(DateTime startDate, DateTime endDate);

}
