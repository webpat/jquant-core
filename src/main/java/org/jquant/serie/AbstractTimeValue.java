package org.jquant.serie;

import org.joda.time.DateTime;

/**
 * The Mother class of all Time Values 
 * @author patrick.merheb
 *
 */
public abstract class AbstractTimeValue implements ITimeValue {

	protected DateTime date;


	public AbstractTimeValue(DateTime date) {
		super();
		this.date = date;
	}

	/**
	 * @return moment in time where the Value stands 
	 */
	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}



}