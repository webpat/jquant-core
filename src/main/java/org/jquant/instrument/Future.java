package org.jquant.instrument;

import java.util.Date;

import org.jquant.model.InstrumentId;

/**
 * Future Derivative 
 * @author patrick.merheb
 *
 */
public class Future extends Derivative {

	private Date firstDeliveryDate;
	private Date lastDeliveryDate;
	
	
	
	public Future(InstrumentId symbol) {
		super(symbol);
	}



	public Date getLastDeliveryDate() {
		return lastDeliveryDate;
	}



	public void setLastDeliveryDate(Date lastDeliveryDate) {
		this.lastDeliveryDate = lastDeliveryDate;
	}



	public Date getFirstDeliveryDate() {
		return firstDeliveryDate;
	}



	public void setFirstDeliveryDate(Date firstDeliveryDate) {
		this.firstDeliveryDate = firstDeliveryDate;
	}

}
