package org.jquant.time.calendar;

import java.util.Iterator;

import org.joda.time.DateTime;

/**
 * This class is used to browse dates in a daily manner.
 * 
 * This using all days of the calendar. No special treatment for holidays or
 * weekends...
 * 
 * @author Patrick Ducharme-Boutin
 */
class DailyCalendar implements IReportingDayCalendar {

	/** start (min) date */
	private DateTime from;

	/** end (max) date */
	private DateTime to;

	/** holds the current (as in iteration) date */
	private DateTime current;

	DailyCalendar(DateTime from, DateTime to) {
		this.from = from;
		this.to = to;
		if (to == null) {
			this.to = new DateTime();
		}
		if (this.from == null) {
			this.from = this.to;
		}		
	}

	private void initForNext() {
		// here we make sure that the next call to "next" will fall on the next
		// wanted day
		current = from.minusDays(1);
	}

	private void initForPrevious() {
		// here we make sure that the next call to "previou" will fall on the
		// previous wanted day
		current = to.plusDays(1);
	}

	/**
	 * Return the next valid day.
	 * 
	 * @return the next valid day as a DateTime.
	 */
	public DateTime next() {
		if (current == null) {
			initForNext();
		}

		current = current.plusDays(1);

		if (current.isAfter(to)) {
			return null;
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
		return (current == null) || (!current.plusDays(1).isAfter(to));
	}

	/**
	 * Return the DateTime iterator.
	 */
	public Iterator<DateTime> iterator() {
		current = null;
        return this;
	}

	/**
	 * Indicates if after a next or previous call we end up in a troncated
	 * period (related to the forcing on upper and lower bounds).
	 * 
	 * @return true if are in the case of a forcing on upper and lower bounds.
	 */
	public boolean isTroncatedPeriod() {
		return false;
	}

	/**
	 * Is not implemented.
	 */
	public void remove() {
		// nothing !!!
	}

	/**
	 * Return the previous valid day.
	 * 
	 * @return the previous valid day as a DateTime.
	 */
	public DateTime previous() {
		if (current == null) {
			initForPrevious();
		}

		current = current.minusDays(1);

		if (current.isBefore(from)) {
			return null;
		} else {
			return current;
		}
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
