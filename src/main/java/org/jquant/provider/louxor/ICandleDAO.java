package org.jquant.provider.louxor;

import java.util.List;

import org.joda.time.DateTime;
import org.jquant.core.Candle;
import org.jquant.core.IInstrument;
import org.jquant.exception.DAOException;


public interface ICandleDAO {

	public List<Candle> fetchAllCandle(IInstrument instrument) throws DAOException;

	public List<Candle> fetchAuditedCandle(IInstrument instrument) throws DAOException;

	public Candle fetchCandle(IInstrument instrument,DateTime date) throws DAOException;
	
	
}
