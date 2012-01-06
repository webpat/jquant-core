/*
 * Created on 12 juin 07
 */
package org.jquant.serie;

import org.joda.time.DateTime;


/**
 * Abstract Interface for TimeValues Objects 
 * 
 * @author patrick.merheb
 *@see BBBA 
 *@see Candle 
 */
public interface ITimeValue {

    
    /**
     * 
     * @return the value
     */
	public double getValue();
    
	/**
	 * 
	 * @return the place in time
	 */
    public DateTime getDate();
    
    
   
    
}
