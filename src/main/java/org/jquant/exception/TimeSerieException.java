package org.jquant.exception;

/**
 * Exception for time-series related operations
 * <p>Possible causes are: no data, wrong operation ...
 * @author patrick.merheb
 *
 */
public class TimeSerieException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 670442403022039326L;

	   
    public TimeSerieException(String message, Throwable ex) {
        super(message, ex);
    }        
	
}
