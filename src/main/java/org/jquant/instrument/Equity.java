package org.jquant.instrument;

import java.util.List;

import org.jquant.model.CashDividend;
import org.jquant.model.InstrumentId;
import org.jquant.model.InstrumentType;


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

	
	public Equity(InstrumentId symbol) {
		super(symbol);
		assert(InstrumentType.EQUITY.equals(symbol.getType()));
		
	}
	


	public void setCashDividends(List<CashDividend> cashDividends) {
		this.cashDividends = cashDividends;
	}


	public List<CashDividend> getCashDividends() {
		return cashDividends;
	}	
	
	
}
