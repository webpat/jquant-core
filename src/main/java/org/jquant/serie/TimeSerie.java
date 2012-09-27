package org.jquant.serie;


import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Iterator;
import java.util.Observable;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.jquant.model.InstrumentId;



/**
 * Accumulation of time values that varies over time 
 * <p>The Timeserie ease the creation,storage, transformation and manipulation of time series.</p>
 * 
 * @author Patrick Merheb
 * @param <T> The content type of the TimeSerie
 * @see TimeValue
 */
public abstract class TimeSerie<T extends AbstractTimeValue> extends Observable implements Iterable<T>, ITimeSerie<T>, Serializable  {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 751389787380655669L;


	/**
	 * {@link #isPercent()}
	 */
	protected boolean isPercent;   
    

    protected final SortedMap<DateTime,T> map;
    
     
    /**
     * {@link InstrumentId} linked to the Time Serie 
     * {@link #getSymbol()}
     */
    protected InstrumentId symbol;
    
    
    public TimeSerie(){
        
        map = new TreeMap<DateTime,T>();
    }
    
  
    /**
     * @return Chronological Iterator on Values 
     */
    public Iterator<T> iterator() {
        return map.values().iterator();
    
    }
    
    /**
     *  
     * @return Anti - Chronological iterator on values
     */
    public Iterator<T> reverseIterator(){
		
    	final SortedMap<DateTime, T> reverseMap= new TreeMap<DateTime,T>(Collections.reverseOrder());
    	reverseMap.putAll(map);
    	    	
    	return reverseMap.values().iterator();
    	
    }
    
       
    
    /* (non-Javadoc)
	 * @see org.jquant.serie.ITimeSerie#getValue(org.joda.time.DateTime)
	 */
    @Override
	public T getValue(DateTime timestamp){
        return map.get(timestamp);
    }
    
    /* (non-Javadoc)
	 * @see org.jquant.serie.ITimeSerie#get(int)
	 */
    @Override
	public T get(int index){
    	
    	if (index >size())
    		throw new IllegalArgumentException("Index is out of bounds",null);
    	Iterator<T> iter = map.values().iterator();
    	int i = index;
    	while(i>0 && iter.hasNext()){
    		i--;
    		iter.next();
    	}
    	return iter.next();
    	
    }
    
    /**
     * Add a TimeValue to this time serie
     * @param value  a TimeValue 
     */
    public void addValue(T value){
        map.put(value.getDate(),value);
        setChanged();
        notifyObservers(this);
        clearChanged();
    }
    
    
    /**
     * Add all time values contained in ts in this TimeSerie 
     * @param ts the other TimeSerie 
     */
    public void addAll(TimeSerie<T> ts){
    	map.putAll(ts.map);
    }
    
    
    /**
     * 
     * @return an ordered List of all the {@link DateTime} in this TimeSerie
     */
    public Set<DateTime> getDateTimes(){
    	return  map.keySet();
    }
    
    /**
     * @return Renvoie la classe contenue (Candle, BBBA, Trade ...)
     */
    protected abstract Class<T> getChildClass();
    
    public abstract ITimeSerie<T> clone();
    
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
     * is this a yield output 
     * @return <code>true</code> if values are yield  
     */
	public boolean isPercent() {
		return isPercent;
	}

	public void setPercent(boolean isYield) {
		this.isPercent = isYield;
	}

	

	/* (non-Javadoc)
	 * @see org.jquant.serie.ITimeSerie#getSymbol()
	 */
	@Override
	public InstrumentId getSymbol() {
		return symbol;
	}


	public void setSymbol(InstrumentId symbol) {
		this.symbol = symbol;
	}

	
	/**
	 * 
	 * @return <b>first</b> {@link DateTime} of the Serie
	 */
	public DateTime getFirstDate(){
		return map.firstKey();
		
	}
	
	/**
	 * 
	 * @return <b>last</b> DateTime of the Serie
	 */
	public DateTime getLastDate(){
		return map.lastKey();
		
	}
	/**
	 * 
	 * @return the Last {@link AbstractTimeValue} of the Serie 
	 */
	public T getLast(){
		return map.get(map.lastKey());
	}
	
	/**
	 * 
	 * @return the First {@link AbstractTimeValue} of the Serie 
	 */
	public T getFirst(){
		return map.get(map.firstKey());
	}
	

}
