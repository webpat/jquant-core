package org.jquant.portfolio;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.jquant.model.InstrumentId;

/**
 * 	A <b>transaction</b> that involves the selling and purchasing of a security
 * <a href="http://www.investopedia.com/terms/t/trade.asp#ixzz1jgKUXZcZ">Read more</a>
 * <p>
 * This class contains mutable and Immutable properties
 * @author patrick.merheb
 *
 */
public class Trade implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9173239730375062100L;

	private final InstrumentId instrument;
	
	/**
	 * How many securities were traded
	 * TODO: Il faudra certainement mettre des BigDecimal
	 */
	private final double quantity;
	
	/**
	 * The total price of the transaction
	 */
	private final double price;
	
	/**
	 * Buy or sell 
	 */
	private final TradeSide side;
	
	/**
	 * ENTER, EXIT, STRENGTHEN, RELIEVE your position, what is the purpose of this trade
	 */
	private TradeStatus status;
	
	/**
	 * P&L if > 0 the trade is a Winning trade
	 * <p>
	 * if {@link Double#NaN} then the trade is an opening trade 
	 */
	private double profitAndLoss = Double.NaN;
	
	/**
	 * The date at wich the Trade takes place 
	 */
	private final DateTime timestamp;
	
	/**
	 * Default constructor
	 * @param instrument
	 * @param quantity
	 * @param price
	 * @param side
	 * @param timestamp
	 */
	public Trade(TradeSide side,InstrumentId instrument, double quantity, double price,  DateTime timestamp) {
		super();
		this.instrument = instrument;
		this.quantity = quantity;
		this.price = price;
		this.side = side;
		this.timestamp = timestamp;
		this.status = TradeStatus.OPEN;
		
	}
	
	@Override
	protected Trade clone()  {
		Trade dolly = new Trade(this.side,this.instrument,this.quantity, this.price,this.timestamp);
		return dolly;
	}

	/**
	 * 
	 * @return the traded security, an {@link InstrumentId}
	 */
	public InstrumentId getInstrument() {
		return instrument;
	}


	/**
	 * 
	 * @return How many securities were traded
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * 
	 * @return The total price of the transaction
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * 
	 * @return the unit price of the transaction
	 */
	public double getUnitPrice(){
		return price/quantity;
	}
	
	/**
	 * {@link TradeSide#BUY} or {@link TradeSide#SELL}
	 * @return the {@link TradeSide}
	 */
	public TradeSide getSide() {
		return side;
	}

	
	/**
	 * @return The date at wich the Trade takes place 
	 */
	public DateTime getTimestamp() {
		return timestamp;
	}

	/**
	 * Is it an {@link TradeStatus#OPEN} Trade or a {@link TradeStatus#CLOSED} Trade 
	 * @return The {@link TradeStatus}
	 */
	public TradeStatus getStatus() {
		return status;
	}

	public void setStatus(TradeStatus status) {
		this.status = status;
	}

	/**
	 * Only valid for a Closing Trade
	 * @return get the P&L associated with this trade 
	 */
	public double getProfitAndLoss() {
		return profitAndLoss;
	}

	public void setProfitAndLoss(double profitAndLoss) {
		this.profitAndLoss = profitAndLoss;
	}

	@Override
	public String toString() {
		return "Trade [side=" + side + ", instrument=" + instrument + ", quantity=" + quantity + ", price=" + price + ", timestamp=" + timestamp + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((instrument == null) ? 0 : instrument.hashCode());
		long temp;
		temp = Double.doubleToLongBits(quantity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((side == null) ? 0 : side.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trade other = (Trade) obj;
		if (instrument == null) {
			if (other.instrument != null)
				return false;
		} else if (!instrument.equals(other.instrument))
			return false;
		if (Double.doubleToLongBits(quantity) != Double.doubleToLongBits(other.quantity))
			return false;
		if (side != other.side)
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}
	
	
	
	public enum TradeStatus {
		OPEN,CLOSED
	}
	
	public enum TradeSide {
		BUY,SELL;
	}
	
	
}
