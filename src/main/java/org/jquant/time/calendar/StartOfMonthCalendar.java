package org.jquant.time.calendar;

import java.util.Iterator;

import org.joda.time.DateTime;
import org.joda.time.Months;

/**
 * This class is used to browse dates in a monthly manner.
 * 
 * This is used to go from the first day of a month to next/previous.
 * 
 * This is using all days of the calendar. No special treatment for holidays or
 * weekends...
 * 
 * @author Patrick Ducharme-Boutin
 */
class StartOfMonthCalendar implements IDateTimeCalendar {

	/** start (min) date */
	private DateTime from;

	/** end (max) date */
	private DateTime to;

	/** holds the current (as in iteration) date */
	private DateTime current;

	/** used to know if we have to return the min date (from) */
	private boolean forceLowerBound;

	/** used to know if we have to return the max date (to) */
	private boolean forceUpperBound;

	/**
	 * indicates if we are in the case of a troncated period (in the upper or
	 * lower bounds)
	 */
	private boolean troncatedPeriod;

	/**
	 * Constructor of the start of month browser.
	 * 
	 * @param from
	 *            the smallest month of the browser.
	 * @param to
	 *            the biggest month of the browser.
	 * 
	 * @param forceLowerBound
	 *            if true the 'from' date will be returned on a previous call if
	 *            we fall on the same month as 'from' but a prior day.
	 * 
	 * EX: form is '2006-09-08' and the current value is '2006-09-29' if
	 * forceLowerBound is true, a call to previous will return '2006-09-08'. If
	 * false it will return null.
	 * 
	 * @param forceUpperBound
	 *            if true the 'to' date will be returned even if it is not an
	 *            end of month.
	 * 
	 * EX: to is '2006-12-07' and the current value is '2006-11-30' if
	 * forceUpperBound is true, a call to next will return '2006-12-07'. If
	 * false it will return null.
	 */
	StartOfMonthCalendar(DateTime from, DateTime to, boolean forceLowerBound,
			boolean forceUpperBound) {
		this.from = from;
		this.to = to;
		if (to == null) {
			this.to = new DateTime();
		}
		this.forceLowerBound = forceLowerBound;
		this.forceUpperBound = forceUpperBound;
	}

	/**
	 * Return the next valid month.
	 * 
	 * @return the next valid day as a DateTime.
	 */
	public DateTime next() {
		troncatedPeriod = false;
		if (current == null) {
			if (forceLowerBound) {
				if (from.isEqual(from.dayOfMonth().withMinimumValue())) {
					current = from;
					return current;
				} else {
					// we are not strict on the first value and the from is not
					// on a start of month
					troncatedPeriod = true;
					current = from;
					return from;
				}
			} else {
				current = from.plusMonths(1).dayOfMonth().withMinimumValue();
				return current;
			}
		}

		current = current.plusMonths(1).dayOfMonth().withMinimumValue();

		if (current.isAfter(to)) {
			// we just went over the last day
			if (forceUpperBound && (Months.monthsBetween(to, current).getMonths() < 1)) {
				// but we are on the same month so we send back to (the last
				// day)
				troncatedPeriod = true;
				current = to.plusMonths(4); 
				return to;
			} else {
				return null;
			}
		} else {
			return current;
		}
	}

	/**
	 * Return true if the iterator has more elements.
	 * 
	 * @return boolean.
	 */
	public boolean hasNext() {
		if (current == null) {
			return true;
		} else {
			DateTime dd = current.plusMonths(1).dayOfMonth().withMinimumValue();

			if (dd.isAfter(to)) {
				// we just went over the last day
				if ((dd.year().get() == to.year().get())
						&& (dd.monthOfYear().get() == to.monthOfYear().get()) && forceUpperBound) {

					// but we are on the same month so we send back to (the last
					// day)
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		}
	}

	/**
	 * Return the previous valid month.
	 * 
	 * @return the previous valid day as a DateTime.
	 */
	public DateTime previous() {
		troncatedPeriod = false;
		if (current == null) {
			if (forceUpperBound) {
				if (!to.isEqual(to.dayOfMonth().withMinimumValue())) {
					troncatedPeriod = true;
				}
				current = to;
				return current;
			} else if (to.isEqual(to.dayOfMonth().withMinimumValue())) {
				current = to;
				return current;
			} else {
				current = to;
			}
		}

		current = current.minusMonths(1).dayOfMonth().withMinimumValue();

		if (current.isBefore(from)) {
			// we just went over the first day
			DateTime fromMinusOne = from.minusMonths(1).plusDays(1);
			if (!from.isEqual(from.dayOfMonth().withMinimumValue()) && forceLowerBound
					&& fromMinusOne.isBefore(current)) {
				return from;
			} else {
				return null;
			}
		} else {
			return current;
		}
	}

	/**
	 * Indicates if after a next or previous call we end up in a troncated
	 * period (related to the forcing on upper and lower bounds).
	 * 
	 * @return true if are in the case of a forcing on upper and lower bounds.
	 */
	public boolean isTroncatedPeriod() {
		return troncatedPeriod;
	}

	/**
	 * Return the DateTime iterator.
	 */
	public Iterator<DateTime> iterator() {
		return this;
	}

	/**
	 * Is not implemented.
	 */
	public void remove() {
		// nothing !!!
	}

	/**
	 * Return the day the browser starts from.
	 * 
	 * @return a DateTime.
	 */
	public DateTime getStartDay() {
		return from;
	}

	/**
	 * Return the day the browser ends at.
	 * 
	 * @return a DateTime.
	 */
	public DateTime getEndDay() {
		return to;
	}
	public void reset() {
		current = from;
		
	}
	
	
}
