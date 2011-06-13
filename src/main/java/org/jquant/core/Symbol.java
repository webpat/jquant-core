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

import org.jquant.data.DataProvider;


/**
 * La paire provider!code représente un instrument de manière unique chez un provider.
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

    
   
}

