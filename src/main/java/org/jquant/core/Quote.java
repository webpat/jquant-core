package org.jquant.core;

import org.joda.time.DateTime;
import org.jquant.core.TimeSerie.Variable;


public class Quote extends TimeValue {

	protected double quote;
	
	
	public Quote(){
		
	}
	
	public Quote(DateTime date,double rate) {
		this.date = date;
		this.quote = rate;
	}

	public Variable getDefaultVariable() {
		return Variable.QUOTE;
	}

	public double getValue(Variable var) {
		return quote;
	}


	public double getValue() {
		return quote;
	}
	
	
	
	public double getQuote() {
		return quote;
	}

	public void setQuote(double quote) {
		this.quote = quote;
	}

	public Quote clone(){
		Quote clone = new Quote(this.date,this.quote);
		
		return clone;
		
	}
	


}
