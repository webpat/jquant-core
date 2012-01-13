package org.jquant.serie;

import java.util.List;

import org.joda.time.DateTime;
import org.jquant.model.Symbol;
import org.jquant.serie.Candle.CandleData;



/**
 * The CandleSerie is a time-output of Candles 
 *<p> This time-output can be associated with an instrument  
 *@author JQUANT TEAM 
 *@see TimeSerie
 *@see Candle
 */
public class CandleSerie extends TimeSerie<Candle> {

	public CandleSerie(){
		super();
	}
	
	/**
	 * Construct and mark the output with a Symbol
	 * @param symbol {@link Symbol}
	 */
	public CandleSerie(Symbol symbol){
		super();
		setSymbol(symbol);
	}
	
	/**
	 * Constrructor from a {@link List}
	 * <p>
	 * INDEX THE ARRAY LIST IN A TIME INDEXED HASH MAP 
	 * @param candles
	 */
	public CandleSerie(List<Candle> candles){
		for(Candle c:candles){
			this.addValue(c);
		}
	}
	
	@Override
	public CandleSerie clone() {
		CandleSerie clonedCandleSerie = new CandleSerie();
		for(Candle candle: this){
			clonedCandleSerie.addValue(candle.clone());
		}
		
		return clonedCandleSerie;
	}
	
	@Override
	public void addValue(Candle candle) {
		super.addValue(candle);
//		candle.setSerie(this);//Symetric binding
	}
	

	@Override
	protected Class<Candle> getChildClass() {
		return Candle.class;
	}
	public double[] getData(CandleData data) {
		double[] array = new double[size()];
		int i =0;
		for(Candle c:this){
			switch (data){
				case CLOSE : array[i++] = c.getClose();break;
				case OPEN : array[i++] = c.getOpen();break;
				case HIGH : array[i++] = c.getHigh();break;
				case LOW : array[i++] = c.getLow();break;
				
			}
			
		}
		return array;
	}
	
	public double[] getClosesFromDateToDate(CandleData data, DateTime firstDate, DateTime lastDate) {
	
		double[] array = new double[this.size()-1];
		int i =0;
		for(Candle c:this){
			if (c.getDate().compareTo(firstDate)<0) continue;
			if (c.getDate().compareTo(lastDate)>0) break;
			switch (data){
				case CLOSE : array[i++] = c.getClose();break;
				case OPEN : array[i++] = c.getOpen();break;
				case HIGH : array[i++] = c.getHigh();break;
				case LOW : array[i++] = c.getLow();break;
			
			}
		}
		return array;
	}
	
	@Override
	public String toString() {
		return "CandleSerie [size=" + size() + ", symbol=" + getSymbol() + "]";
	}
	
	

}
