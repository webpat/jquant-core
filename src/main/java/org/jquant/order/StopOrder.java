package org.jquant.order;

import org.joda.time.DateTime;
import org.jquant.model.InstrumentId;

/**
 * An order to buy or sell a security when its price surpasses a particular point, 
 * thus ensuring a greater probability of achieving a predetermined entry or exit price, 
 * limiting the investor's loss or locking in his or her profit. 
 * Once the price surpasses the predefined entry/exit point, the stop order becomes a <b>market order</b>.
 * <p> Also referred to as a "stop" and/or "stop-loss order". 
 * <a href ="http://www.investopedia.com/terms/s/stoporder.asp#ixzz1p9zo87gZ">Read more </a>
 * @author patrick.merheb
 *
 */
public class StopOrder extends Order {
	
	private final double stopPrice;

	public StopOrder(OrderSide side, InstrumentId instrument, double quantity, double price,String text) {
		super(side, instrument, quantity, text);
		this.stopPrice = price;
	}
	
	public StopOrder(OrderSide side, InstrumentId instrument, double quantity, double price,String text,DateTime created) {
		super(side, instrument, quantity, text,created);
		this.stopPrice = price;
	}

	public double getStopPrice() {
		return stopPrice;
	}

	
	
	
}
