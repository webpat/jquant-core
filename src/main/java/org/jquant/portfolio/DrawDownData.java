package org.jquant.portfolio;

import java.io.Serializable;

/**
 * Transfer Object with draw down properties 
 * @author patrick.merheb
 *
 */
public class DrawDownData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4992387532439377102L;

	public final double maxDrawDown;

	public final int timeInMaxDD;

	public final int timeToRecover;

	public final int fromIndex;

	public final int toIndex;

	

	public DrawDownData(double maxDrawDown, int timeInMaxDD, int timeToRecover, int fromIndex, int toIndex) {
		super();
		this.maxDrawDown = maxDrawDown;
		this.timeInMaxDD = timeInMaxDD;
		this.timeToRecover = timeToRecover;
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}



	/**
	 * 
	 * @return The maximum dd value in the serie
	 */
	public double getMaxDrawDown() {
		return maxDrawDown;
	}

	

	/**
	 * 
	 * @return the number of elements in the serie during the dd
	 */
	public int getTimeInMaxDD() {
		return timeInMaxDD;
	}

	
	/**
	 * 
	 * @return Number of elements for  recovery time in the DD 
	 */
	public int getTimeToRecover() {
		return timeToRecover;
	}

	
	/**
	 * 
	 * @return Index in the serie where the DD starts
	 */
	public int getFromIndex() {
		return fromIndex;
	}

	
	/**
	 * 
	 * @return Index in the serie where the DD ends
	 */
	public int getToIndex() {
		return toIndex;
	}

}