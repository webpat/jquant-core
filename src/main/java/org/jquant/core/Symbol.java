package org.jquant.core;

import org.jquant.data.DataProvider;


/**
 * La paire provider!code repr�sente un instrument de manière unique chez un provider.
 * This class is immutable.
 * <br>
 * <b>History:</b><br>
 *
 */
public final class Symbol implements Comparable<Symbol> {
	   
    private final String id;
    private final DataProvider provider;
    
    public Symbol(DataProvider provider, String name) {
    	this.id = name;
    	this.provider = provider;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (provider != other.provider)
			return false;
		return true;
	}

	public String getId() {
		return id;
	}

	public DataProvider getProvider() {
		return provider;
	}

	public int compareTo(Symbol o) {
		
		return (provider.compareTo(o.provider)+id.compareTo(o.id));
	}

	@Override
	public String toString() {
		return "Symbol [" + provider + "!"+id+"]";
	}

    
   
}

