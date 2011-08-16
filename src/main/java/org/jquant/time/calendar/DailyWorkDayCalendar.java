/*
 * Created on 9 janv. 07
 */
package org.jquant.time.calendar;

import java.util.Iterator;

import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.joda.LocalDateCalculator;
import net.objectlab.kit.datecalc.joda.LocalDateKitCalculatorsFactory;

import org.joda.time.DateTime;
import org.jquant.model.MICMarketPlace;


/**
 * Takes a DailyBrowser, add the Holidays Mgt (Working day only)
 * 
 * 
 * 
 * @author merhebp
 */
class DailyWorkDayCalendar implements IReportingDayCalendar {

    /** start (min) date */
    private DateTime from;

    /** end (max) date */
    private DateTime to;

    /** holds the current (as in iteration) date */
    private DateTime current;
    
    
    /** The Date Calculator for business day management*/
    private LocalDateCalculator cal;

    /**
     * 
     * @param from
     *  left bound of the calendar
     * @param to
     *  right bound of the calendar
     * @param calendarName
     * market calendar name (holidays set interested in). 
     * If there is set of holidays with that name, 
     * it will return a DateCalculator with an empty holiday set (will work on Weekend only).
     */
    DailyWorkDayCalendar(DateTime from, DateTime to,MICMarketPlace mic) {
        this.from = from;
        this.to = to;
		if (to == null) {
			this.to = new DateTime();
		}
		if (this.from == null) {
			this.from = this.to;
		}		
        
        cal = LocalDateKitCalculatorsFactory.getDefaultInstance().
        getDateCalculator(mic.getCode(),HolidayHandlerType.FORWARD); //TYPE FORWARD
    }

    private void initForNext() {
        // here we make sure that the next call to "next" will fall on the next
        // wanted day
        current = from.minusDays(1);
        
    }

    private void initForPrevious() {
        // here we make sure that the next call to "previous" will fall on the
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
            cal.setStartDate(current.toLocalDate());
        }

        current = current.plusDays(1);
        cal.setCurrentBusinessDate(current.toLocalDate());
        current = cal.getCurrentBusinessDate().toDateTimeAtMidnight();
        
        if (current.isAfter(to)) {
            return null;
        } else {
            return cal.getCurrentBusinessDate().toDateTimeAtMidnight();
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
        cal.setCurrentBusinessDate(current.toLocalDate());
        current = cal.getCurrentBusinessDate().toDateTimeAtMidnight();
        
        
        if (current.isBefore(from)) {
            return null;
        } else {
            return cal.getCurrentBusinessDate().toDateTimeAtMidnight();
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

    public void reset(){
    	current = from;
    }
    
}
