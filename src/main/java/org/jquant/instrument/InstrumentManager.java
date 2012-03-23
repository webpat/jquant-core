package org.jquant.instrument;

import org.jquant.model.InstrumentType;
import org.jquant.model.InstrumentId;
import org.springframework.stereotype.Component;

/**
 * InstrumentId Manager, retreives Instruments from the data providers 
 * @author patrick.merheb
 *
 */
@Component
public class InstrumentManager {

	/**
	 * Renvoie un instrument à partir de son Symbole
	 * @param symbol un {@link InstrumentId}
	 * @return un instrument (Equity, Forex, Future ...) voir: {@link BaseInstrument}
	 */
	public BaseInstrument fetchInstrument(InstrumentId symbol){
		// Parcourt tous les types d'instruments à partir de l'instrument reader
		
		return null;
	}
	
	/**
	 * Résout un instrument à partir de son Symbole et de son type 
	 * <p> Cette méthode est plus rapide que {@link #fetchInstrument(InstrumentId)}
	 * @param type le {@link InstrumentType}
	 * @param symbol le {@link InstrumentId} de l'instrument 
	 * @return un instrument (Equity, Forex, Future ...) voir : {@link BaseInstrument}
	 */
	public BaseInstrument fetchInstrument(InstrumentType type, InstrumentId symbol){
		// Cette fois ci le type d'instrument nous permet de résoudre l'instrument plus rapidement à partir du symbole 
		
		return null;
	}
	
}
