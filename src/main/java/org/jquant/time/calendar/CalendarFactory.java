package org.jquant.time.calendar;

import org.joda.time.DateTime;
import org.jquant.model.MarketIdentifierCode;


/**
 * This is a factory used to get the different calendar browser.
 * 
 * @author Patrick Ducharme-Boutin
 */
public abstract class CalendarFactory {

	/**
	 * 
	 * @param from start Date
	 * @param to end Date
	 * @return a Daily calendar
	 */
	public static IDateTimeCalendar getDailyBrowser(DateTime from, DateTime to) {
		return new DailyCalendar(from, to);
	}

	/**
	 * Return a daily calendar with the holidays and the week ends off 
	 * @param from start Date
	 * @param to end Date
	 * @param mic a {@link MarketIdentifierCode}
	 * @return a Daily calendar of the trading days, If mic is <code>null</code> week-ends are skipped
	 */
	public static IDateTimeCalendar getDailyTradingDayBrowser(DateTime from, DateTime to,
            MarketIdentifierCode mic) {
        return new DailyTradingDayCalendar(from, to, mic);
    }
	

	/**
	 * Return a weekly date browser/iterator, ...
	 * 
	 * @param from
	 *            the oldest month of the browser.
	 * 
	 * @param to
	 *            the most recent month of the browser.
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
	 * @return a Weekly Calendar
	 */
	public static IDateTimeCalendar getWeeklyBrowser(DateTime from, DateTime to,
			DayOfTheWeek dow, boolean forceLowerBound, boolean forceUpperBound) {
		return new WeeklyCalendar(from, to, dow, forceLowerBound, forceUpperBound);
	}

	/**
	 * Return a monthly date browser/iterator, ...
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
	 * EX: from is '2006-09-08' and the current value is '2006-10-07' if
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
	 * @return a Montly Calendar
	 */
	public static IDateTimeCalendar getMonthlyBrowser(DateTime from, DateTime to,
			boolean forceLowerBound, boolean forceUpperBound) {
		return new MonthlyCalendar(from, to, forceLowerBound, forceUpperBound);
	}

	/**
	 * Return an end of month date browser/iterator, ...
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
	 * EX: from is '2006-09-08' and the current value is '2006-09-29' if
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
	public static IDateTimeCalendar getEndOfMonthBrowser(DateTime from, DateTime to,
			boolean forceLowerBound, boolean forceUpperBound) {
		return new EndOfMonthCalendar(from, to, forceLowerBound, forceUpperBound);
	}
    
    /**
     * Return a EOM Monthly Browser that takes care of the week-ends 
     * @param from
     *      the smallest month of the browser.
     * @param to
     *      the biggest month of the browser.
     * @param calendarName
     *      calendar name (holidays set interested in). 
     *      If there is set of holidays with that name, 
     *      it will return a DateCalculator with an empty holiday set 
     *      (will work on Weekend only).
     *       
     */
    public static IDateTimeCalendar getEndOfMonthWorkDayBrowser(DateTime from, DateTime to,
           MarketIdentifierCode mic) {
        return new EndOfMonthWorkDayCalendar(from, to, mic);
    }
    
    
    
    
	/**
	 * Returns a start of month date browser/iterator, ...
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
	public static IDateTimeCalendar getStartOfMonthBrowser(DateTime from, DateTime to,
			boolean forceLowerBound, boolean forceUpperBound) {
		return new StartOfMonthCalendar(from, to, forceLowerBound, forceUpperBound);
	}	
    
}
