package org.jquant.model;

import org.joda.time.DateTime;

public class DividendYield extends Quote implements Comparable<DividendYield>{

	private double			timeToMaturity;
	
	public DividendYield(DateTime date, double price, double timeToMaturity){
		super(date, price);
		this.timeToMaturity = timeToMaturity;
	}

	public double getTimeToMaturity() {
		return timeToMaturity;
	}

	public void setTimeToMaturity(double timeToMaturity) {
		this.timeToMaturity = timeToMaturity;
	}

	public int compareTo(DividendYield arg0) {
		if (this.timeToMaturity<arg0.getTimeToMaturity()) return -1;
		else if (this.timeToMaturity==arg0.getTimeToMaturity()) return 0;
		else return 1;
	}
}
