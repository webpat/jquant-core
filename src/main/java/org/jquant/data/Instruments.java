/****

    activequant - activestocks.eu

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.

	
	contact  : contact@activestocks.eu
    homepage : http://www.activestocks.eu

****/
package org.jquant.data;

import org.jquant.model.Currency;
import org.jquant.model.InstrumentId;
import org.jquant.model.InstrumentType;
import org.jquant.model.MarketIdentifierCode;



/**
 * <b>Description :</b> Un ensemble de symboles pré-remplis valides sur LOUXOR 
 * <br>
 * <b>History:</b>
 * 
 * <br>
 *
 *  @author Patrick Merheb
 */
public interface Instruments {
	
    // forex
	InstrumentId EURUSD = new InstrumentId(JQuantDataProvider.LOUXOR,"EURUSD",InstrumentType.FOREX,MarketIdentifierCode.NO_MIC,Currency.USD);
	InstrumentId GBPUSD = new InstrumentId(JQuantDataProvider.LOUXOR,"GBPUSD",InstrumentType.FOREX,MarketIdentifierCode.NO_MIC,Currency.USD);
	InstrumentId USDJPY = new InstrumentId(JQuantDataProvider.LOUXOR,"USDJPY",InstrumentType.FOREX,MarketIdentifierCode.NO_MIC,Currency.JPY);
	InstrumentId USDCHF = new InstrumentId(JQuantDataProvider.LOUXOR,"USDCHF",InstrumentType.FOREX,MarketIdentifierCode.NO_MIC,Currency.CHF);
	InstrumentId USDCAD = new InstrumentId(JQuantDataProvider.LOUXOR,"USDCAD",InstrumentType.FOREX,MarketIdentifierCode.NO_MIC,Currency.CAD);
	InstrumentId AUDUSD = new InstrumentId(JQuantDataProvider.LOUXOR,"AUDUSD",InstrumentType.FOREX,MarketIdentifierCode.NO_MIC,Currency.USD);
	InstrumentId EURJPY = new InstrumentId(JQuantDataProvider.LOUXOR,"EURJPY",InstrumentType.FOREX,MarketIdentifierCode.NO_MIC,Currency.JPY);
	InstrumentId EURCHF = new InstrumentId(JQuantDataProvider.LOUXOR,"EURCHF",InstrumentType.FOREX,MarketIdentifierCode.NO_MIC,Currency.CHF);
	InstrumentId EURGBP = new InstrumentId(JQuantDataProvider.LOUXOR,"EURGBP",InstrumentType.FOREX,MarketIdentifierCode.NO_MIC,Currency.GBP);
	InstrumentId EURCAD = new InstrumentId(JQuantDataProvider.LOUXOR,"EURCAD",InstrumentType.FOREX,MarketIdentifierCode.NO_MIC,Currency.CAD);
	InstrumentId GBPJPY = new InstrumentId(JQuantDataProvider.LOUXOR,"GBPJPY",InstrumentType.FOREX,MarketIdentifierCode.NO_MIC,Currency.JPY);
	InstrumentId GBPCHF = new InstrumentId(JQuantDataProvider.LOUXOR,"GBPCHF",InstrumentType.FOREX,MarketIdentifierCode.NO_MIC,Currency.CHF);

	
	// index
	InstrumentId DAX = new InstrumentId(JQuantDataProvider.LOUXOR,"IND_DAX_30",InstrumentType.INDEX,MarketIdentifierCode.NO_MIC,null);
	InstrumentId SP500 = new InstrumentId(JQuantDataProvider.LOUXOR,"IND_S&P_500",InstrumentType.INDEX,MarketIdentifierCode.NO_MIC,null);
	InstrumentId NASDAQ = new InstrumentId(JQuantDataProvider.LOUXOR,"IND_NASDAQ_COMPOSITE_INDEX",InstrumentType.INDEX,MarketIdentifierCode.NO_MIC,null);
	InstrumentId NIKKEI225 = new InstrumentId(JQuantDataProvider.LOUXOR,"IND_NIKKEI_225",InstrumentType.INDEX,MarketIdentifierCode.NO_MIC,null);
	InstrumentId CAC40 = new InstrumentId(JQuantDataProvider.LOUXOR,"IND_CAC_40",InstrumentType.INDEX,MarketIdentifierCode.NO_MIC,null);
	
	// Future sur bonds
	InstrumentId US_30Y = new InstrumentId(JQuantDataProvider.LOUXOR,"US 30Y BOND",InstrumentType.GENERIC_FUTURE,MarketIdentifierCode.XCBT,Currency.USD);
	InstrumentId US_10Y = new InstrumentId(JQuantDataProvider.LOUXOR,"US 10Y NOTE",InstrumentType.GENERIC_FUTURE,MarketIdentifierCode.XCBT,Currency.USD);
	InstrumentId US_5Y = new InstrumentId(JQuantDataProvider.LOUXOR,"US 5Y NOTE",InstrumentType.GENERIC_FUTURE,MarketIdentifierCode.XCBT,Currency.USD);
	InstrumentId BUND = new InstrumentId(JQuantDataProvider.LOUXOR,"EURO BUND",InstrumentType.GENERIC_FUTURE,MarketIdentifierCode.XEUR,Currency.EUR);
	InstrumentId BOBL = new InstrumentId(JQuantDataProvider.LOUXOR,"EURO BOBL",InstrumentType.GENERIC_FUTURE,MarketIdentifierCode.XEUR,Currency.EUR);
	InstrumentId SCHATZ = new InstrumentId(JQuantDataProvider.LOUXOR,"EURO SCHATZ",InstrumentType.GENERIC_FUTURE,MarketIdentifierCode.XEUR,Currency.EUR);
	
	// Generic Futures sur commodity
	InstrumentId CRUDEOIL = new InstrumentId(JQuantDataProvider.LOUXOR,"CRUDE OIL",InstrumentType.GENERIC_FUTURE,MarketIdentifierCode.XNYM,Currency.USD);
	InstrumentId NATURALGAS = new InstrumentId(JQuantDataProvider.LOUXOR,"NATURAL GAS",InstrumentType.GENERIC_FUTURE,MarketIdentifierCode.XNYM,Currency.USD);
	InstrumentId COFFEE = new InstrumentId(JQuantDataProvider.LOUXOR,"COFFEE",InstrumentType.GENERIC_FUTURE,MarketIdentifierCode.XNYF,Currency.USD);
	InstrumentId SOYBEAN = new InstrumentId(JQuantDataProvider.LOUXOR,"SOYBEAN",InstrumentType.GENERIC_FUTURE,MarketIdentifierCode.XCBT,Currency.USD);
	InstrumentId SOYBEAN_OIL = new InstrumentId(JQuantDataProvider.LOUXOR,"SOYBEAN OIL",InstrumentType.GENERIC_FUTURE,MarketIdentifierCode.XCBT,Currency.USD);
	InstrumentId SOYBEAN_MEAL = new InstrumentId(JQuantDataProvider.LOUXOR,"SOYBEAN MEAL",InstrumentType.GENERIC_FUTURE,MarketIdentifierCode.XCBT,Currency.USD);
	InstrumentId LUMBER = new InstrumentId(JQuantDataProvider.LOUXOR,"LUMBER",InstrumentType.GENERIC_FUTURE,MarketIdentifierCode.XCME,Currency.USD);
	InstrumentId GOLD = new InstrumentId(JQuantDataProvider.LOUXOR,"GOLD 100",InstrumentType.GENERIC_FUTURE,MarketIdentifierCode.XCEC,Currency.USD);
	InstrumentId SILVER = new InstrumentId(JQuantDataProvider.LOUXOR,"SILVER",InstrumentType.GENERIC_FUTURE,MarketIdentifierCode.XCEC,Currency.USD);
	InstrumentId NICKEL = new InstrumentId(JQuantDataProvider.LOUXOR,"NICKEL",InstrumentType.GENERIC_FUTURE,MarketIdentifierCode.XLME,Currency.USD);
	InstrumentId COPPER = new InstrumentId(JQuantDataProvider.LOUXOR,"COPPER",InstrumentType.GENERIC_FUTURE,MarketIdentifierCode.XCEC,Currency.USD);

	// stocks
	InstrumentId MSFT = new InstrumentId(JQuantDataProvider.LOUXOR,"US5949181045",InstrumentType.EQUITY,MarketIdentifierCode.XFRA,Currency.EUR);
	InstrumentId GOOG = new InstrumentId(JQuantDataProvider.LOUXOR,"US38259P5089",InstrumentType.EQUITY,MarketIdentifierCode.XNMS,Currency.USD);
	InstrumentId IBM = new InstrumentId(JQuantDataProvider.LOUXOR,"US4592001014",InstrumentType.EQUITY, MarketIdentifierCode.XNYS,Currency.USD);
	InstrumentId HEINZ = new InstrumentId(JQuantDataProvider.LOUXOR,"US4230741039",InstrumentType.EQUITY, MarketIdentifierCode.XNYM,Currency.USD);
	
	//trackers 
	InstrumentId LYXOR_TOPIX = new InstrumentId(JQuantDataProvider.LOUXOR,"FR0010245514",InstrumentType.TRACKER,MarketIdentifierCode.XPAR,Currency.EUR);
	InstrumentId LYXOR_NASDAQ_100 = new InstrumentId(JQuantDataProvider.LOUXOR,"FR0007063177",InstrumentType.TRACKER,MarketIdentifierCode.XPAR,Currency.EUR);

	
	
}
