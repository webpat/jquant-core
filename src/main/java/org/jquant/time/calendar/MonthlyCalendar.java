package org.jquant.time.calendar;

import java.util.Iterator;

import org.joda.time.DateTime;

/**
 * This class is used to browse dates in a monthly manner.
 * 
 * This should be used for the 1 st or any day in the month, as long as it is
 * not the last.
 * 
 * This using all days of the calendar. No special treatment for holidays or
 * weekends...
 * 
 * @author Patrick Ducharme-Boutin
 */
class MonthlyCalendar implements IReportingDayCalendar {

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
	 * Constructor of the end of month browser.
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
	 * EX: form is '2006-09-08' and the current value is '2006-10-07' if
	 * forceLowerBound is true, a call to previous will return '2006-09-08'. If
	 * false it will return null.
	 * 
	 * @param forceUpperBound
	 *            if true the 'to' date will be returned even if it is not an
	 *            end of month.
	 * 
	 * EX: to is '2006-12-07' and the current value is '2006-11-08' if
	 * forceUpperBound is true, a call to next will return '2006-12-07'. If
	 * false it will return null.
	 */
	MonthlyCalendar(DateTime from, DateTime to, boolean forceLowerBound, boolean forceUpperBound) {
		this.from = from;
		this.to = to;
		if (to == null) {
			this.to = new DateTime();
		}
		if (this.from == null) {
			this.from = this.to;
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
			current = from;
			return current;
		}

		current = current.plusMonths(1);

		if (current.isAfter(to)) {
			// we just went over the last day
			if ((current.year().get() == to.year().get())
					&& (current.monthOfYear().get() == to.monthOfYear().get()) && forceUpperBound) {

				// but we are on the same month so we send back to (the last
				// day)
				troncatedPeriod = true;
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
			DateTime dd = current.plusMonths(1);

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
			current = to;
			return current;
		}

		current = current.minusMonths(1);

		if (current.isBefore(from)) {
			// we just went over the first day
			if ((current.year().get() == from.year().get())
					&& (current.monthOfYear().get() == from.monthOfYear().get()) && forceLowerBound) {

				// but we are on the same month so we send back from (the first
				// day)
				troncatedPeriod = true;
				return from;
			} else {
				return null;
			}
		} else {
			return current;
		}
	}

	/**
	 * Return the DateTime iterator.
	 */
	public Iterator<DateTime> iterator() {
		current = null;
        return this;
	}

	/**
	 * Is not implemented.
	 */
	public void remove() {
		// nothing !!!
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
