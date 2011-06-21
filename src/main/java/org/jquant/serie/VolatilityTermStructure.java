package org.jquant.serie;

import java.util.List;

import org.joda.time.Period;
import org.jquant.core.Rate;
import org.jquant.model.OptionType;



/**
 * La term Structure pour un {@link OptionType} et un strike donné
 * @author patrick.merheb
 *
 */
public class VolatilityTermStructure extends TermStructure {

	private final OptionType type;
	private final Double strike;
	
	
	public VolatilityTermStructure(List<Rate> list,Period term,OptionType type,Double strike){
		super(list,term);
		this.type = type;
		this.strike = strike;
	}
	
	
	/**
	 * 
	 * @return le {@link OptionType}
	 */
	public OptionType getType() {
		return type;
	}

	/**
	 * Le strike en % de la valeur du sous-jacent 
	 * 1.0 = ATM  
	 * @return Strike en % 
	 */
	public double getStrike() {
		return strike;
	}

	
	
	
}
