package org.jquant.exception;


/**
 * Exception class for the DAOs.
 *
 * @author  Patrick Ducharme-Boutin
 */
public class MarketDataReaderException extends Exception {

    /** serialVersionUID for class interoperability */
    private static final long serialVersionUID = -6849794470754667710L;
    
    public MarketDataReaderException(String message, Throwable ex) {
        super(message, ex);
    }        
}