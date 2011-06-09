package org.jquant.exception;


/**
 *
 *  @author JQUANT TEAM
 */
public class UnknownSymbolException extends Exception {

	private static final long serialVersionUID = -7886468298879310253L;

	public UnknownSymbolException() {
		super("Unknown Symbol");
	}
	
	public UnknownSymbolException(String message) {
		super(message);
	}
	
}
