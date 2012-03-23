package org.jquant.model;

import org.jquant.data.JQuantDataProvider;


/**
 * This class is used to uniquely identify a traded instrument 
 * <p>
 * The  <b>provider!code!exchange</b> matches a unique instrument in the Jquant Scope 
 * <p>
 * This class is immutable.
 *@author patrick.merheb
 */
public final class InstrumentId implements Comparable<InstrumentId> {
	   
    private final String code;
    private final JQuantDataProvider provider;
    private final InstrumentType type;
    private final MICMarketPlace exchange;
    private final Currency currency;
    
    public InstrumentId(JQuantDataProvider provider, String code, InstrumentType type, MICMarketPlace exchange,Currency currency) {
    	this.type = type;
    	this.code = code;
    	this.provider = provider;
    	this.exchange = exchange;
    	this.currency = currency;
    }

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((exchange == null) ? 0 : exchange.hashCode());
		result = prime * result + ((provider == null) ? 0 : provider.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		InstrumentId other = (InstrumentId) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (currency != other.currency)
			return false;
		if (exchange == null) {
			if (other.exchange != null)
				return false;
		} else if (!exchange.equals(other.exchange))
			return false;
		if (provider != other.provider)
			return false;
		if (type != other.type)
			return false;
		return true;
	}


	/**
	 * Bloomberg folks call that Ticker, Reuters folks call that RIC 
	 * ex : IBM, MSFT Equity, AAPL 
	 * @return the code used to identify a particular security listed on an exchange 
	 */
	public String getCode() {
		return code;
	}

	/**
	 * ex : {@link JQuantDataProvider#BLOOMBERG}, {@link JQuantDataProvider#LOUXOR}
	 * @return The provider where this code is used 
	 */
	public JQuantDataProvider getProvider() {
		return provider;
	}

	
	/**
	 * The exchange place 
	 * @return The {@link MICMarketPlace} where the equity is traded 
	 */
	public MICMarketPlace getExchange() {
		return exchange;
	}

	
	
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * 
	 * @return Le type d'instrument {@link InstrumentType} (EQUITY, FUND, FUTURE, TRACKER ...)
	 */
	public InstrumentType getType() {
		return type;
	}

	public int compareTo(InstrumentId o) {
		
		return (provider.compareTo(o.provider)+code.compareTo(o.code));
	}

	@Override
	public String toString() {
		return "InstrumentId [" + provider + "!"+code+"]";
	}

    
   
}

