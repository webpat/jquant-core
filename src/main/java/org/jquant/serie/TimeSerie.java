package org.jquant.serie;


import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Observable;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.jquant.exception.TimeSerieException;
import org.jquant.model.InstrumentId;



/**
 * Accumulation of time values that varies over time 
 * <p>The Timeserie ease the creation,storage, transformation and manipulation of time series.</p>
 * 
 * @author Patrick Merheb
 * @param <T> The content type of the TimeSerie
 * @see TimeValue
 */
public abstract class TimeSerie<T extends AbstractTimeValue> extends Observable implements Iterable<T>, ITimeSerie<T>  {

	
	/**
	 * {@link #isPercent()}
	 */
	protected boolean isPercent;   
    
	
	/**
	 * Le contenu de la time-output
	 */
    protected final TreeMap<DateTime,T> map;
    
     
    /**
     * Le symbole de l'instrument associé à la Time Serie 
     * {@link #getSymbol()}
     */
    protected InstrumentId symbol;
    
    
    public TimeSerie(){
        
        map = new TreeMap<DateTime,T>();
    }
    
  
    public Iterator<T> iterator() {
        return map.values().iterator();
   
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
    
    public void addValue(T value){
        map.put(value.getDate(),value);
        setChanged();
        notifyObservers(this);
        clearChanged();
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
		return map.lastEntry().getValue();
	}
	

}
