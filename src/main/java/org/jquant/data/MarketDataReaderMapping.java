package org.jquant.data;

/**
 * Interface to be implemented by objects that define a mapping between
 * a {@link JQuantDataProvider} and  MarketDataReader objects.
 * 
 * @author patrick.merheb
 *
 */
public interface MarketDataReaderMapping {

	/**
	 * Returns a MarketDataReader according to the MarketData Layer provider
	 * @param provider a {@link JQuantDataProvider} (Bloomberg, Louxor, ...)
	 * @return The Proporietary MarketDataReader 
	 */
	public Object getReader(JQuantDataProvider provider);
}
