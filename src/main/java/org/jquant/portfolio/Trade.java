package org.jquant.portfolio;

import org.joda.time.DateTime;
import org.jquant.model.IInstrument;

/**
 * 	A <b>transaction</b> that involves the selling and purchasing of a security
 * <a href="http://www.investopedia.com/terms/t/trade.asp#ixzz1jgKUXZcZ">Read more</a>
 * <p>
 * This class contains mutable and Immutable properties
 * @author patrick.merheb
 *
 */
public class Trade {
	
	private final IInstrument instrument;
	
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
	private PositionSide mouvement;
	
	/**
	 * P&L if > 0 the trade is a Winning trade
	 */
	private double profitAndLoss;
	
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
	public Trade(IInstrument instrument, double quantity, double price, TradeSide side, DateTime timestamp) {
		super();
		this.instrument = instrument;
		this.quantity = quantity;
		this.price = price;
		this.side = side;
		this.timestamp = timestamp;
	}

	/**
	 * 
	 * @return the traded security, an {@link IInstrument}
	 */
	public IInstrument getInstrument() {
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


	public TradeSide getSide() {
		return side;
	}

	
	/**
	 * 
	 * @return The date at wich the Trade takes place 
	 */
	public DateTime getTimestamp() {
		return timestamp;
	}


	public PositionSide getMouvement() {
		return mouvement;
	}

	public void setMouvement(PositionSide mouvement) {
		this.mouvement = mouvement;
	}

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
		result = prime * result + ((instrument == null) ? 0 : instrument.getSymbol().hashCode());
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
		} else if (!instrument.getSymbol().equals(other.instrument.getSymbol()))
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
	
	
	
}
