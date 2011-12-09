/*
 * Created on 12 juin 07
 */
package org.jquant.model;

import org.joda.time.DateTime;


public interface ITimeValue {

//    public double getValue(Variable var);
    
    public double getValue();
    
    public DateTime getDate();
    
//    public Variable getDefaultVariable();
    
    public boolean isPadValue();
    
}
