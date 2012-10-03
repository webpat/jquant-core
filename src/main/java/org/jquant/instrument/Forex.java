package org.jquant.instrument;

import org.jquant.data.JQuantDataProvider;
import org.jquant.model.Currency;
import org.jquant.model.InstrumentId;
import org.jquant.model.InstrumentType;
import org.jquant.model.MarketIdentifierCode;

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

	

	private final Currency exchangedCurrency;
		
	
	/**
	 * 
	 * @param provider : Le {@link JQuantDataProvider} chez qui on prend la quotation
	 * @param exchangedCurrency La devise échangée (ex : EURUSD, EUR est la devise échangée)
	 * @param priceCurrency La devise de réglement (ex : EURUSD, USD est la devise de réglement) 
	 */
	public Forex(JQuantDataProvider provider, Currency exchangedCurrency, Currency priceCurrency) {
		super(new InstrumentId(provider, exchangedCurrency.name()+priceCurrency.name(),InstrumentType.FOREX,MarketIdentifierCode.NO_MIC,priceCurrency));
		
		this.exchangedCurrency = exchangedCurrency;
	}
	

	public Currency getExchangedCurrency() {
		return exchangedCurrency;
	}

	public Currency getPriceCurrency() {
		return symbol.getCurrency();
	}

	
}
