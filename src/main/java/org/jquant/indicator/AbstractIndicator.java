package org.jquant.indicator;

import java.util.Iterator;
import java.util.Observer;

import org.joda.time.DateTime;
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
    protected final DoubleSerie output;
	
   
    
    
	public AbstractIndicator() {
		super();
		 output = new DoubleSerie();
	}


	public double getValue(DateTime timestamp){
		if (output.getValue(timestamp)!=null){
			return output.getValue(timestamp).getValue();
		}else {
			return Double.NaN;
		}
		
		
	}
	
	@Override
	public Iterator<TimeValue> iterator() {
		return output.iterator();
	}

	 
	
}
