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

import org.jquant.core.Symbol;
import org.jquant.model.MarketDataProvider;



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
	Symbol EURUSD = new Symbol(DataProvider.LOUXOR,"EURUSD");
	Symbol GBPUSD = new Symbol(DataProvider.LOUXOR,"GBPUSD");
	Symbol USDJPY = new Symbol(DataProvider.LOUXOR,"USDJPY");
	Symbol USDCHF = new Symbol(DataProvider.LOUXOR,"USDCHF");
	Symbol USDCAD = new Symbol(DataProvider.LOUXOR,"USDCAD");
	Symbol AUDUSD = new Symbol(DataProvider.LOUXOR,"AUDUSD");
	Symbol EURJPY = new Symbol(DataProvider.LOUXOR,"EURJPY");
	Symbol EURCHF = new Symbol(DataProvider.LOUXOR,"EURCHF");
	Symbol EURGBP = new Symbol(DataProvider.LOUXOR,"EURGBP");
	Symbol EURCAD = new Symbol(DataProvider.LOUXOR,"EURCAD");
	Symbol GBPJPY = new Symbol(DataProvider.LOUXOR,"GBPJPY");
	Symbol GBPCHF = new Symbol(DataProvider.LOUXOR,"GBPCHF");
	Symbol CHFJPY = new Symbol(DataProvider.LOUXOR,"CHFJPY");
	Symbol NZDUSD = new Symbol(DataProvider.LOUXOR,"NZDUSD");
	Symbol USDZAR = new Symbol(DataProvider.LOUXOR,"USDZAR");
	Symbol USDNOK = new Symbol(DataProvider.LOUXOR,"USDNOK");
	Symbol EURNOK = new Symbol(DataProvider.LOUXOR,"EURNOK");
	Symbol USDSEK = new Symbol(DataProvider.LOUXOR,"USDSEK");
	Symbol EURSEK = new Symbol(DataProvider.LOUXOR,"EURSEK");
	Symbol USDMXN = new Symbol(DataProvider.LOUXOR,"USDMXN");
	Symbol USDINR = new Symbol(DataProvider.LOUXOR,"USDINR");
	Symbol GBPINR = new Symbol(DataProvider.LOUXOR,"GBPINR");
	Symbol USDRMB = new Symbol(DataProvider.LOUXOR,"USDRMB");
	
	// index
	Symbol DAX = new Symbol(DataProvider.LOUXOR,"DAX");
	Symbol SP500 = new Symbol(DataProvider.LOUXOR,"SP500");
	Symbol NASDAQ = new Symbol(DataProvider.LOUXOR,"NASDAQ");
	Symbol NIKKEI225 = new Symbol(DataProvider.LOUXOR,"NIKKEI225");
	Symbol CRB = new Symbol(DataProvider.LOUXOR,"CRB");
	Symbol CAC40 = new Symbol(DataProvider.LOUXOR,"CAC40");
	
	// bonds
	Symbol GERMANBUND = new Symbol(DataProvider.LOUXOR,"GERMANBUND");
	Symbol TNOTES = new Symbol(DataProvider.LOUXOR,"TNOTES");
	
	// commodity
	Symbol CRUDEOIL = new Symbol(DataProvider.LOUXOR,"CRUDEOIL");
	Symbol NATURALGAS = new Symbol(DataProvider.LOUXOR,"NATURALGAS");
	Symbol COFFEE = new Symbol(DataProvider.LOUXOR,"COFFEE");
	Symbol AMGEN = new Symbol(DataProvider.LOUXOR,"AMGEN");
	Symbol MERCK = new Symbol(DataProvider.LOUXOR,"MERCK");
	Symbol SOYBEANS = new Symbol(DataProvider.LOUXOR,"SOYBEANS");
	Symbol LUMBER = new Symbol(DataProvider.LOUXOR,"LUMBER");
	Symbol GOLD = new Symbol(DataProvider.LOUXOR,"GOLD");
	Symbol SILVER = new Symbol(DataProvider.LOUXOR,"SILVER");
	Symbol OIL = new Symbol(DataProvider.LOUXOR,"OIL");
	Symbol GAS = new Symbol(DataProvider.LOUXOR,"GAS");
	Symbol COPPER = new Symbol(DataProvider.LOUXOR,"COPPER");

	// stocks
	Symbol MSFT = new Symbol(DataProvider.LOUXOR,"US5949181045");
	Symbol GOOG = new Symbol(DataProvider.LOUXOR,"US38259P5089");
	Symbol ALCATEL= new Symbol(DataProvider.LOUXOR,"FR0000130007");
	
	
}
