package org.jquant.core;

import java.util.List;

import org.jquant.time.TimeFrame;


/**
 * The Rate Serie structure, holds rates for a given TIME-FRAME(pillar)
 * @see TimeFrame 
 * @see Rate
 * @author JQUANT TEAM 
 *
 */
public class TermStructure extends TimeSerie<Rate>{

	protected TimeFrame pillar;
	
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
			this.addValue(r.getDate(), r);
		}
	}
	
	public TermStructure(List<Rate> list,TimeFrame pillar){
		super();
		this.setPillar(pillar);
		//indexes the QuotesVector and make it a TimeSerie
		for(Rate r:list){
//			r.setPillar(pillar);
			this.addValue(r.getDate(), r);
		}
	}
	
	
	@Override
	public TimeSerie<Rate> clone() {
		TermStructure clone = new TermStructure();
		return clone;
	}

	@Override
	protected Class<Rate> getChildClass() {
		
		return Rate.class;
	}

	public TimeFrame getPillar() {
		return pillar;
	}

	public void setPillar(TimeFrame pillar) {
		this.pillar = pillar;
	}

	
	
}
