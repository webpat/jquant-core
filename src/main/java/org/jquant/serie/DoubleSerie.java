package org.jquant.serie;

import org.joda.time.DateTime;


/**
 * 
 * @author patrick.merheb
 *
 */
public class DoubleSerie extends TimeSerie<TimeValue> {

	
	
	
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
			returns.addValue(new TimeValue(c1.getDate(), c0.getValue() - c1.getValue() / c1.getValue()));
		}
		return returns;
	}
	
	/**
	 *
	 * @param addend
	 * @return  S1(this) + S2 addend 
	 */
	public DoubleSerie add(DoubleSerie addend){
		DoubleSerie sum = new DoubleSerie();
		for (TimeValue tv:this){
			TimeValue add = addend.getValue(tv.getDate());
			double toAdd = add != null ? add.getValue(): 0;
			TimeValue ntv = new TimeValue(tv.getDate(), tv.getValue()+toAdd);
			sum.addValue(ntv);
		}
		
		return sum;
	}

}
