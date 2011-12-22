package org.jquant.time.calendar;

import java.util.Iterator;

import org.joda.time.DateTime;

/**
 * Interface used to define the methods on a reporting day browser.
 * 
 * @author Patrick Ducharme-Boutin
 */
public interface IDateTimeCalendar extends Iterable<DateTime>, Iterator<DateTime> {

	/**
	 * Return the day the browser starts from.
	 * 
	 * @return a DateTime.
	 */
	public DateTime getStartDay();

	/**
	 * Return the day the browser ends at.
	 * 
	 * @return a DateTime.
	 */
	public DateTime getEndDay();

	/**
	 * Return the previous valid day. Null if there is no more items.
	 * 
	 * @return the previous valid day as a DateTime.
	 */
	public DateTime previous();

	/**
	 * Indicates if after a next or previous call we end up in a troncated
	 * period (related to the forcing on upper and lower bounds).
	 * 
	 * @return true if are in the case of a forcing on upper and lower bounds.
	 */
	public boolean isTroncatedPeriod();
	
	/**
	 * reset the iterator 
	 * allows multiple browsing on the same Calendar
	 */
	public void reset();
}
