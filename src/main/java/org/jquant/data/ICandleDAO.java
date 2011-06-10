package org.jquant.data;

import java.util.List;

import org.joda.time.DateTime;
import org.jquant.core.Candle;
import org.jquant.exception.DAOException;


public interface ICandleDAO {

	/**
	 * 
	 * @param instrumentId
	 * @return
	 * @throws DAOException
	 */
	public List<Candle> fetchCandle(String instrumentId) throws DAOException;
	
	
	/**
	 * 
	 * @param instrumentId
	 * @param start
	 * @param end
	 * @return une {@link List} de {@link Candle}
	 * @throws DAOException
	 */
	public List<Candle> fetchCandle(String instrumentId,DateTime start, DateTime end) throws DAOException;
	
	/**
	 * 
	 * @param instrumentId
	 * @param date
	 * @return one {@link Candle}
	 * @throws DAOException
	 */
	public Candle fetchCandle(String instrumentId,DateTime date) throws DAOException;
	
	
}
