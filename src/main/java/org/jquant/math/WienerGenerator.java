package org.jquant.math;

import static java.lang.Math.sqrt;

import java.util.Random;

/**
 * Wiener process generator
 * @author patrick.merheb
 *
 */
public class WienerGenerator {

	private double drift;
	private double wienerValue;
	
	
	

	/**
	 * 
	 * @param drift
	 * @param t The time 
	 * @param sd The Standard Deviation  
	 * @return W(t)
	 */
	public double generate(double drift,double t, double sd){
		
		double deltaz;
		double driftvalue;
		double deltax;
		
		deltaz = wienerProcess(t);
		setWienerValue(deltaz);
		driftvalue = drift*t;
		setDrift(driftvalue);
		deltax = driftvalue + sd*deltaz;
		
		return deltax;
		
	}
	
	/**
	 * Random source for Wiener Process 
	 * @param t time 
	 * @return W(t)
	 */
	private double wienerProcess(double t){
		Random r = new Random();
		double epsilon = r.nextGaussian();
		return sqrt(t)*epsilon;
	}
	
	
	public double getDrift() {
		return drift;
	}


	public void setDrift(double drift) {
		this.drift = drift;
	}


	public double getWienerValue() {
		return wienerValue;
	}


	public void setWienerValue(double wienerValue) {
		this.wienerValue = wienerValue;
	}

	
	
}
