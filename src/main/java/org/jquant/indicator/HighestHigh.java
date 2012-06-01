package org.jquant.indicator;

import org.jquant.serie.Candle;
import org.jquant.serie.CandleSerie;

public class HighestHigh extends CandleIndicator {

	private double highest;
	
	public HighestHigh(CandleSerie input) {
		super(input);
		highest = Double.MIN_VALUE;
	}

	@Override
	public void add(Candle candle) {
		if (candle.getHigh()>highest){
			highest = candle.getHigh();
		}
		output.add(candle.getDate(), highest);

	}

}
