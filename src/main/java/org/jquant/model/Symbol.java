package org.jquant.model;

import org.jquant.data.JQuantDataProvider;


/**
 * The  <b>provider!code</b> matches a unique instrument in the Jquant Scope 
 * <p>
 * This class is immutable.
 *@author patrick.merheb
 */
public final class Symbol implements Comparable<Symbol> {
	   
    private final String code;
    private final JQuantDataProvider provider;
    private final InstrumentType type;
    
    public Symbol(JQuantDataProvider provider, String code, InstrumentType type) {
    	this.type = type;
    	this.code = code;
    	this.provider = provider;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((provider == null) ? 0 : provider.hashCode());
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
		Symbol other = (Symbol) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (provider != other.provider)
			return false;
		return true;
	}

	public String getCode() {
		return code;
	}

	public JQuantDataProvider getProvider() {
		return provider;
	}

	
	/**
	 * 
	 * @return Le type d'instrument {@link InstrumentType} (EQUITY, FUND, FUTURE, TRACKER ...)
	 */
	public InstrumentType getType() {
		return type;
	}

	public int compareTo(Symbol o) {
		
		return (provider.compareTo(o.provider)+code.compareTo(o.code));
	}

	@Override
	public String toString() {
		return "Symbol [" + provider + "!"+code+"]";
	}

    
   
}

