package org.jquant.model;

import org.joda.time.DateTime;

public class CashDividend {

	protected DateTime effectiveDate;
	private double value;
	
	public CashDividend() {
		
	}
	
	public CashDividend(DateTime effectiveDate, double value) {
		this.effectiveDate = effectiveDate;
		this.value = value;
	}

	public DateTime getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(DateTime effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	
}
