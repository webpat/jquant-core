package org.jquant.core;

import java.util.IdentityHashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.jquant.exception.NotEnoughDataException;
import org.jquant.model.Currency;
import org.jquant.time.TimeFrame;



/**
 * <b>Description :</b> This abstract class represents contains the basics of an instrument specification 
 * (type, market, expiry date, currency ...)
 * For most specific instruments attributes  see specific child classes
 * <p>For a given Data Provider a symbol/market tuple should be unique  
 * The volatility Map use an IdentityHashMap (use of the == operator instead of the equals Method)
 * </p>
 * This class is immutable.
 * <br/>
 * <b>History:</b><br>
 * @author patrick.merheb
 *
 */
public abstract class BaseInstrument implements IInstrument {

	protected MICMarketPlace market;
	protected Symbol symbol = null;	
	protected Currency currency;

	
	private CandleSerie prices;
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
	
	
	public String getName(){
		return this.symbol.toString();
	}
	
	
	
	@Override
	public int hashCode() {
		int hashcode = 0;
		hashcode ^= symbol.hashCode();
		hashcode ^= market.hashCode();
		
		return hashcode;
	}
	
	public CandleSerie getPrices(){	
		return prices;
		
	}
	
	public void setPrices(CandleSerie prices) {
		this.prices = prices;
	}
	

	public VolatilityTermStructure getImplicitVolatility(TimeFrame pillar) throws NotEnoughDataException{
		
		VolatilityTermStructure volCurve = volatilityMap.get(pillar.toString());
		
		if (volCurve == null){
			
			throw new NotEnoughDataException("No volatilities for pillar:"+pillar.toString());
		}
		
		return volCurve;
		
	}
	
	public Candle getCandle(DateTime date) throws NotEnoughDataException{
		if (prices == null){
			throw new NotEnoughDataException("No prices, please fill your instrument.");
			//TODO: TIme SERIE READER FETCH
		}
		
		Candle result = prices.getValue(date);
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
	public Rate getImplicitVolatility(TimeFrame pillar,DateTime date) throws NotEnoughDataException{
		
		TermStructure volCurve = volatilityMap.get(pillar.toString());
		if (volCurve == null){
			throw new NotEnoughDataException("No volatilities for pillar:"+pillar.toString()+" at date"+ date);
		}
			
		return volCurve.getValue(date);
		
	}

	

	public void setVolatility(TimeFrame pillar,VolatilityTermStructure volCurve) {
		
		// Create the Map if it does not exist 
		if (volatilityMap == null)
			volatilityMap = new IdentityHashMap<Integer, VolatilityTermStructure>();
		
		this.volatilityMap.put(pillar.hashCode(), volCurve);
	}
	
	/**
	 * get surface volatility OnDate 
	 * @param date
	 * @return
	 */
	public Map<Integer, VolatilityTermStructure> getSurfaceVolatility(DateTime date){
		
		return null;
		
	}
	
}
