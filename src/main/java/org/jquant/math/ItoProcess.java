package org.jquant.math;
import static java.lang.Math.sqrt;

/**
 * Computes the generalised Wiener process where the parameters are
 * functions of the underlying variable
 * @author patrick.merheb
 *
 */
public class ItoProcess {

	private double sd;
	private double mean;
	private double deltaPrice;
	
	/**
	 * 
	 * @param mu mean value (Expected Return)  
	 * @param sigma The variance (Variance of the asset price) 
	 * @param timedelta time periods for each step (annually based, 1day = 1/365, 1w = 1/52)
	 * @param basevalue the starting value (S0, initial stock price) 
	 * @return The deltaPrice value
	 */
	public double getValue(double mu, double sigma, double timedelta,double basevalue){
		setSd(basevalue*(sigma*sqrt(timedelta)));
		WienerGenerator g = new WienerGenerator();
		mu = mu * basevalue;
		sigma = sigma * basevalue;
		double change=( g.generate(mu, timedelta, sigma));
		setDeltaPrice(change);
		setMean(g.getDrift());
		return change;
		
	}

	/**
	 * 
	 * @return the Standard Deviation used in the Wiener Generator
	 */
	public double getSd() {
		return sd;
	}

	public void setSd(double sd) {
		this.sd = sd;
	}

	/**
	 * 
	 * @return The drift (average expected return) used as the mean in the Wiener Generator
	 */
	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getDeltaPrice() {
		return deltaPrice;
	}

	public void setDeltaPrice(double base) {
		this.deltaPrice = base;
	}
	
	
	
}
