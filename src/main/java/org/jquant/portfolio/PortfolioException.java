package org.jquant.portfolio;

public class PortfolioException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5033041747946852569L;

	public PortfolioException() {
	}

	public PortfolioException(String message) {
		super(message);
	}

	public PortfolioException(Throwable cause) {
		super(cause);
	}

	public PortfolioException(String message, Throwable cause) {
		super(message, cause);
	}

}
