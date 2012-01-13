package org.jquant.indicator;

import org.apache.commons.lang3.ArrayUtils;
import org.jquant.serie.Candle;
import org.jquant.serie.Candle.CandleData;
import org.jquant.serie.CandleSerie;
import org.jquant.serie.TimeValue;
import org.jquant.util.FinancialLibrary;

/**
 * Simple Moving Average 
 * <p>
 * An indicator frequently used in technical analysis showing the average value of a security's price over a set period. 
 * Moving averages are generally used to measure momentum and define areas of possible support and resistance.
 * @author patrick.merheb
 *
 */
public final class SMA extends CandleIndicator {

	/**
	 * Length of the indicator in candles 
	 */
	private final int length;
	
	private final CandleData data;
	
	
	public SMA(CandleSerie serie, int length,CandleData data) {
		super(serie);
		this.length = length;
		this.data = data;
		
	}

//	@Override
//	public void compute(CandleSerie output) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public void add(Candle candle) {
	 if (input.size()>=length){
		 double[] array = input.getData(data);
		 output.addValue(new TimeValue(candle.getDate(), 
				 					 FinancialLibrary.SMA(length, ArrayUtils.subarray(array,array.length-length ,array.length),0)));
	 }
		
	}

}
