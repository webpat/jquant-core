package org.jquant.data;

import org.joda.time.DateTime;
import org.jquant.core.IInstrument;
import org.jquant.core.Quote;
import org.jquant.core.QuoteSerie;
import org.jquant.exception.DAOException;


public interface IQuoteDAO {

	/**
	 * Lit une série de quotations 
	 * @param instrument
	 * @return une {@link QuoteSerie}
	 * @throws DAOException
	 */
	public QuoteSerie fetchAllQuote(IInstrument instrument) throws DAOException;
	
	/**
	 * Lit une unique quotations 
	 * @param instrument
	 * @param date
	 * @return une {@link Quote}
	 * @throws DAOException
	 */
	public Quote fetchQuote(IInstrument instrument,DateTime date) throws DAOException;
	
	
}
