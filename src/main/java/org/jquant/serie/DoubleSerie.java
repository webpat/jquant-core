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

	@Override
	public TimeSerie<TimeValue> clone() {
		
		DoubleSerie dolly = new DoubleSerie();
		for (TimeValue tv : this){
			dolly.addValue(new TimeValue(tv.getDate(), tv.getValue()));
		}
		return dolly;
	}

}
