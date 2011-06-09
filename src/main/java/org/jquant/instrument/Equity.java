package org.jquant.instrument;

import java.util.List;

import org.jquant.core.BaseInstrument;
import org.jquant.core.CashDividend;
import org.jquant.core.MICMarketPlace;
import org.jquant.core.Symbol;


/**
 ** <b>Description :</b> The Equity(Stock) Security Type  
 * <br>
 * <b>History:</b>
 * <br>
 * @see BaseInstrument TermStructure CandleSerie   
 * @author merhebp
 *
 */
public class Equity extends BaseInstrument{

	protected List<CashDividend> cashDividends;

	
	public Equity(Symbol symbol, MICMarketPlace market) {
		super(symbol, market);
		
	}
	


	public void setCashDividends(List<CashDividend> cashDividends) {
		this.cashDividends = cashDividends;
	}


	public List<CashDividend> getCashDividends() {
		return cashDividends;
	}	
	
	
}
