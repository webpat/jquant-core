package org.jquant.time;

/**
 * TODO: Why not using JODA-TIME Periods
 * (Years Months Weeks Days ...) 
 * Composant of a TimeFrame @see TimeFrame
 * @author JQUANT TEAM 
 *
 */
public enum TimeUnit {
	DAY(1),
	WEEK(7),
	MONTH(30),
	YEAR(365);
	
    private final int value; // day value
    
    private TimeUnit(int value) {
		this.value = value;
	}
    public int getValue() {
		return this.value;
	}
    
    /**
     * Read codes <br/>
     * <ul>
     * <li>M : Months </li>
     * <li>D : Days </li>
     * <li>Y : Years </li>
     * <li>W : Weeks </li>
     *  </ul>
     * @param c
     * @return
     */
    public static  TimeUnit fromCode(Character c){
		c = Character.toUpperCase(c);
    	switch(c){
			case 'M':return MONTH;
			case 'Y':return YEAR;
			case 'D':return DAY;
			case 'W':return WEEK;
		}
    	return null;
    	
    }
    
    public String toLouxorCode(){
		switch(this){
			case MONTH: return "M";
			case YEAR: return "Y";
			case DAY: return "D";
			case WEEK: return "W";
		}
    	return null;
    	
    }
}
