package org.jquant.time.calendar;

import java.util.Iterator;

import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 * This class is used to browse dates in a weekly manner.
 * 
 * This is using all days of the calendar. No special treatment for holidays or
 * weekends...
 * 
 * @author Patrick Ducharme-Boutin
 */
class WeeklyCalendar implements IDateTimeCalendar {

	/** start (min) date */
	private DateTime from;

	/** end (max) date */
	private DateTime to;

	/** holds the current (as in iteration) date */
	private DateTime current;

	/** holds the day of the week we are running on */
	private DayOfTheWeek dow;

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
	 * 
	 * @param from
	 *            the smallest month of the browser.
	 * 
	 * @param to
	 *            the biggest month of the browser.
	 * 
	 * @param dow
	 *            the day of the week we are interested in.
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
	 * EX: to is '2006-12-07' and the current value is '2006-12-06' if
	 * forceUpperBound is true, a call to next will return '2006-12-07'. If
	 * false it will return null.
	 */
	WeeklyCalendar(DateTime from, DateTime to, DayOfTheWeek dow, boolean forceLowerBound,
			boolean forceUpperBound) {
		this.from = from;
		this.to = to;
		if (to == null) {
			this.to = new DateTime();
		}
		if (this.from == null) {
			this.from = this.to;
		}
		this.dow = dow;
		this.forceLowerBound = forceLowerBound;
		this.forceUpperBound = forceUpperBound;
	}

	private void initForNext() {
		// here we make sure that the next call to "next" will fall on the next
		// wanted day of the week
		DayOfTheWeek startDow = DayOfTheWeek.get(from.dayOfWeek().get());
		int nbSteps = (dow.getDayOfWeek() - startDow.getDayOfWeek() + 7) % 7;
		current = from.minusDays(7 - nbSteps);
	}

	private void initForPrevious() {
		// here we make sure that the next call to "previous" will fall on the
		// next
		// wanted day of the week
		DayOfTheWeek startDow = DayOfTheWeek.get(to.dayOfWeek().get());
		int nbSteps = (startDow.getDayOfWeek() - dow.getDayOfWeek() + 7) % 7;
		current = to.plusDays(7 - nbSteps);
	}

	/**
	 * Return the next valid day.
	 * 
	 * @return the next valid day as a DateTime.
	 */
	public DateTime next() {
		troncatedPeriod = false;
		if (current == null) {
			if (forceLowerBound) {
				if (dow.getDayOfWeek() == from.dayOfWeek().get()) {
					current = from;
					return current;
				} else {
					initForNext();
					troncatedPeriod = true;
					return from;
				}
			} else {
				initForNext();
			}
		}

		current = current.plusDays(7);

		if (current.isAfter(to)) {
			if (forceUpperBound && (Days.daysBetween(to, current).getDays() < 6)) {
				troncatedPeriod = true;
				return to;
			}

			return null;
		} else {
			return current;
		}
	}

	/**
	 * Return the previous valid day.
	 * 
	 * @return the previous valid day as a DateTime.
	 */
	public DateTime previous() {
		troncatedPeriod = false;
		if (current == null) {
			if (forceUpperBound) {
				if (dow.getDayOfWeek() == to.dayOfWeek().get()) {
					current = to;
					return current;
				} else {
					initForPrevious();
					troncatedPeriod = true;
					return to;
				}
			} else {
				initForPrevious();
			}
		}

		current = current.minusDays(7);

		if (current.isBefore(from)) {
			// we just went over the first day
			if (forceLowerBound && (Days.daysBetween(current, from).getDays() < 7)) {
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
	 * Return true if the iterator has more elements.
	 * 
	 * @return boolean.
	 */
	public boolean hasNext() {
		if (current == null) {
			return true;
		} else {
			DateTime dd = current.plusDays(7);

			if (dd.isAfter(to)) {
				if (forceUpperBound && (Days.daysBetween(to, dd).getDays() < 6)) {
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

	@Override
	public String toString() {
		return "WeeklyCalendar [from=" + from + ", to=" + to + ", current="
				+ current + "]";
	}
	
	
}
