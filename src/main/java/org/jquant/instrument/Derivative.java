package org.jquant.instrument;

import org.jquant.model.InstrumentId;

public class Derivative extends BaseInstrument {

	private InstrumentId underlying;
	
	public Derivative(InstrumentId symbol) {
		super(symbol);
	}

	public InstrumentId getUnderlying() {
		return underlying;
	}

	public void setUnderlying(InstrumentId underlying) {
		this.underlying = underlying;
	}

	
}
