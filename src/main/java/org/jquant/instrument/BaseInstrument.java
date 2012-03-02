package org.jquant.instrument;

import org.jquant.model.Currency;
import org.jquant.model.IInstrument;
import org.jquant.model.MICMarketPlace;
import org.jquant.model.Symbol;



/**
 * <b>Description :</b> This abstract class represents contains the basics of an instrument specification 
 * (type, market, expiry date, currency ...)
 * <p>For most specific instruments attributes  see specific child classes
 * <p>For a given Data Provider a symbol/market tuple should be unique  
 * <b>History:</b><br>
 * @author patrick.merheb
 * @see Symbol
 * @see MICMarketPlace
 * @see Currency
 */
public abstract class BaseInstrument implements IInstrument {

	protected Symbol symbol = null;	
	protected Currency currency;

	/**
	 * Constructor
	 * @param symbol a {@link Symbol} 
	 * @param currency a {@link Currency}
	 */	
	public BaseInstrument(Symbol symbol,Currency currency){
		this.symbol = symbol;
	}

	
	
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	
	public MICMarketPlace getMarket() {
		return symbol.getExchange();
	}		
	
	public Symbol getSymbol() {
		return symbol;
	}
	
	
	
	
	
	@Override
	public int hashCode() {
		int hashcode = 0;
		hashcode ^= symbol.hashCode();
		hashcode ^= currency.hashCode();
		
		return hashcode;
	}
	
	/**
	 * 
	 * @return Daily prices
	 */
//	public CandleSerie getCandles(){	
//		return candles;
//		
//	}
//	
//	public void setCandles(CandleSerie prices) {
//		this.candles = prices;
//	}
	

//	protected VolatilityTermStructure getImplicitVolatility(Period term){
//		
//		if (volatilityMap != null){
//			VolatilityTermStructure volCurve = volatilityMap.get(term);
//			return volCurve;
//		}
//		
//		return null;
//	}
	
//	protected Candle getCandle(DateTime date){
//		
//		if (candles != null){
//			Candle result = candles.getValue(date);
//			return result;
//		}
//		return null;
//	}
	/**
	 * get Implicit volatility OnDate 
	 * @param pillar
	 * @param une {@link DateTime} 
	 * @return {@link Rate}
	 * @throws NotEnoughDataException 
	 */
//	protected Rate getImplicitVolatility(Period pillar,DateTime date){
//		
//		TermStructure volCurve = volatilityMap.get(pillar.toString());
//		if (volCurve == null){
//			throw new NotEnoughDataException("No volatilities for pillar:"+pillar.toString()+" at date"+ date);
//		}
//			
//		return volCurve.getValue(date);
//		
//	}

	

//	protected void setVolatility(Period pillar,VolatilityTermStructure volCurve) {
//		
//		// Create the Map if it does not exist 
//		if (volatilityMap == null)
//			volatilityMap = new IdentityHashMap<Integer, VolatilityTermStructure>();
//		
//		this.volatilityMap.put(pillar.hashCode(), volCurve);
//	}



//	public void setQuotes(QuoteSerie quotes) {
//		this.quotes = quotes;
//	}
//
//
//
//	public QuoteSerie getQuotes() {
//		return quotes;
//	}
	
	
	
}
