package org.jquant.instrument;

import org.jquant.model.MICMarketPlace;
import org.jquant.model.Symbol;


/**
 * <b>Description :</b> Une indice de marché, ne s'échange pas mais sert de base à la quotation des trackers qui eux s'échangent  
 * <br>
 * <b>History:</b>
 * 
 * <br>
 *
 *  @author Patrick Merheb
 */
public class Index extends BaseInstrument {

	
	
	public Index(Symbol symbol, MICMarketPlace market) {
		super(symbol, market);
	}
	

	
}
