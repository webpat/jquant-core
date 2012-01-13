package org.jquant.serie;


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
	 * 
	 * @return an <code>array</code> containing the values of the output
	 */
	public double[] getData() {
		double[] array = new double[size()];
		int i =0;
		for(TimeValue tv:this){
			 array[i++] = tv.getValue();break;
				
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

}
