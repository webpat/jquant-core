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
 * Calendrier des fins de mois (sans les WE et jours f�ri�s)
 * Calendrier des jours OUVRABLES (Du lundi au vendredi) par d�faut
 *
 * @author merhebp
 */
class EndOfMonthWorkDayCalendar implements IDateTimeCalendar {

    /** start (min) date */
    private DateTime from;

    /** end (max) date */
    private DateTime to;

    /** holds the current (as in iteration) date */
    private DateTime current;
    
    /** The Date Calculator for business day management*/
    private final LocalDateCalculator cal;
    
 
    
    /**
     * 
     * @param from
     *  left bound of the calendar
     * @param to
     *  right bound of the calendar
     * @param 
     * market calendar name (holidays set interested in). 
     * If there is set of holidays with that name, 
     * it will return a DateCalculator with an empty holiday set (will work on Weekend only).
     */
    public EndOfMonthWorkDayCalendar(DateTime from, DateTime to,MICMarketPlace market) {
        super();
        this.from = from;
        this.to = to;
		if (to == null) {
			this.to = new DateTime();
		}
		if (this.from == null) {
			this.from = this.to;
		}        
        cal = LocalDateKitCalculatorsFactory.getDefaultInstance().
                    getDateCalculator(market.getCode(),HolidayHandlerType.MODIFIED_FOLLOWING);
    }

   
    public DateTime next() {
        if (current == null) { // INIT 
            current = from.dayOfMonth().withMaximumValue();
            cal.setStartDate(current.toLocalDate());
            return cal.getCurrentBusinessDate().toDateTimeAtMidnight();
            
        }

        // NEXT 
        current = current.plusMonths(1).dayOfMonth().withMaximumValue();
        cal.setCurrentBusinessDate(current.toLocalDate());
        
        // MANAGE BOUNDARY
        if (current.isAfter(to)) {
            // we just went over the last day
            if ((current.year().get() == to.year().get())
                    && (current.monthOfYear().get() == to.monthOfYear().get())) {

                // but we are on the same month so we send back to (the last
                // day)
                cal.setCurrentBusinessDate(to.toLocalDate());
                return cal.getCurrentBusinessDate().toDateTimeAtMidnight();
            } else {
                return null;
            }
        } else {
            return cal.getCurrentBusinessDate().toDateTimeAtMidnight();
        }
    }
    
    
    public DateTime previous() {
        
        
        // INIT 
        if (current == null) {
            current = to;
            cal.setCurrentBusinessDate(current.toLocalDate()); 
            return cal.getCurrentBusinessDate().toDateTimeAtMidnight();
        } else if (to.isEqual(to.dayOfMonth().withMaximumValue())) {
            current = to;
            cal.setCurrentBusinessDate(current.toLocalDate());
            return cal.getCurrentBusinessDate().toDateTimeAtMidnight();       
        } else {
            current = to;
        }

        // PREVIOUS 
        current = current.minusMonths(1).dayOfMonth().withMaximumValue();
        cal.setCurrentBusinessDate(current.toLocalDate());

        // MANAGE BOUNDARY
        if (current.isBefore(from)) {
            // we just went over the first day
            DateTime fromMinusOne = from.minusMonths(1).plusDays(1);
            if (!from.isEqual(from.dayOfMonth().withMaximumValue())
                    && fromMinusOne.isBefore(current)) {
                cal.setCurrentBusinessDate(from.toLocalDate());
                return cal.getCurrentBusinessDate().toDateTimeAtMidnight();
            } else {
                return null;
            }
        } else {
            return cal.getCurrentBusinessDate().toDateTimeAtMidnight();
        }
    }
    
    public DateTime getEndDay() {
      return to;
    }

    public DateTime getStartDay() {
        return from;
    }

    public boolean isTroncatedPeriod() {
        return false;
    }

  

    public Iterator<DateTime> iterator() {
     
        return this;
    }

    public boolean hasNext() {
        if (current == null) {
            return true;
        } else {
            DateTime dd = current.plusMonths(1).dayOfMonth().withMaximumValue();

            if (dd.isAfter(to)) {
                // we just went over the last day
                if ((dd.year().get() == to.year().get())
                        && (dd.getMonthOfYear() == to.getMonthOfYear())) {

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

    public void reset() {
		current = from;
		
	}

    public void remove() {
        // NOT IMPLEMENTED

    }

}
