package org.jquant.instrument;

import org.joda.time.Period;
import org.jquant.model.Currency;
import org.jquant.model.MICMarketPlace;
import org.jquant.model.Symbol;

/**
 * <b>Description :</b> Une taux sans risque qui se rapporte à une devise {@link Currency}  
 * <br>
 * <b>History:</b>
 * 
 * <br>
 *
 *  @author Patrick Merheb
 */
public class RateIndex extends BaseInstrument {
	
	protected Currency currency;
	protected Period length;
	
	public RateIndex(Symbol symbol, Period length) {
		super(symbol, MICMarketPlace.NO_MIC);
		this.length = length;
	}
	

}
