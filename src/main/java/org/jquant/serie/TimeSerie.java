package org.jquant.serie;


import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.jquant.exception.TimeSerieException;
import org.jquant.model.IInstrument;
import org.jquant.model.Symbol;
import org.jquant.model.TimeValue;



/**
 * Accumulation of time values that varies over time 
 * <p>The Timeserie ease the creation,storage, transformation and manipulation of time series.</p>
 * 
 * @author merhebp
 * @param <T> The content type of the TimeSerie
 * @see TimeValue
 */
public abstract class TimeSerie<T extends TimeValue> implements Iterable<T>  {

	
	/**
	 * {@link #isPercent()}
	 */
	protected boolean isPercent;   
    
	
	/**
	 * Le contenu de la time-serie
	 */
    private final TreeMap<DateTime,T> map;
    
    
    /**
     * {@link #getInstrument()}
     */
    protected IInstrument instrument;
    
    
    protected Symbol symbol;
    
    
    public TimeSerie(){
        
        map = new TreeMap<DateTime,T>();
    }
    
  
    public Iterator<T> iterator() {
        return map.values().iterator();
   
    }

    
    /**
     * @param timestamp un {@link DateTime}
     * @return La valeur contenue à l'instant précisé 
     */
    public T getValue(DateTime timestamp){
        return map.get(timestamp);
    }
    
    /**
     * Return the element @ index position
     * @param index
     * @return the Time serie content at index 
     * @throws TimeSerieException 
     */
    public T get(int index) throws TimeSerieException{
    	
    	if (index >size())
    		throw new TimeSerieException("No Data",null);
    	Iterator<T> iter = map.values().iterator();
    	int i = index;
    	while(i>0 && iter.hasNext()){
    		i--;
    		iter.next();
    	}
    	return iter.next();
    	
    }
    
    public void addValue(DateTime timestamp, T value){
        map.put(timestamp,value);
    }
    
    /**
     * @return Renvoie la classe contenue (Candle, Quote, Trade ...)
     */
    protected abstract Class<T> getChildClass();
    
    public abstract TimeSerie<T> clone();
    
    /**
     * @return La {@link TimeSerie} convertie en Array
     */
    @SuppressWarnings("unchecked")
	public T[] toArray(){
        T[] array = 
        map.values().toArray((T[]) Array.newInstance(getChildClass(), 0));
     
        return array;
        
    }
    
    /**
     * 
     * @param date
     * @return <code>true</code> si la {@link TimeSerie} contient cette date
     */
    public boolean containsDate(DateTime date) {
		return map.containsKey(date);
	}

    /**
     * 
     * @return La taille de la {@link TimeSerie}
     */
    public int size(){
    	return map.size();
    }
    
    /**
     * 
     * @return <code>true</code> Les valeurs sont en rendements 
     */
	public boolean isPercent() {
		return isPercent;
	}

	public void setPercent(boolean isYield) {
		this.isPercent = isYield;
	}

	/**
	 * 
	 * @return  L'instrument associé à la time serie 
	 */
	public IInstrument getInstrument() {
		return instrument;
	}


	public void setInstrument(IInstrument instrument) {
		this.instrument = instrument;
	}

	/**
	 * 
	 * @return Le {@link Symbol} de l'instrument associé à la timeSerie
	 */
	public Symbol getSymbol() {
		return symbol;
	}


	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}

	
	
	
    
//    public enum Variable{OPEN,HIGH,LOW,CLOSE,VOLUME,RETURN,NAV,QUOTE};
    
    /**
     * Résolution de la série temporelle 
     * <ul>
     * 	<li>DAILY : Quotidienne</li>
     *  <li>QUOTES : Intraday quotes (bid, ask)</li>
     * </ul>
     * 
     */
//    public enum SerieFrequency {
//
//    	DAILY,
//    	QUOTES,
//    	TRADE
//    	
//    }
}
