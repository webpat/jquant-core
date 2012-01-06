package org.jquant.indicator;

import java.util.Observable;

import org.jquant.serie.Candle;
import org.jquant.serie.CandleSerie;

/**
 * Indicator build on a serie of candles 
 * @author patrick.merheb
 *
 */
public abstract class CandleIndicator extends AbstractIndicator {

	/**
	 * Compute the Inicator serie based on the CandleSerie
	 * @param serie
	 */
	public abstract void compute(CandleSerie serie);
	
	/**
	 * Update the indicator serie based on the last candle received
	 * @param candle
	 */
	public abstract void compute(Candle candle);
	
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof CandleSerie){
			compute(((CandleSerie)o).getLast());
		}

	}
	

}
