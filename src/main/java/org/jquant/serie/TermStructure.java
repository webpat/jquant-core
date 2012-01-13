package org.jquant.serie;

import java.util.List;

import org.joda.time.Period;


/**
 * The Rate Serie structure, holds rates for a given TIME-FRAME(pillar)
 * @see Term 
 * @see Rate
 * @author JQUANT TEAM 
 *
 */
public class TermStructure extends TimeSerie<Rate>{

	protected Period term;
	
	/**
	 * Blank Constructor
	 */
	public TermStructure(){
		super();
	}
	
	/**
	 *  TIME Indexing constructor from a straight list 
	 * @param list
	 */
	public TermStructure(List<Rate> list){
		super();
		//indexes the QuotesVector and make it a TimeSerie
		for(Rate r:list){
			this.addValue(r);
		}
	}
	
	public TermStructure(List<Rate> list,Period term){
		super();
		this.setTerm(term);
		//indexes the QuotesVector and make it a TimeSerie
		for(Rate r:list){
			this.addValue(r);
		}
	}
	
	
	@Override
	public ITimeSerie<Rate> clone() {
		TermStructure clone = new TermStructure();
		return clone;
	}

	@Override
	protected Class<Rate> getChildClass() {
		
		return Rate.class;
	}

	/**
	 * TERM, Period, length 
	 * @return La durée (term) sur laquelle s'exprime le taux
	 */
	public Period getTerm() {
		return term;
	}

	public void setTerm(Period term) {
		this.term = term;
	}



	
	
}
