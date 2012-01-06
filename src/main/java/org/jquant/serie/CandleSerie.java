package org.jquant.serie;

import java.util.List;

import org.joda.time.DateTime;

/**
 * The CandleSerie is a time-serie of Candles 
 *<p> This time-serie can be associated with an instrument  
 *@author JQUANT TEAM 
 *@see TimeSerie
 *@see Candle
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
		candle.setSerie(this);//Symetric binding
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
	
	public double[] getClosesFromDateToDate(DateTime firstDate, DateTime lastDate) {
	
		double[] array = new double[this.size()-1];
		int i =0;
		for(Candle c:this){
			if (c.getDate().compareTo(firstDate)<0) continue;
			if (c.getDate().compareTo(lastDate)>0) break;
			array[i++] = c.getClose();
		}
		return array;
	}
	
	@Override
	public String toString() {
		return "CandleSerie [size=" + size() + ", symbol=" + getSymbol() + "]";
	}
	

}
