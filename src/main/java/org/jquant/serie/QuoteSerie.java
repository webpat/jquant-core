package org.jquant.serie;

import java.util.List;

import org.jquant.core.Quote;

/**
 * Time indexed Quote Structure
 * isPercent (is it percentage yield) 
 *  
 * @author merhebp
 *
 */
public class QuoteSerie extends TimeSerie<Quote>{

	
	public QuoteSerie(){
		super();
	}
	
	/**
	 * HASH TIME INDEX THE QUOTES ARRAY LIST
	 * @return a Time Indexed Quotes Serie  with fancy methods to parse and manipulate
	 * @param vector
	 */
	public QuoteSerie(List<Quote> vector){
		
		super();
		//indexes the QuotesVector and make it a TimeSerie
		for(Quote c:vector){
			this.addValue(c.getDate(), c);
		}
	}
	
	
	@Override
	public QuoteSerie clone() {
		QuoteSerie clonedTimeSerie = new QuoteSerie();
		
		for(Quote quote: this){
			clonedTimeSerie.addValue(quote.getDate(), quote.clone());
		}
		
		return clonedTimeSerie;
	}

	@Override
	protected Class<Quote> getChildClass() {
		return Quote.class;
	}
	
//	public QuoteVector toQuoteVector() {
//		QuoteVector quoteVector = new QuoteVector();
//		for(Quote quote: this){
//			quoteVector.add(quote.toQuoteTO());
//		}
//		return quoteVector;
//	}

	
}
