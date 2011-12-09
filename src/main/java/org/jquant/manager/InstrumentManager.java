package org.jquant.manager;

import org.jquant.instrument.BaseInstrument;
import org.jquant.model.InstrumentType;
import org.jquant.model.Symbol;
import org.springframework.stereotype.Component;

/**
 * Instrument Manager, retreives Instruments from the data providers 
 * @author patrick.merheb
 *
 */
@Component
public class InstrumentManager {

	/**
	 * Renvoie un instrument à partir de son Symbole
	 * @param symbol un {@link Symbol}
	 * @return un instrument (Equity, Forex, Future ...) voir: {@link BaseInstrument}
	 */
	public BaseInstrument fetchInstrument(Symbol symbol){
		// Parcourt tous les types d'instruments à partir de l'instrument reader
		
		return null;
	}
	
	/**
	 * Résout un instrument à partir de son Symbole et de son type 
	 * <p> Cette méthode est plus rapide que {@link #fetchInstrument(Symbol)}
	 * @param type le {@link InstrumentType}
	 * @param symbol le {@link Symbol} de l'instrument 
	 * @return un instrument (Equity, Forex, Future ...) voir : {@link BaseInstrument}
	 */
	public BaseInstrument fetchInstrument(InstrumentType type, Symbol symbol){
		// Cette fois ci le type d'instrument nous permet de résoudre l'instrument plus rapidement à partir du symbole 
		
		return null;
	}
	
}
