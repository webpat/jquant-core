package org.jquant.indicator;

import java.util.Observable;

import org.jquant.serie.Candle;
import org.jquant.serie.CandleSerie;

/**
 * Indicator build on a output of candles 
 * @author patrick.merheb
 *
 */
public abstract class CandleIndicator extends AbstractIndicator {

	/**
	 * The Input of the CandleIndicator is a CandleSerie 
	 */
	protected CandleSerie input;
		
	
	public CandleIndicator(CandleSerie input) {
		super();
		this.input = input;
		this.input.addObserver(this); // This indicator becomes a CandleSerieObserver
	}
	
	/**
	 * Compute the Inicator output based on the CandleSerie
	 * @param output
	 */
//	public abstract void compute(CandleSerie output);
	
	/**
	 * Update the indicator output based on the last candle received
	 * @param candle
	 */
	public abstract void add(Candle candle);
	
	@Override
	public void update(Observable o, Object arg) {
		/**
		 * The indicator receives an update from an Obsrvable CandleSerie then update its own output via the add method
		 */
		if (o instanceof CandleSerie){
			add(((CandleSerie)o).getLast());
		}

	}
	

}
