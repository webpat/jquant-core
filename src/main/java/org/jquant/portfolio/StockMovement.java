package org.jquant.portfolio;

import org.jquant.model.IInstrument;

/**
 * used in the  {@link Portfolio} inventory
 * <p>
 * natural key is asset/price
 * @author patrick.merheb
 *
 */
public final class StockMovement {


	private final IInstrument instrument;
	
	private final Double price;
	
	private final Trade trade;
	
	
	public StockMovement(IInstrument instrument, Double price, Trade trade) {
		super();
		this.instrument = instrument;
		this.price = price;
		this.trade = trade;
		
	}



	public Double getPrice() {
		return price;
	}



	public IInstrument getInstrument() {
		return instrument;
	}
	
	
	

	public Trade getTrade() {
		return trade;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((instrument == null) ? 0 : instrument.getSymbol().hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
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
		StockMovement other = (StockMovement) obj;
		if (instrument == null) {
			if (other.instrument != null)
				return false;
		} else if (!instrument.equals(other.instrument))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;

		return true;
	}



	@Override
	public String toString() {
		return "StockMovement [" + trade.getSide() + " " + instrument + "@"+ price +"]";
	}

	
	
}
