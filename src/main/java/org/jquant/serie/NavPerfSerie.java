package org.jquant.serie;

import org.jquant.core.NavPerf;

public class NavPerfSerie extends TimeSerie<NavPerf> {

	@Override
	public TimeSerie<NavPerf> clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<NavPerf> getChildClass() {
		// TODO Auto-generated method stub
		return NavPerf.class;
	}

}
