/*
 * Created on 13 juin 07
 */
package org.jquant.model;



/**
 * Basic Interface for an Instrument
 * <p> Holds a <b>Symbol</b>, a Market and a Currency
 * <p>
 * A tradeable asset or negotiable item such as a security, commodity, derivative or index, or any item that underlies a derivative.
 *
 *@author Patrick Merheb 
 *@see Currency
 *@see MICMarketPlace
 *@see Symbol
 */
public interface IInstrument {

    /**
     * Dans quelle monnaie l'instrument est il quoté 
     * @return une {@link Currency}
     */
    public Currency getCurrency();
    
    
    /**
     * Quelle est sa paire Provider!Code!Exchange
     * @return un {@link Symbol}
     */
    public Symbol getSymbol();
    
}
