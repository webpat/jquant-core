package org.jquant.time.calendar;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.joda.LocalDateKitCalculatorsFactory;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.jquant.model.MarketIdentifierCode;


/**
 * DOCUMENT ME!
 * 
 * @author JQUANT TEAM 
 */
public final class CalendarUtils {

	// TODO create and manage for a market place (EX: UK, US, ...)
	public static DateTime getBusinessDayGoingBackward(DateTime aDate, MarketIdentifierCode marketPlace) {
		// create or get the Holidays
		// final Set<DateTime> holidays = new HashSet<DateTime>();
		// holidays.add(new DateTime("2006-08-28"));

		// register the holidays (any calculator with name "UK" asked from now
		// on will receive a reference to this set
		// DefaultDateCalculatorFactory.getDefaultInstance().registerHolidays("UK",
		// holidays);

		if (marketPlace == null) {
			marketPlace = MarketIdentifierCode.NO_MIC;
		}
		
		// ask for a DateTime calculator for "UK" (even if a new set of
		// holidays is registered, this one calculator is not affected
		DateCalculator<LocalDate> cal = LocalDateKitCalculatorsFactory.getDefaultInstance()
				.getDateCalculator(marketPlace.getCode(), HolidayHandlerType.BACKWARD);
		cal.setStartDate(aDate.toLocalDate()); // this also sets the current business date.

		return cal.getCurrentBusinessDate().toDateTimeAtMidnight();
	}

	// TODO create and manage for a market place (EX: UK, US, ...)
	public static DateTime getBusinessDayGoingForward(DateTime aDate, MarketIdentifierCode marketPlace) {
		// create or get the Holidays
		// final Set<DateTime> holidays = new HashSet<DateTime>();
		// holidays.add(new DateTime("2006-08-28"));

		// register the holidays (any calculator with name "UK" asked from now
		// on will receive a reference to this set
		// DefaultDateCalculatorFactory.getDefaultInstance().registerHolidays("UK",
		// holidays);

		if (marketPlace == null) {
			marketPlace = MarketIdentifierCode.NO_MIC;
		}
		
		// ask for a DateTime calculator for "UK" (even if a new set of
		// holidays is registered, this one calculator is not affected
		DateCalculator<LocalDate> cal = LocalDateKitCalculatorsFactory.getDefaultInstance()
				.getDateCalculator(marketPlace.getCode(), HolidayHandlerType.FORWARD);
		cal.setStartDate(aDate.toLocalDate()); // this also sets the current business date.

		return cal.getCurrentBusinessDate().toDateTimeAtMidnight();
	}


	/**
	 * DOCUMENT ME!
	 * @param monthsBack
	 *            DOCUMENT ME!
	 * @param aDay
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws CalendarException
	 *             DOCUMENT ME!
	 */
	public static DateTime getXMonthAgo(int monthsBack, DateTime aDay) {

		
        DateTime result = aDay.minusMonths(monthsBack);
  		return result;
	}

	/**
	 * DOCUMENT ME!
	 * @param month
	 *            DOCUMENT ME!
	 * @param year
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public static DateTime getLastDayOfPrecedingMonth(DateTime date) {
		
        DateTime endOfPrecedingMonth = date.minusMonths(1).dayOfMonth().withMaximumValue(); 
        
		return endOfPrecedingMonth;
	}

	
	

	/**
     * @param aDate
     * @return
	 */
    public static DateTime getLastDayOfFollowingMonth(DateTime date) {
       
        DateTime endOfFollowingMonth = date.plusMonths(1).dayOfMonth().withMaximumValue(); 

		return endOfFollowingMonth;
	}

	
	/**
	
     * 
	 * @param month
	 *            DOCUMENT ME!
	 * @param year
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws CalendarException
	 *             DOCUMENT ME!
	 */
	public static DateTime getFirstDayOfPrecedingMonth(DateTime date){
        DateTime startOfPrecedingMonth = date.minusMonths(1).dayOfMonth().withMinimumValue(); 

		return startOfPrecedingMonth;
	}

	

	/**

     * @param month
     * @param year
     * @return
     * @throws CalendarException
	 */
    public static DateTime getFirstDayOfFollowingMonth(DateTime date){
        
        DateTime startOfFollowingMonth = date.plusMonths(1).dayOfMonth().withMinimumValue(); 

        return startOfFollowingMonth;
	}
    
	
//    public static DateTime getPreviousWorkDay(DateTime endDate,Period period, MarketIdentifierCode marketPlace) {
//    	
//    	DateTime previousWorkDay = null;
//		if (marketPlace == null) {
//			marketPlace = MarketIdentifierCode.NO_MIC;
//		}
//		
//		if (durationType.equals(DurationFieldType.days()))
//		{
//			DateTime tmpDate = endDate;
//			for (int i=0;i<period.length;i++)
//				tmpDate = getBusinessDayGoingForward(tmpDate.minusDays(1),marketPlace);
//			previousWorkDay = tmpDate;
//		}else if (durationType.equals(DurationFieldType.weeks()){
//		
//			previousWorkDay = getBusinessDayGoingForward(endDate.minusWeeks(period.length),marketPlace);
//		}else if (durationType.equals(DurationFieldType.years())){
//		
//			DateCalculator<LocalDate> cal = LocalDateKitCalculatorsFactory.getDefaultInstance().
//        		getDateCalculator(marketPlace.getCode(),HolidayHandlerType.MODIFIED_FOLLOWING);
//			if (isEndOfMonth(endDate)) {
//				cal.setStartDate(endDate.minusMonths(period.length).dayOfMonth().withMaximumValue().toLocalDate());
//			} else {
//				cal.setStartDate(endDate.minusMonths(period.length).toLocalDate());
//			}
//			previousWorkDay = cal.getCurrentBusinessDate().toDateTimeAtMidnight();
//			break;
//		}
//		case YEAR:
//		{
//			TimeFrame periodInMonths = new TimeFrame(period.length*12,TimeUnit.MONTH);
//			previousWorkDay = getLastWorkDay(endDate,periodInMonths,marketPlace);
//			break;
//		}
//		}
//		
//		return previousWorkDay;
//    }
    
	/**
	 * Get Last day of a period using convention business days associated to a specific market place
	 *
	 * @return Last day of the period  
	 */
//    public static DateTime getLastWorkDay(DateTime startDate, TimeFrame period, MarketIdentifierCode marketPlace) {
//    	
//    	DateTime lastWorkDay = null;
//		if (marketPlace == null) {
//			marketPlace = MarketIdentifierCode.NO_MIC;
//		}
//
//		switch (period.unit)
//		{
//		case DAY:
//		{
//			DateTime tmpDate = startDate;
//			for (int i=0;i<period.length;i++)
//				tmpDate = getBusinessDayGoingForward(tmpDate.plusDays(1),marketPlace);
//			lastWorkDay = tmpDate;
//			break;
//		}
//		case WEEK:
//			lastWorkDay = getBusinessDayGoingForward(startDate.plusWeeks(period.length),marketPlace);
//			break;
//		case MONTH:
//		{
//			DateCalculator<LocalDate> cal = LocalDateKitCalculatorsFactory.getDefaultInstance().
//        		getDateCalculator(marketPlace.getCode(),HolidayHandlerType.MODIFIED_FOLLOWING);
//			if (isEndOfMonth(startDate)) {
//				cal.setStartDate(startDate.plusMonths(period.length).dayOfMonth().withMaximumValue().toLocalDate());
//			} else {
//				cal.setStartDate(startDate.plusMonths(period.length).toLocalDate());
//			}
//			lastWorkDay = cal.getCurrentBusinessDate().toDateTimeAtMidnight();
//			break;
//		}
//		case YEAR:
//		{
//			TimeFrame periodInMonths = new TimeFrame(period.length*12,TimeUnit.MONTH);
//			lastWorkDay = getLastWorkDay(startDate,periodInMonths,marketPlace);
//			break;
//		}
//		}
//     	return lastWorkDay;
//    }
    
	/**
	 * Says if a date is at the end of month
	 *
	 * @return Return true if date is at the end of month
	 */
    public static boolean isEndOfMonth(DateTime date) {
    	return (date.plusDays(1).getDayOfMonth()==1);
    }



	public static DateTime getOldestDate(DateTime... date){
		DateTime oldestDate = date[0];
		for (int i = 1; i < date.length; i++) {
			if (date[i].compareTo(oldestDate)<=0)
				oldestDate = date[i];
		}
		return oldestDate;
	}
	
	public static DateTime getYoungestDate(DateTime... date){
		DateTime youngestDate = date[0];
		for (int i = 1; i < date.length; i++) {
			if (date[i].compareTo(youngestDate)>=0)
				youngestDate = date[i];
		}
		return youngestDate;
	}
	
	public static int getNbBusinessDays(DateTime startDate, DateTime lastDate, MarketIdentifierCode marketPlace) {
		int nbBusinessDays = 0;
		DateTime tmpDate = startDate;
		while (tmpDate.compareTo(lastDate)<0) {
			tmpDate = getBusinessDayGoingForward(tmpDate.plusDays(1),marketPlace);
			nbBusinessDays++;
		}
		return nbBusinessDays;	
	}
    
}
