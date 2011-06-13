package org.jquant.core;

import java.util.IdentityHashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.jquant.exception.NotEnoughDataException;
import org.jquant.model.Currency;
import org.jquant.serie.CandleSerie;
import org.jquant.serie.QuoteSerie;
import org.jquant.serie.TermStructure;
import org.jquant.serie.VolatilityTermStructure;
import org.jquant.time.TimeFrame;



/**
 * <b>Description :</b> This abstract class represents contains the basics of an instrument specification 
 * (type, market, expiry date, currency ...)
 * For most specific instruments attributes  see specific child classes
 * <p>For a given Data Provider a symbol/market tuple should be unique  
 * The volatility Map use an IdentityHashMap (use of the == operator instead of the equals Method)
 * </p>
 * <br/>
 * <b>History:</b><br>
 * @author patrick.merheb
 *
 */
public abstract class BaseInstrument implements IInstrument {

	protected MICMarketPlace market;
	protected Symbol symbol = null;	
	protected Currency currency;

	/**
	 * Daily prices {@link BaseInstrument#getCandles()}
	 */
	private CandleSerie candles;
	
	/**
	 * Intraday Quotes
	 */
	private QuoteSerie quotes;
	
	/**
	 * Volatility Surface
	 */
	private Map<Integer,VolatilityTermStructure> volatilityMap; 
	
	
	
	public BaseInstrument(Symbol symbol,MICMarketPlace market){
		this.symbol = symbol;
		this.market = market;
	}

	
	
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	
	public MICMarketPlace getMarket() {
		return market;
	}		
	
	public Symbol getSymbol() {
		return symbol;
	}
	
	
	
	
	
	@Override
	public int hashCode() {
		int hashcode = 0;
		hashcode ^= symbol.hashCode();
		hashcode ^= market.hashCode();
		
		return hashcode;
	}
	
	/**
	 * 
	 * @return Daily prices
	 */
	public CandleSerie getCandles(){	
		return candles;
		
	}
	
	public void setCandles(CandleSerie prices) {
		this.candles = prices;
	}
	

	protected VolatilityTermStructure getImplicitVolatility(TimeFrame pillar) throws NotEnoughDataException{
		
		VolatilityTermStructure volCurve = volatilityMap.get(pillar.toString());
		
		if (volCurve == null){
			
			throw new NotEnoughDataException("No volatilities for pillar:"+pillar.toString());
		}
		
		return volCurve;
		
	}
	
	protected Candle getCandle(DateTime date) throws NotEnoughDataException{
		if (candles == null){
			throw new NotEnoughDataException("No prices, please add this instrument to the market manager.");
		}
		
		Candle result = candles.getValue(date);
		if (result == null )
			throw new NotEnoughDataException("No Candle at: "+date);
		return result;
		
	}
	/**
	 * get Implicit volatility OnDate 
	 * @param pillar
	 * @param une {@link DateTime} 
	 * @return {@link Rate}
	 * @throws NotEnoughDataException 
	 */
	protected Rate getImplicitVolatility(TimeFrame pillar,DateTime date) throws NotEnoughDataException{
		
		TermStructure volCurve = volatilityMap.get(pillar.toString());
		if (volCurve == null){
			throw new NotEnoughDataException("No volatilities for pillar:"+pillar.toString()+" at date"+ date);
		}
			
		return volCurve.getValue(date);
		
	}

	

	protected void setVolatility(TimeFrame pillar,VolatilityTermStructure volCurve) {
		
		// Create the Map if it does not exist 
		if (volatilityMap == null)
			volatilityMap = new IdentityHashMap<Integer, VolatilityTermStructure>();
		
		this.volatilityMap.put(pillar.hashCode(), volCurve);
	}



	public void setQuotes(QuoteSerie quotes) {
		this.quotes = quotes;
	}



	public QuoteSerie getQuotes() {
		return quotes;
	}
	
	
	
}
