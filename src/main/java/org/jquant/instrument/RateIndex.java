package org.jquant.instrument;

import org.jquant.core.BaseInstrument;
import org.jquant.core.MICMarketPlace;
import org.jquant.core.Symbol;
import org.jquant.model.Currency;
import org.jquant.time.TimeFrame;

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
	protected TimeFrame timeFrame;
	
	public RateIndex(Symbol symbol, TimeFrame timeFrame) {
		super(symbol, MICMarketPlace.NO_MIC);
		this.timeFrame = timeFrame;
	}
	

}
