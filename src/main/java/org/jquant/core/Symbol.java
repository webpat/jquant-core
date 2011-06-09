/****

    activequant - activestocks.eu

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.

	
	contact  : contact@activestocks.eu
    homepage : http://www.activestocks.eu

****/
package org.jquant.core;

import org.jquant.model.MarketDataProvider;


/**
 * La paire provider!code représente un instrument de manière unique chez un provider.
 * This class is immutable.
 * <br>
 * <b>History:</b><br>
 *
 */
public final class Symbol implements Comparable<Symbol> {
	   
    private final String name;
    protected MarketDataProvider provider = MarketDataProvider.BLOOMBERG;
    
    public Symbol(MarketDataProvider provider, String name) {
    	this.name = name;
    	this.provider = provider;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (provider != other.provider)
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public MarketDataProvider getProvider() {
		return provider;
	}

	public int compareTo(Symbol o) {
		
		return (provider.compareTo(o.provider)+name.compareTo(o.name));
	}

    
   
}

