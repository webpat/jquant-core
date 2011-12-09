/*
 * Created on 13 juin 07
 */
package org.jquant.model;



/**
 * Basic Interface for an IInstrument
 * <p> Holds a <b>Symbol</b>, a Market and a Currency
 * @author JQUANT TEAM 
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
     * Sur quel marché l'instrument est il côté 
     * @return un {@link MICMarketPlace}
     */
    public MICMarketPlace getMarket();
    
    /**
     * Quelle est sa paire Provider!Code
     * @return un {@link Symbol}
     */
    public Symbol getSymbol();
    
}
