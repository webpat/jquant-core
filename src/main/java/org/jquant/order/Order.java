package org.jquant.order;

import org.joda.time.DateTime;
import org.jquant.model.IInstrument;

/**
 * Order POJO
 * <p>
 * can be a Market Order a Stop Order or a Limit Order
 * <p>
 * 
 * @author patrick.merheb
 *
 */
public class Order {

	private final double quantity;
	
	private DateTime executionTime;
	
	private final DateTime creationTime;
	
	private double filledPrice;
	
	private double filledQuantity;

	private final String text;
	
	private OrderStatus status;
	
	private final OrderSide side;
	
	
	private final IInstrument instrument;
	
	
	
	/**
	 * 
	 * @param side {@link OrderSide#BUY} or {@link OrderSide#SELL}
	 * @param instrument The {@link IInstrument}
	 * @param quantity 
	 * @param text 
	 */
	public Order(OrderSide side, IInstrument instrument, double quantity, String text) {
		super();
		this.side = side;
		this.instrument = instrument;
		this.quantity = quantity;
		this.text = text;
		this.creationTime = new DateTime();
	}
	
	/**
	 * 
	 * @param side {@link OrderSide#BUY} or {@link OrderSide#SELL}
	 * @param instrument The {@link IInstrument}
	 * @param quantity
	 * @param text
	 * @param created {@link DateTime} Used for backtesting orders 
	 */
	public Order(OrderSide side, IInstrument instrument, double quantity, String text,DateTime created) {
		super();
		this.side = side;
		this.instrument = instrument;
		this.quantity = quantity;
		this.text = text;
		this.creationTime = created;
	}

	
	
	
	/**
	 * 
	 * @return The quantity that was filled by the broker 
	 */
	public double getFilledQuantity() {
		return filledQuantity;
	}


	public void setFilledQuantity(double filledQuantity) {
		this.filledQuantity = filledQuantity;
	}

	/**
	 * 
	 * @return The price @ which the order was filled
	 */
	public double getFilledPrice() {
		return filledPrice;
	}

	public void setFilledPrice(double filledPrice) {
		this.filledPrice = filledPrice;
	}

	/**
	 * A comment alongside the Order
	 * @return A {@link String} 
	 */
	public String getText() {
		return text;
	}

	
	/**
	 * 
	 * @return Amount of instrument to trade 
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * {@link OrderStatus#CREATED} --> {@link OrderStatus#SUBMITED} --> {@link OrderStatus#FILLED} ... 
	 * @return {@link OrderStatus}
	 */
	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public OrderSide getSide() {
		return side;
	}

	
	/**
	 * @return the {@link DateTime} when the order was EXECUTED by the Broker
	 */
	public DateTime getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(DateTime executionTime) {
		this.executionTime = executionTime;
	}


	/**
	 * 
	 * @return The exchanged instrument 
	 */
	public IInstrument getInstrument() {
		return instrument;
	}


	/**
	 * 
	 * @return Creation Time in JQUANT System
	 */
	public DateTime getCreationTime() {
		return creationTime;
	}

	@Override
	public String toString() {
		return "Order [instrument=" + instrument + ", side=" + side + ", quantity=" + quantity + ", text=" + text + ", status=" + status + "]";
	}



	public enum OrderSide{
		BUY, SELL;
	}
	
	public enum OrderType{
		MARKET,LIMIT,STOP,STOPLIMIT;
	}
	
	
}
