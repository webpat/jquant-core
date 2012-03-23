package org.jquant.instrument;

import java.util.ArrayList;
import java.util.List;

import org.jquant.model.IInstrument;
import org.jquant.model.InstrumentId;


/**
 * an index is an imaginary portfolio of securities representing a particular market or a portion of it.
 * <a href="http://www.investopedia.com/terms/i/index.asp#ixzz1jgOu8P4Q">Read more: </a>
 * <p>
 * Because, technically, you can't actually invest in an index, index mutual funds and exchange-traded funds (based on indexes) allow investors to invest in securities representing broad market segments and/or the total market.
 *
 *  @author Patrick Merheb
 */
public class Index extends BaseInstrument {

	
	List<IInstrument> composition  =  new ArrayList<IInstrument>();
	
	public Index(InstrumentId symbol) {
		super(symbol);
	}

	public List<IInstrument> getComposition() {
		return composition;
	}

	public void setComposition(List<IInstrument> composition) {
		this.composition = composition;
	}
	

	
}
