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
import org.jquant.model.MICMarketPlace;



/**
 * <b>Description :</b> Un ensemble de symboles pré-remplis 
 * <br>
 * <b>History:</b>
 * 
 * <br>
 *
 *  @author Patrick Merheb
 */
public interface Symbols {
	
    // forex
	InstrumentId EURUSD = new InstrumentId(JQuantDataProvider.LOUXOR,"EURUSD",InstrumentType.FOREX,MICMarketPlace.NO_MIC,Currency.USD);
	InstrumentId GBPUSD = new InstrumentId(JQuantDataProvider.LOUXOR,"GBPUSD",InstrumentType.FOREX,MICMarketPlace.NO_MIC,Currency.USD);
	InstrumentId USDJPY = new InstrumentId(JQuantDataProvider.LOUXOR,"USDJPY",InstrumentType.FOREX,MICMarketPlace.NO_MIC,Currency.JPY);
	InstrumentId USDCHF = new InstrumentId(JQuantDataProvider.LOUXOR,"USDCHF",InstrumentType.FOREX,MICMarketPlace.NO_MIC,Currency.CHF);
	InstrumentId USDCAD = new InstrumentId(JQuantDataProvider.LOUXOR,"USDCAD",InstrumentType.FOREX,MICMarketPlace.NO_MIC,Currency.CAD);
	InstrumentId AUDUSD = new InstrumentId(JQuantDataProvider.LOUXOR,"AUDUSD",InstrumentType.FOREX,MICMarketPlace.NO_MIC,Currency.USD);
	InstrumentId EURJPY = new InstrumentId(JQuantDataProvider.LOUXOR,"EURJPY",InstrumentType.FOREX,MICMarketPlace.NO_MIC,Currency.JPY);
	InstrumentId EURCHF = new InstrumentId(JQuantDataProvider.LOUXOR,"EURCHF",InstrumentType.FOREX,MICMarketPlace.NO_MIC,Currency.CHF);
	InstrumentId EURGBP = new InstrumentId(JQuantDataProvider.LOUXOR,"EURGBP",InstrumentType.FOREX,MICMarketPlace.NO_MIC,Currency.GBP);
	InstrumentId EURCAD = new InstrumentId(JQuantDataProvider.LOUXOR,"EURCAD",InstrumentType.FOREX,MICMarketPlace.NO_MIC,Currency.CAD);
	InstrumentId GBPJPY = new InstrumentId(JQuantDataProvider.LOUXOR,"GBPJPY",InstrumentType.FOREX,MICMarketPlace.NO_MIC,Currency.JPY);
	InstrumentId GBPCHF = new InstrumentId(JQuantDataProvider.LOUXOR,"GBPCHF",InstrumentType.FOREX,MICMarketPlace.NO_MIC,Currency.CHF);

	
	// index
	InstrumentId DAX = new InstrumentId(JQuantDataProvider.LOUXOR,"DAX",InstrumentType.INDEX,MICMarketPlace.NO_MIC,null);
	InstrumentId SP500 = new InstrumentId(JQuantDataProvider.LOUXOR,"SP500",InstrumentType.INDEX,MICMarketPlace.NO_MIC,null);
	InstrumentId NASDAQ = new InstrumentId(JQuantDataProvider.LOUXOR,"NASDAQ",InstrumentType.INDEX,MICMarketPlace.NO_MIC,null);
	InstrumentId NIKKEI225 = new InstrumentId(JQuantDataProvider.LOUXOR,"NIKKEI225",InstrumentType.INDEX,MICMarketPlace.NO_MIC,null);
	InstrumentId CRB = new InstrumentId(JQuantDataProvider.LOUXOR,"CRB",InstrumentType.INDEX,MICMarketPlace.NO_MIC,null);
	InstrumentId CAC40 = new InstrumentId(JQuantDataProvider.LOUXOR,"CAC40",InstrumentType.INDEX,MICMarketPlace.NO_MIC,null);
	
	// bonds
	InstrumentId GERMANBUND = new InstrumentId(JQuantDataProvider.LOUXOR,"GERMANBUND",InstrumentType.BOND,MICMarketPlace.NO_MIC,Currency.EUR);
	InstrumentId TNOTES = new InstrumentId(JQuantDataProvider.LOUXOR,"TNOTES",InstrumentType.BOND,MICMarketPlace.NO_MIC,Currency.USD);
	
	// commodity
	InstrumentId CRUDEOIL = new InstrumentId(JQuantDataProvider.LOUXOR,"CRUDEOIL",InstrumentType.FUTURE,MICMarketPlace.XCBT,Currency.USD);
	InstrumentId NATURALGAS = new InstrumentId(JQuantDataProvider.LOUXOR,"NATURALGAS",InstrumentType.FUTURE,MICMarketPlace.XCBT,Currency.USD);
	InstrumentId COFFEE = new InstrumentId(JQuantDataProvider.LOUXOR,"COFFEE",InstrumentType.FUTURE,MICMarketPlace.XCBT,Currency.USD);
	InstrumentId AMGEN = new InstrumentId(JQuantDataProvider.LOUXOR,"AMGEN",InstrumentType.FUTURE,MICMarketPlace.XCBT,Currency.USD);
	InstrumentId MERCK = new InstrumentId(JQuantDataProvider.LOUXOR,"MERCK",InstrumentType.FUTURE,MICMarketPlace.XCBT,Currency.USD);
	InstrumentId SOYBEANS = new InstrumentId(JQuantDataProvider.LOUXOR,"SOYBEANS",InstrumentType.FUTURE,MICMarketPlace.XCBT,Currency.USD);
	InstrumentId LUMBER = new InstrumentId(JQuantDataProvider.LOUXOR,"LUMBER",InstrumentType.FUTURE,MICMarketPlace.XCBT,Currency.USD);
	InstrumentId GOLD = new InstrumentId(JQuantDataProvider.LOUXOR,"GOLD",InstrumentType.FUTURE,MICMarketPlace.XCBT,Currency.USD);
	InstrumentId SILVER = new InstrumentId(JQuantDataProvider.LOUXOR,"SILVER",InstrumentType.FUTURE,MICMarketPlace.XCBT,Currency.USD);
	InstrumentId OIL = new InstrumentId(JQuantDataProvider.LOUXOR,"OIL",InstrumentType.FUTURE,MICMarketPlace.XCBT,Currency.USD);
	InstrumentId GAS = new InstrumentId(JQuantDataProvider.LOUXOR,"GAS",InstrumentType.FUTURE,MICMarketPlace.XCBT,Currency.USD);
	InstrumentId COPPER = new InstrumentId(JQuantDataProvider.LOUXOR,"COPPER",InstrumentType.FUTURE,MICMarketPlace.XCBT,Currency.USD);

	// stocks
	InstrumentId MSFT = new InstrumentId(JQuantDataProvider.LOUXOR,"US5949181045",InstrumentType.EQUITY,MICMarketPlace.XFRA,Currency.EUR);
	InstrumentId GOOG = new InstrumentId(JQuantDataProvider.LOUXOR,"US38259P5089",InstrumentType.EQUITY,MICMarketPlace.XNMS,Currency.USD);
	InstrumentId IBM = new InstrumentId(JQuantDataProvider.LOUXOR,"US4592001014",InstrumentType.EQUITY, MICMarketPlace.XNYS,Currency.USD);
	InstrumentId HEINZ = new InstrumentId(JQuantDataProvider.LOUXOR,"US4230741039",InstrumentType.EQUITY, MICMarketPlace.HDG_US,Currency.USD);
	

}
