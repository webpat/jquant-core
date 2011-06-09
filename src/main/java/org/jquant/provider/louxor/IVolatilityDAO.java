package org.jquant.provider.louxor;

import java.util.List;

import org.jquant.core.BaseInstrument;
import org.jquant.core.VolatilityTermStructure;
import org.jquant.exception.DAOException;


/**
 * Lecture de surface de volatilité 
 * Lecture d'une term-structure 
 * @author patrick.merheb
 *
 */
public interface IVolatilityDAO {

	/**
	 * 
	 * @param underlying Le sous-jacent 
	 * @return 
	 * @throws DAOException
	 */
	public List<VolatilityTermStructure> fetchAllVolatilities(BaseInstrument underlying) throws DAOException;
	
}
