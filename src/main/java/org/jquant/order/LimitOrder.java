package org.jquant.order;

import org.joda.time.DateTime;
import org.jquant.model.InstrumentId;

/**
 * An order placed with a brokerage to buy or sell a set number of shares at a specified price or better. 
 * Limit orders also allow an investor to limit the length of time an order can be outstanding before being canceled. 
 * Depending on the direction of the position, limit orders are sometimes referred to more specifically as 
 * a buy limit order, or a sell limit order.
 * <p>
 * <a href="http://www.investopedia.com/terms/l/limitorder.asp#ixzz1p9xJnHGk">Read more </a>
 * @author patrick.merheb
 *
 */
public class LimitOrder extends Order {

	private final double limitPrice;

	public LimitOrder(OrderSide side, InstrumentId instrument, double quantity, double price, String text) {
		super(side, instrument, quantity, text);
		this.limitPrice = price;
	}
	
	public LimitOrder(OrderSide side, InstrumentId instrument, double quantity, double price, String text,DateTime created) {
		super(side, instrument, quantity, text,created);
		this.limitPrice = price;
	}


	public double getLimitPrice() {
		return limitPrice;
	}
	
}
