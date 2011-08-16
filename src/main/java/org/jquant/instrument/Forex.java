package org.jquant.instrument;

import org.jquant.data.DataProvider;
import org.jquant.model.Currency;
import org.jquant.model.MICMarketPlace;
import org.jquant.model.MarketDataProvider;
import org.jquant.model.Symbol;

/**
 * <b>Description :</b> Une paire de devises échangées sur les marchés FOREX 
 * <br>
 * <b>History:</b>
 * 
 * <br>
 *
 *  @author Patrick Merheb
 */
public class Forex extends BaseInstrument {

	

	private Currency exchangedCurrency;
	private Currency priceCurrency;
	
	
	public Forex(Symbol symbol, MICMarketPlace market) {
		super(symbol, market);
	}
	
	
	/**
	 * 
	 * @param provider : Le {@link MarketDataProvider} chez qui on prend la quotation
	 * @param exchangedCurrency La devise échangée (ex : EURUSD, EUR est la devise échangée)
	 * @param priceCurrency La devise de réglement (ex : EURUSD, USD est la devise de réglement) 
	 */
	public Forex(DataProvider provider, Currency exchangedCurrency, Currency priceCurrency) {
		super(new Symbol(provider, exchangedCurrency.name()+priceCurrency.name()),MICMarketPlace.NO_MIC);
		
		this.exchangedCurrency = exchangedCurrency;
		this.priceCurrency = priceCurrency;
	}
	
	//TODO : Test it
//	public double getForwardPrice(DateTime startDate, DateTime endDate) throws TermStructureException, NotEnoughDataException {
//		double forwardPrice = 0.0;
//		
//		TermStructureFactory factory = JQuantApplicationContext.getTermStructureFactory();
//		ZeroCouponCurve zeroCurveExchangedCurrency = factory.getDepositCurve(getExchangedCurrency(), startDate);
//		ZeroCouponCurve zeroCurvePriceCurrency = factory.getDepositCurve(getPriceCurrency(), startDate);
//		double rateExchCurr = zeroCurveExchangedCurrency.getSpotYield(Days.daysBetween(startDate, endDate).getDays());
//		double ratePriceCurr = zeroCurvePriceCurrency.getSpotYield(Days.daysBetween(startDate, endDate).getDays());
//		DayCounter dcExchCurr = getExchangedCurrency().getDayCounter();
//		DayCounter dcPriceCurr = getPriceCurrency().getDayCounter();
//
//		double spot = getCandle(startDate).getClose();
//		double tmp = -rateExchCurr*dcExchCurr.calculateYearFraction(startDate,endDate) +
//				ratePriceCurr*dcPriceCurr.calculateYearFraction(startDate,endDate);
//		forwardPrice = spot*Math.exp(tmp);
//		return forwardPrice;
//	}

	public Currency getExchangedCurrency() {
		return exchangedCurrency;
	}

	public void setExchangedCurrency(Currency exchangedCurrency) {
		this.exchangedCurrency = exchangedCurrency;
	}

	public Currency getPriceCurrency() {
		return priceCurrency;
	}

	public void setPriceCurrency(Currency priceCurrency) {
		this.priceCurrency = priceCurrency;
	}
	
	
	
}
