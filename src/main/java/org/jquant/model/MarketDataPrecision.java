package org.jquant.model;

/**
 * Market data resolution/precision from the lowest to the highest.  
 * <ul>
 * <li>CANDLE : Echantillonage par intervalles de temps avec Open High Low Close</li>
 * <li>TRADE : Intraday Haute Fréquence buy/sell</li>
   <li>QUOTE : Intraday Moyenne Fréquence bid/ask </li>
 * </ul>
 * @author patrick.merheb
 *
 */
public enum MarketDataPrecision {

	CANDLE,
	TRADE,
	QUOTE,
}
