package org.jquant.exception;

public class NotEnoughDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7848873145971705758L;

	public NotEnoughDataException() {
		super("Not enough data to compute indicator.");
	}

	public NotEnoughDataException(String msg) {
		super(msg);
	}
	
	
}
