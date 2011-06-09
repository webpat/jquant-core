package org.jquant.core;

import java.util.List;

import org.jquant.model.OptionType;
import org.jquant.time.TimeFrame;



/**
 * La term Structure pour un {@link OptionType} et un strike donné
 * @author patrick.merheb
 *
 */
public class VolatilityTermStructure extends TermStructure {

	private final OptionType type;
	private final Double strike;
	
	
	public VolatilityTermStructure(List<Rate> list,TimeFrame pillar,OptionType type,Double strike){
		super(list,pillar);
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
