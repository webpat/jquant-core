package org.jquant.serie;


import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.jquant.exception.NotEnoughDataException;



/**
 * <p>The Timeserie ease the creation,storage and transformation of time series.</p>
 * Agglomeration of one or more <b>variables</b> {@link Variable} that varies over time 
 * @author merhebp
 */
public abstract class TimeSerie<T> implements Iterable<T>  {

	protected boolean isPercent;   
    
    protected TreeMap<DateTime,T> map;
    
    
    public TimeSerie(){
        
        map = new TreeMap<DateTime,T>();
    }
    
  
    public Iterator<T> iterator() {
        return map.values().iterator();
   
    }

    
    public T getValue(DateTime timestamp){
        return map.get(timestamp);
    }
    
    /**
     * Return the element @ index position
     * @param index
     * @return
     * @throws NotEnoughDataException 
     */
    public T get(int index) throws NotEnoughDataException{
    	
    	if (index >size())
    		throw new NotEnoughDataException();
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
    
    protected abstract Class<T> getChildClass();
    
    public abstract TimeSerie<T> clone();
    
    @SuppressWarnings("unchecked")
	public T[] toArray(){
        T[] array = 
        map.values().toArray((T[]) Array.newInstance(getChildClass(), 0));
     
        return array;
        
    }
    public boolean containsDate(DateTime date) {
		return map.containsKey(date);
	}

    public int size(){
    	return map.size();
    }
    
	public boolean isPercent() {
		return isPercent;
	}

	public void setPercent(boolean isYield) {
		this.isPercent = isYield;
	}

    
    public enum Variable{OPEN,HIGH,LOW,CLOSE,VOLUME,RETURN,NAV,QUOTE};
    
    /**
     * Résolution de la série temporelle 
     * <ul>
     * 	<li>DAILY : Quotidienne</li>
     *  <li>QUOTES : Intraday quotes (bid, ask)</li>
     * </ul>
     * 
     */
    public enum SerieFrequency {

    	DAILY,
    	QUOTES,
    	TRADE
    	
    }
}
