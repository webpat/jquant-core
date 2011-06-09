package org.jquant.time.calendar;

/**
 * @author Patrick Ducharme-Boutin
 * 
 * This class is an enum used to obtain the different days of the week.
 */

public enum DayOfTheWeek {
	MONDAY(1), 
	
	TUESDAY(2), 
	
	WEDNESDAY(3), 
	
	THURSDAY(4), 
	
	FRIDAY(5), 
	
	SATURDAY(6), 
	
	SUNDAY(7);

	private int dayOfWeek;

	private DayOfTheWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public static DayOfTheWeek get(int d) {
		switch (d) {
		case 1:
			return MONDAY;
		case 2:
			return TUESDAY;
		case 3:
			return WEDNESDAY;
		case 4:
			return THURSDAY;
		case 5:
			return FRIDAY;
		case 6:
			return SATURDAY;
		case 7:
			return SUNDAY;
		}

		return null;
	}
}
