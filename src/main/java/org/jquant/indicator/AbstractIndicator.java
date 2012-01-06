package org.jquant.indicator;

import java.util.Iterator;
import java.util.Observer;

import org.jquant.serie.DoubleSerie;
import org.jquant.serie.TimeValue;


/**
 * Technical Analysis AbstractIndicator
 * <p>
 * In the context of technical analysis, an indicator is a mathematical calculation based on a securities price and/or volume. 
 * The result is used to predict future prices. Common technical analysis indicators are the moving average convergence-divergence (MACD) indicator and the relative strength index (RSI).
 * 
 * @author patrick.merheb
 *
 */
public abstract class AbstractIndicator implements Observer,Iterable<TimeValue> {

	
	/**
	 * Le contenu de l'indicateur
	 */
    protected final DoubleSerie serie;
	
  
    
    
	public AbstractIndicator() {
		super();
		 serie = new DoubleSerie();
	}


	@Override
	public Iterator<TimeValue> iterator() {
		return serie.iterator();
	}

	 
	
}
