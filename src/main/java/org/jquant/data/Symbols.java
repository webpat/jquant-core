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
	Symbol EURUSD = new Symbol(MarketDataProvider.BLOOMBERG,"EURUSD");
	Symbol GBPUSD = new Symbol(MarketDataProvider.BLOOMBERG,"GBPUSD");
	Symbol USDJPY = new Symbol(MarketDataProvider.BLOOMBERG,"USDJPY");
	Symbol USDCHF = new Symbol(MarketDataProvider.BLOOMBERG,"USDCHF");
	Symbol USDCAD = new Symbol(MarketDataProvider.BLOOMBERG,"USDCAD");
	Symbol AUDUSD = new Symbol(MarketDataProvider.BLOOMBERG,"AUDUSD");
	Symbol EURJPY = new Symbol(MarketDataProvider.BLOOMBERG,"EURJPY");
	Symbol EURCHF = new Symbol(MarketDataProvider.BLOOMBERG,"EURCHF");
	Symbol EURGBP = new Symbol(MarketDataProvider.BLOOMBERG,"EURGBP");
	Symbol EURCAD = new Symbol(MarketDataProvider.BLOOMBERG,"EURCAD");
	Symbol GBPJPY = new Symbol(MarketDataProvider.BLOOMBERG,"GBPJPY");
	Symbol GBPCHF = new Symbol(MarketDataProvider.BLOOMBERG,"GBPCHF");
	Symbol CHFJPY = new Symbol(MarketDataProvider.BLOOMBERG,"CHFJPY");
	Symbol NZDUSD = new Symbol(MarketDataProvider.BLOOMBERG,"NZDUSD");
	Symbol USDZAR = new Symbol(MarketDataProvider.BLOOMBERG,"USDZAR");
	Symbol USDNOK = new Symbol(MarketDataProvider.BLOOMBERG,"USDNOK");
	Symbol EURNOK = new Symbol(MarketDataProvider.BLOOMBERG,"EURNOK");
	Symbol USDSEK = new Symbol(MarketDataProvider.BLOOMBERG,"USDSEK");
	Symbol EURSEK = new Symbol(MarketDataProvider.BLOOMBERG,"EURSEK");
	Symbol USDMXN = new Symbol(MarketDataProvider.BLOOMBERG,"USDMXN");
	Symbol USDINR = new Symbol(MarketDataProvider.BLOOMBERG,"USDINR");
	Symbol GBPINR = new Symbol(MarketDataProvider.BLOOMBERG,"GBPINR");
	Symbol USDRMB = new Symbol(MarketDataProvider.BLOOMBERG,"USDRMB");
	
	// index
	Symbol DAX = new Symbol(MarketDataProvider.BLOOMBERG,"DAX");
	Symbol SP500 = new Symbol(MarketDataProvider.BLOOMBERG,"SP500");
	Symbol NASDAQ = new Symbol(MarketDataProvider.BLOOMBERG,"NASDAQ");
	Symbol NIKKEI225 = new Symbol(MarketDataProvider.BLOOMBERG,"NIKKEI225");
	Symbol CRB = new Symbol(MarketDataProvider.BLOOMBERG,"CRB");
	Symbol CAC40 = new Symbol(MarketDataProvider.BLOOMBERG,"CAC40");
	
	// bonds
	Symbol GERMANBUND = new Symbol(MarketDataProvider.BLOOMBERG,"GERMANBUND");
	Symbol TNOTES = new Symbol(MarketDataProvider.BLOOMBERG,"TNOTES");
	
	// commodity
	Symbol CRUDEOIL = new Symbol(MarketDataProvider.BLOOMBERG,"CRUDEOIL");
	Symbol NATURALGAS = new Symbol(MarketDataProvider.BLOOMBERG,"NATURALGAS");
	Symbol COFFEE = new Symbol(MarketDataProvider.BLOOMBERG,"COFFEE");
	Symbol AMGEN = new Symbol(MarketDataProvider.BLOOMBERG,"AMGEN");
	Symbol MERCK = new Symbol(MarketDataProvider.BLOOMBERG,"MERCK");
	Symbol SOYBEANS = new Symbol(MarketDataProvider.BLOOMBERG,"SOYBEANS");
	Symbol LUMBER = new Symbol(MarketDataProvider.BLOOMBERG,"LUMBER");
	Symbol GOLD = new Symbol(MarketDataProvider.BLOOMBERG,"GOLD");
	Symbol SILVER = new Symbol(MarketDataProvider.BLOOMBERG,"SILVER");
	Symbol OIL = new Symbol(MarketDataProvider.BLOOMBERG,"OIL");
	Symbol GAS = new Symbol(MarketDataProvider.BLOOMBERG,"GAS");
	Symbol COPPER = new Symbol(MarketDataProvider.BLOOMBERG,"COPPER");

	// stocks
	Symbol MSFT = new Symbol(MarketDataProvider.BLOOMBERG,"US5949181045");
	Symbol GOOG = new Symbol(MarketDataProvider.BLOOMBERG,"US38259P5089");
	Symbol ALCATEL= new Symbol(MarketDataProvider.BLOOMBERG,"FR0000130007");
	
	
}
