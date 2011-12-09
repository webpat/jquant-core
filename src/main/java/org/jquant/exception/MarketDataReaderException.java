package org.jquant.exception;


/**
 * Exception class for Data Abstraction Layer 
 *
 * @author Patrick Ducharme-Boutin
 * @author patrick.merheb
 */
public class MarketDataReaderException extends Exception {

    /** serialVersionUID for class interoperability */
    private static final long serialVersionUID = -6849794470754667710L;
    
    public MarketDataReaderException(String message) {
        super(message);
    }        
    
    public MarketDataReaderException(String message, Throwable ex) {
        super(message, ex);
    }        
}