/*
 * Created on 13 juin 07
 */
package org.jquant.core;

import org.jquant.model.Currency;


/**
 * Basic Interface for an IInstrument
 * @author JQUANT TEAM 
 *
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
