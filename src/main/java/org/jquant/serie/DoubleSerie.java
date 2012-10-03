package org.jquant.serie;

import org.apache.commons.math3.stat.StatUtils;
import org.joda.time.DateTime;


/**
 * 
 * @author patrick.merheb
 *
 */
public class DoubleSerie extends TimeSerie<TimeValue> {

	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4606211240416864648L;

	@Override
	protected Class<TimeValue> getChildClass() {
		return TimeValue.class;
	}
	
	/**
	 * Convenience method to add a TimeValue pair 
	 * @param date a {@link DateTime}
	 * @param value a {@link Double}
	 */
	public void add(DateTime date, double value){
		addValue(new TimeValue(date, value));
	}
	

	/**
	 * 
	 * @param dt {@link DateTime}
	 * @return a {@link Double}
	 */
	public double getDouble(DateTime dt){
		return getValue(dt).getValue();
		
	}
	
	/**
	 * 
	 * @return an <code>array</code> containing the values of the output
	 */
	public double[] getData() {
		double[] array = new double[size()];
		int i =0;
		for(TimeValue tv:this){
			 array[i++] = tv.getValue();
				
		}
		return array;
	}
	
	@Override
	public ITimeSerie<TimeValue> clone() {
		
		DoubleSerie dolly = new DoubleSerie();
		for (TimeValue tv : this){
			dolly.addValue(new TimeValue(tv.getDate(), tv.getValue()));
		}
		return dolly;
	}
	
	/**
	 * Get the returns 
	 * @return a {@link TimeSerie#size()}-1 {@link DoubleSerie} with the <b>returns</b> relative to the values of this serie
	 */
	public DoubleSerie getReturns() {
		DoubleSerie returns = new DoubleSerie();
		for(int i=1;i<size();i++){
			TimeValue c0 = this.get(i-1);
			TimeValue c1 = this.get(i);
			returns.addValue(new TimeValue(c0.getDate(), (c1.getValue()- c0.getValue()) / c0.getValue()));
		}
		return returns;
	}
	
	/**
	 * Add two series (just the common time values)
	 * @param addend
	 * @return  S1(this) + S2 addend 
	 */
	public DoubleSerie sum(DoubleSerie addend){
		DoubleSerie sum = new DoubleSerie();
		for (TimeValue tv:this){
			TimeValue add = addend.getValue(tv.getDate());
			double toAdd = add != null ? add.getValue(): 0;
			TimeValue ntv = new TimeValue(tv.getDate(), tv.getValue()+toAdd);
			sum.addValue(ntv);
		}
		
		return sum;
	}
	
	/**
	 * Multiply Serie by a scalar 
	 * @param scalar
	 * @return S(this) x scalar
	 */
	public DoubleSerie product(double scalar){
		DoubleSerie result = new DoubleSerie();
		for (TimeValue tv:this){
			TimeValue ntv = new TimeValue(tv.getDate(), tv.getValue()*scalar);
			result.addValue(ntv);
		}
		
		return result;
	}
	
	/**
	 * Returns the arithmetic mean of the entries in the input array, or Double.NaN if the array is empty. 
	 * <p>
	 * Throws IllegalArgumentException if the array is null.
	 * @return arithmetic mean 
	 */
	public double mean(){
		return StatUtils.mean(getData());
	}
	
	/**
	 * Returns the variance of the entries in the input array, or Double.NaN if the array is empty. 
	 * <p>This method returns the bias-corrected sample variance (using n - 1 in the denominator).
	 * @return the variance
	 */
	public double variance(){
		return StatUtils.variance(getData());
	}
	
	 

}
