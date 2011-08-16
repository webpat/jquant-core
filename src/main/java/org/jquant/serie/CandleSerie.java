package org.jquant.serie;

import java.util.List;

import org.joda.time.DateTime;
import org.jquant.model.Candle;

/**
 * 
 * 
 * @author JQUANT TEAM 
 *
 */
public class CandleSerie extends TimeSerie<Candle> {

	public CandleSerie(){
		super();
	}
	/**
	 * Constrructor from a Vector
	 * INDEX THE ARRAY LIST IN A TIME INDEXED HASH MAP 
	 * @param vector
	 */
	public CandleSerie(List<Candle> vector){
		//indexes the candleVector and make it a TimeSerie
		for(Candle c:vector){
			this.addValue(c.getDate(), c);
		}
	}
	
	@Override
	public CandleSerie clone() {
		CandleSerie clonedCandleSerie = new CandleSerie();
		for(Candle candle: this){
			clonedCandleSerie.addValue(candle.getDate(), candle.clone());
		}
		
		return clonedCandleSerie;
	}

	@Override
	protected Class<Candle> getChildClass() {
		return Candle.class;
	}
	public double[] getCloses() {
		double[] array = new double[size()];
		int i =0;
		for(Candle c:this){
			array[i++] = c.getClose();
		}
		return null;
	}
	
	public CandleSerie getClosesFromDateToDate(DateTime firstDate, DateTime lastDate) {
		// count the closes
		CandleSerie candleSerie = new CandleSerie();
		//int nbCandles = 0;
		for(Candle c:this){
			if (c.getDate().compareTo(firstDate)<0) continue;
			if (c.getDate().compareTo(lastDate)>0) break;
			candleSerie.addValue(c.getDate(), c);
			//nbCandles++;
		}
		/*double[] array = new double[nbCandles];
		int i =0;
		for(Candle c:this){
			if (c.getDate().compareTo(firstDate)<0) continue;
			if (c.getDate().compareTo(lastDate)>0) break;
			array[i++] = c.getClose();
		}*/
		return candleSerie;
	}
	
	@Override
	public String toString() {
		return "CandleSerie [size()=" + size() + "]";
	}
	

}
