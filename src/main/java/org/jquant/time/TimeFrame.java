package org.jquant.time;

/**
 * TODO: Check JODA-TIME Duration, are we overlapping JODA-TIME Here ???
 * Check ALSO PeriodCalculator from Object Lab Kit 
 * # Number of TimeUnit(s)
 * @see TimeUnit 
 * @author JQuant Team
 *
 */
public class TimeFrame implements Comparable<TimeFrame>{

	public int length;
	public TimeUnit unit;
	
	public TimeFrame() {
		
	}
	
	public TimeFrame(int length, TimeUnit timeUnit) {
		this.length = length;
		this.unit = timeUnit;
	}


	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public TimeUnit getUnit() {
		return unit;
	}

	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}

	public int compareTo(TimeFrame arg0) {
		
		TimeFrame period = (TimeFrame) arg0;
		if (this.length*this.unit.getValue()<period.length*period.unit.getValue())
			return -1;
		else if (this.length*this.unit.getValue()>period.length*period.unit.getValue())
			return 1;
		return 0;
	}

	@Override
	public int hashCode() {
		// To Int bits 
		int hashcode = length;
		hashcode ^= unit.hashCode();
		return hashcode;
	}

	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	public String toString()
	{
	    final String TAB = "    ";
	    
	    String retValue = "";
	    
	    retValue = "TimeFrame ( "
	        + super.toString() + TAB
	        + "length = " + this.length + TAB
	        + "unit = " + this.unit + TAB
	        + " )";
	
	    return retValue;
	}
	
	
}
