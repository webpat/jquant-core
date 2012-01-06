package org.jquant.serie;

import java.util.List;


/**
 * Time indexed Best Bid Best Ask series {@link BBBA}
 *  
 * @author merhebp
 *
 */
public class QuoteSerie extends TimeSerie<BBBA>{

	
	public QuoteSerie(){
		super();
	}
	
	/**
	 * HASH TIME INDEX THE QUOTES ARRAY LIST
	 * constuct a Time Indexed Quotes Serie  with fancy methods to parse and manipulate
	 * @param vector
	 */
	public QuoteSerie(List<BBBA> vector){
		
		super();
		//indexes the QuotesVector and make it a TimeSerie
		for(BBBA bbba:vector){
			this.addValue(bbba);
		}
	}
	
	
	@Override
	public QuoteSerie clone() {
		QuoteSerie clonedTimeSerie = new QuoteSerie();
		
		for(BBBA quote: this){
			clonedTimeSerie.addValue(quote.clone());
		}
		
		return clonedTimeSerie;
	}

	@Override
	protected Class<BBBA> getChildClass() {
		return BBBA.class;
	}
	

	
}
