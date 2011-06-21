package org.jquant.instrument;

import org.joda.time.Period;
import org.jquant.core.BaseInstrument;
import org.jquant.core.MICMarketPlace;
import org.jquant.core.Symbol;
import org.jquant.model.Currency;

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
