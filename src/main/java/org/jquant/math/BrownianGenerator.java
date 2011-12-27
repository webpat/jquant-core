package org.jquant.math;
import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Brownian Motion generator expressed with a Wiener process 
 * Classe bizarre !!
 * @author patrick.merheb
 *
 */
public class BrownianGenerator {

	private double drift;
	private double sd;
	private final WienerGenerator g;
	
	
	
	/**
	 * Constructor for the Brownian generator 
	 * @param mu
	 * @param sigma
	 * @param time (???)
	 */
	public BrownianGenerator(double mu, double sigma, double time) {
		
		this.g = new WienerGenerator();
		double process = exp(g.generate(mu, time ,sigma));
		setDrift(g.getDrift());
		setSd(sqrt( (exp(2.0*mu*(time)
				+pow(sigma, 2.0)*(time))*
				(exp(pow (sigma, 2.0)*(time))-1))));
		
	}



	/**
	 * 
	 * @param mu Mean
	 * @param sigma Standard Deviation
	 * @param time 
	 * @param points number of trading days 
	 * @return [0..points][exp(W(t),e(drift),e(drift)+variance,e(drift)-variance]
	 */
	public double[][] generate(double mu, double sigma, double time, int points ){
		double[][] wval = new double [points+1][4]; //Candle
		// init first value
		wval[0][0] = 0.0;
		wval[0][1] = 0.0;
		wval[0][2] = sqrt((exp(0.0)-1)*exp(0.0)); //assumes sd == 1 
		
		int counter = 1;
		double varval;
		double interim = 0.0;
		double driftvalues = 0.0;
		
		while (counter < points){
			varval = (sqrt((exp(counter/points)-1)*exp(2*counter/points)));
			interim = g.generate(mu, time,sigma)+interim ;
			driftvalues=(driftvalues+g.getDrift());
			wval[counter][0]=exp(interim);
			wval[counter][1]=exp(driftvalues);
			wval[counter][2]=(wval[counter][1]+varval); //drift plus variance
			wval[counter][3]=(wval[counter][1]-varval); //drift minus variance
			counter++;
		}
		
		
		return wval;
		
	}



	public double getDrift() {
		return drift;
	}



	public void setDrift(double drift) {
		this.drift = drift;
	}



	public double getSd() {
		return sd;
	}



	public void setSd(double sd) {
		this.sd = sd;
	}
	
	
	
}
