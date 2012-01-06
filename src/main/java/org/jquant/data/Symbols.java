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

import org.jquant.model.InstrumentType;
import org.jquant.model.MICMarketPlace;
import org.jquant.model.Symbol;



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
	Symbol EURUSD = new Symbol(JQuantDataProvider.LOUXOR,"EURUSD",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol GBPUSD = new Symbol(JQuantDataProvider.LOUXOR,"GBPUSD",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol USDJPY = new Symbol(JQuantDataProvider.LOUXOR,"USDJPY",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol USDCHF = new Symbol(JQuantDataProvider.LOUXOR,"USDCHF",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol USDCAD = new Symbol(JQuantDataProvider.LOUXOR,"USDCAD",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol AUDUSD = new Symbol(JQuantDataProvider.LOUXOR,"AUDUSD",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol EURJPY = new Symbol(JQuantDataProvider.LOUXOR,"EURJPY",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol EURCHF = new Symbol(JQuantDataProvider.LOUXOR,"EURCHF",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol EURGBP = new Symbol(JQuantDataProvider.LOUXOR,"EURGBP",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol EURCAD = new Symbol(JQuantDataProvider.LOUXOR,"EURCAD",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol GBPJPY = new Symbol(JQuantDataProvider.LOUXOR,"GBPJPY",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol GBPCHF = new Symbol(JQuantDataProvider.LOUXOR,"GBPCHF",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol CHFJPY = new Symbol(JQuantDataProvider.LOUXOR,"CHFJPY",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol NZDUSD = new Symbol(JQuantDataProvider.LOUXOR,"NZDUSD",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol USDZAR = new Symbol(JQuantDataProvider.LOUXOR,"USDZAR",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol USDNOK = new Symbol(JQuantDataProvider.LOUXOR,"USDNOK",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol EURNOK = new Symbol(JQuantDataProvider.LOUXOR,"EURNOK",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol USDSEK = new Symbol(JQuantDataProvider.LOUXOR,"USDSEK",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol EURSEK = new Symbol(JQuantDataProvider.LOUXOR,"EURSEK",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol USDMXN = new Symbol(JQuantDataProvider.LOUXOR,"USDMXN",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol USDINR = new Symbol(JQuantDataProvider.LOUXOR,"USDINR",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol GBPINR = new Symbol(JQuantDataProvider.LOUXOR,"GBPINR",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	Symbol USDRMB = new Symbol(JQuantDataProvider.LOUXOR,"USDRMB",InstrumentType.FOREX,MICMarketPlace.NO_MIC);
	
	// index
	Symbol DAX = new Symbol(JQuantDataProvider.LOUXOR,"DAX",InstrumentType.INDEX,MICMarketPlace.NO_MIC);
	Symbol SP500 = new Symbol(JQuantDataProvider.LOUXOR,"SP500",InstrumentType.INDEX,MICMarketPlace.NO_MIC);
	Symbol NASDAQ = new Symbol(JQuantDataProvider.LOUXOR,"NASDAQ",InstrumentType.INDEX,MICMarketPlace.NO_MIC);
	Symbol NIKKEI225 = new Symbol(JQuantDataProvider.LOUXOR,"NIKKEI225",InstrumentType.INDEX,MICMarketPlace.NO_MIC);
	Symbol CRB = new Symbol(JQuantDataProvider.LOUXOR,"CRB",InstrumentType.INDEX,MICMarketPlace.NO_MIC);
	Symbol CAC40 = new Symbol(JQuantDataProvider.LOUXOR,"CAC40",InstrumentType.INDEX,MICMarketPlace.NO_MIC);
	
	// bonds
	Symbol GERMANBUND = new Symbol(JQuantDataProvider.LOUXOR,"GERMANBUND",InstrumentType.BOND,MICMarketPlace.NO_MIC);
	Symbol TNOTES = new Symbol(JQuantDataProvider.LOUXOR,"TNOTES",InstrumentType.BOND,MICMarketPlace.NO_MIC);
	
	// commodity
	Symbol CRUDEOIL = new Symbol(JQuantDataProvider.LOUXOR,"CRUDEOIL",InstrumentType.FUTURE,MICMarketPlace.XCBT);
	Symbol NATURALGAS = new Symbol(JQuantDataProvider.LOUXOR,"NATURALGAS",InstrumentType.FUTURE,MICMarketPlace.XCBT);
	Symbol COFFEE = new Symbol(JQuantDataProvider.LOUXOR,"COFFEE",InstrumentType.FUTURE,MICMarketPlace.XCBT);
	Symbol AMGEN = new Symbol(JQuantDataProvider.LOUXOR,"AMGEN",InstrumentType.FUTURE,MICMarketPlace.XCBT);
	Symbol MERCK = new Symbol(JQuantDataProvider.LOUXOR,"MERCK",InstrumentType.FUTURE,MICMarketPlace.XCBT);
	Symbol SOYBEANS = new Symbol(JQuantDataProvider.LOUXOR,"SOYBEANS",InstrumentType.FUTURE,MICMarketPlace.XCBT);
	Symbol LUMBER = new Symbol(JQuantDataProvider.LOUXOR,"LUMBER",InstrumentType.FUTURE,MICMarketPlace.XCBT);
	Symbol GOLD = new Symbol(JQuantDataProvider.LOUXOR,"GOLD",InstrumentType.FUTURE,MICMarketPlace.XCBT);
	Symbol SILVER = new Symbol(JQuantDataProvider.LOUXOR,"SILVER",InstrumentType.FUTURE,MICMarketPlace.XCBT);
	Symbol OIL = new Symbol(JQuantDataProvider.LOUXOR,"OIL",InstrumentType.FUTURE,MICMarketPlace.XCBT);
	Symbol GAS = new Symbol(JQuantDataProvider.LOUXOR,"GAS",InstrumentType.FUTURE,MICMarketPlace.XCBT);
	Symbol COPPER = new Symbol(JQuantDataProvider.LOUXOR,"COPPER",InstrumentType.FUTURE,MICMarketPlace.XCBT);

	// stocks
	Symbol MSFT = new Symbol(JQuantDataProvider.LOUXOR,"US5949181045",InstrumentType.EQUITY,MICMarketPlace.XFRA);
	Symbol GOOG = new Symbol(JQuantDataProvider.LOUXOR,"US38259P5089",InstrumentType.EQUITY,MICMarketPlace.XNMS);
	Symbol IBM = new Symbol(JQuantDataProvider.LOUXOR,"US4592001014",InstrumentType.EQUITY, MICMarketPlace.XNYS);
	Symbol HEINZ = new Symbol(JQuantDataProvider.LOUXOR,"US4230741039",InstrumentType.EQUITY, MICMarketPlace.HDG_US);
	

}
