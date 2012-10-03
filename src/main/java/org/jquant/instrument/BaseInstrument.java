package org.jquant.instrument;

import org.jquant.model.Currency;
import org.jquant.model.IInstrument;
import org.jquant.model.InstrumentId;
import org.jquant.model.MarketIdentifierCode;



/**
 * <b>Description :</b> This abstract class represents contains the basics of an instrument specification 
 * (type, market, expiry date, currency ...)
 * <p>For most specific instruments attributes  see specific child classes
 * <p>For a given Data Provider a symbol/market tuple should be unique  
 * <b>History:</b><br>
 * @author patrick.merheb
 * @see InstrumentId
 * @see MarketIdentifierCode
 * @see Currency
 */
public abstract class BaseInstrument implements IInstrument {

	protected InstrumentId symbol = null;	

	/**
	 * Constructor
	 * @param symbol a {@link InstrumentId} 
	 */	
	public BaseInstrument(InstrumentId symbol){
		this.symbol = symbol;
	}

	
	
	public Currency getCurrency() {
		return symbol.getCurrency();
	}
	
	
	public MarketIdentifierCode getMarket() {
		return symbol.getExchange();
	}		
	
	public InstrumentId getId() {
		return symbol;
	}
	
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
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
		BaseInstrument other = (BaseInstrument) obj;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Instrument [symbol=" + symbol.getCode() + ", type=" + symbol.getType() + "]";
	}
	
	
	
}
