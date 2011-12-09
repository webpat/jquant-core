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
	Symbol EURUSD = new Symbol(JQuantDataProvider.LOUXOR,"EURUSD",InstrumentType.FOREX);
	Symbol GBPUSD = new Symbol(JQuantDataProvider.LOUXOR,"GBPUSD",InstrumentType.FOREX);
	Symbol USDJPY = new Symbol(JQuantDataProvider.LOUXOR,"USDJPY",InstrumentType.FOREX);
	Symbol USDCHF = new Symbol(JQuantDataProvider.LOUXOR,"USDCHF",InstrumentType.FOREX);
	Symbol USDCAD = new Symbol(JQuantDataProvider.LOUXOR,"USDCAD",InstrumentType.FOREX);
	Symbol AUDUSD = new Symbol(JQuantDataProvider.LOUXOR,"AUDUSD",InstrumentType.FOREX);
	Symbol EURJPY = new Symbol(JQuantDataProvider.LOUXOR,"EURJPY",InstrumentType.FOREX);
	Symbol EURCHF = new Symbol(JQuantDataProvider.LOUXOR,"EURCHF",InstrumentType.FOREX);
	Symbol EURGBP = new Symbol(JQuantDataProvider.LOUXOR,"EURGBP",InstrumentType.FOREX);
	Symbol EURCAD = new Symbol(JQuantDataProvider.LOUXOR,"EURCAD",InstrumentType.FOREX);
	Symbol GBPJPY = new Symbol(JQuantDataProvider.LOUXOR,"GBPJPY",InstrumentType.FOREX);
	Symbol GBPCHF = new Symbol(JQuantDataProvider.LOUXOR,"GBPCHF",InstrumentType.FOREX);
	Symbol CHFJPY = new Symbol(JQuantDataProvider.LOUXOR,"CHFJPY",InstrumentType.FOREX);
	Symbol NZDUSD = new Symbol(JQuantDataProvider.LOUXOR,"NZDUSD",InstrumentType.FOREX);
	Symbol USDZAR = new Symbol(JQuantDataProvider.LOUXOR,"USDZAR",InstrumentType.FOREX);
	Symbol USDNOK = new Symbol(JQuantDataProvider.LOUXOR,"USDNOK",InstrumentType.FOREX);
	Symbol EURNOK = new Symbol(JQuantDataProvider.LOUXOR,"EURNOK",InstrumentType.FOREX);
	Symbol USDSEK = new Symbol(JQuantDataProvider.LOUXOR,"USDSEK",InstrumentType.FOREX);
	Symbol EURSEK = new Symbol(JQuantDataProvider.LOUXOR,"EURSEK",InstrumentType.FOREX);
	Symbol USDMXN = new Symbol(JQuantDataProvider.LOUXOR,"USDMXN",InstrumentType.FOREX);
	Symbol USDINR = new Symbol(JQuantDataProvider.LOUXOR,"USDINR",InstrumentType.FOREX);
	Symbol GBPINR = new Symbol(JQuantDataProvider.LOUXOR,"GBPINR",InstrumentType.FOREX);
	Symbol USDRMB = new Symbol(JQuantDataProvider.LOUXOR,"USDRMB",InstrumentType.FOREX);
	
	// index
	Symbol DAX = new Symbol(JQuantDataProvider.LOUXOR,"DAX",InstrumentType.INDEX);
	Symbol SP500 = new Symbol(JQuantDataProvider.LOUXOR,"SP500",InstrumentType.INDEX);
	Symbol NASDAQ = new Symbol(JQuantDataProvider.LOUXOR,"NASDAQ",InstrumentType.INDEX);
	Symbol NIKKEI225 = new Symbol(JQuantDataProvider.LOUXOR,"NIKKEI225",InstrumentType.INDEX);
	Symbol CRB = new Symbol(JQuantDataProvider.LOUXOR,"CRB",InstrumentType.INDEX);
	Symbol CAC40 = new Symbol(JQuantDataProvider.LOUXOR,"CAC40",InstrumentType.INDEX);
	
	// bonds
	Symbol GERMANBUND = new Symbol(JQuantDataProvider.LOUXOR,"GERMANBUND",InstrumentType.BOND);
	Symbol TNOTES = new Symbol(JQuantDataProvider.LOUXOR,"TNOTES",InstrumentType.BOND);
	
	// commodity
	Symbol CRUDEOIL = new Symbol(JQuantDataProvider.LOUXOR,"CRUDEOIL",InstrumentType.FUTURE);
	Symbol NATURALGAS = new Symbol(JQuantDataProvider.LOUXOR,"NATURALGAS",InstrumentType.FUTURE);
	Symbol COFFEE = new Symbol(JQuantDataProvider.LOUXOR,"COFFEE",InstrumentType.FUTURE);
	Symbol AMGEN = new Symbol(JQuantDataProvider.LOUXOR,"AMGEN",InstrumentType.FUTURE);
	Symbol MERCK = new Symbol(JQuantDataProvider.LOUXOR,"MERCK",InstrumentType.FUTURE);
	Symbol SOYBEANS = new Symbol(JQuantDataProvider.LOUXOR,"SOYBEANS",InstrumentType.FUTURE);
	Symbol LUMBER = new Symbol(JQuantDataProvider.LOUXOR,"LUMBER",InstrumentType.FUTURE);
	Symbol GOLD = new Symbol(JQuantDataProvider.LOUXOR,"GOLD",InstrumentType.FUTURE);
	Symbol SILVER = new Symbol(JQuantDataProvider.LOUXOR,"SILVER",InstrumentType.FUTURE);
	Symbol OIL = new Symbol(JQuantDataProvider.LOUXOR,"OIL",InstrumentType.FUTURE);
	Symbol GAS = new Symbol(JQuantDataProvider.LOUXOR,"GAS",InstrumentType.FUTURE);
	Symbol COPPER = new Symbol(JQuantDataProvider.LOUXOR,"COPPER",InstrumentType.FUTURE);

	// stocks
	Symbol MSFT = new Symbol(JQuantDataProvider.LOUXOR,"US5949181045",InstrumentType.EQUITY);
	Symbol GOOG = new Symbol(JQuantDataProvider.LOUXOR,"US38259P5089",InstrumentType.EQUITY);
	Symbol ALCATEL= new Symbol(JQuantDataProvider.LOUXOR,"FR0000130007",InstrumentType.EQUITY);
	
	
}
