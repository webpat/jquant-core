/**
 * 
 */
package org.jquant.strategy;

import java.util.Map;

import org.jquant.model.InstrumentId;
import org.jquant.serie.CandleSerie;

/**
 * @author patrick.merheb
 *
 */
public abstract class MultiStrategy extends AbstractStrategy {

	/**
	 * Reference to a Map of Growing CandleSeries 
	 */
	private Map<InstrumentId, CandleSerie> candleSeries;
	
	
	
	/**
	 * Return the candleSerie for the given InstrumentId 
	 * @param instrument a InstrumentId
	 * @return a {@link CandleSerie}
	 */
	protected CandleSerie getCandleSerie(InstrumentId instrument){
		return candleSeries.get(instrument);
	}
	
//	protected void setCandleSerieMap(Map<InstrumentId,CandleSerie> map){
//		this.candleSeries = map;
//	}
	
	

	
	

}
