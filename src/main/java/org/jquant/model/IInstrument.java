/*
 * Created on 13 juin 07
 */
package org.jquant.model;



/**
 * Basic Interface for an InstrumentId
 * <p> Holds a <b>InstrumentId</b>, a Market and a Currency
 * <p>
 * A tradeable asset or negotiable item such as a security, commodity, derivative or index, or any item that underlies a derivative.
 *
 *@author Patrick Merheb 
 *@see Currency
 *@see MICMarketPlace
 *@see InstrumentId
 */
public interface IInstrument {

    /**
     * Dans quelle monnaie l'instrument est il quoté 
     * @return une {@link Currency}
     */
    public Currency getCurrency();
    
    
    /**
     * Quelle est sa paire Provider!Code!Exchange
     * @return un {@link InstrumentId}
     */
    public InstrumentId getId();
    
}
