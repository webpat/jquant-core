package org.jquant.data;

import java.util.List;

import org.joda.time.DateTime;
import org.jquant.core.IInstrument;
import org.jquant.core.OptionQuote;
import org.jquant.exception.DAOException;
import org.jquant.model.ExerciseType;
import org.jquant.model.OptionType;


/**
 * Interface de lecture de quotations marché d'options 
 * @author patrick.merheb
 *
 */
public interface IOptionQuoteDAO {

	/**
	 * 
	 * @param underlying Le sous-jacent
	 * @param expirationDate La date d'expiration (t+T)
	 * @param onDate La date considérée (t) 
	 * @return une liste de quotation qui correspondent 
	 * @throws DAOException
	 */
	public List<OptionQuote> fetchOptionPrices(IInstrument underlying, DateTime expirationDate, DateTime onDate) throws DAOException;

	/**
	 * Toutes les options quôtées à la date considérée  
	 * @param underlying Un Sous-Jacent
	 * @param onDate La date Considérée 
	 * @return une liste de quotations d'options
	 * @throws DAOException
	 */
	public List<OptionQuote> fetchOptionPrices(IInstrument underlying, DateTime onDate) throws DAOException;

	/**
	 * 
	 * @param underlying
	 * @param expirationDate
	 * @param onDate
	 * @param exerciseType
	 * @return une liste de quotations d'options
	 * @throws DAOException
	 */
	public List<OptionQuote> fetchOptionPrices(IInstrument underlying, DateTime expirationDate, DateTime onDate, ExerciseType exerciseType) throws DAOException;

	/**
	 * 
	 * @param underlying
	 * @param onDate
	 * @param exerciseType
	 * @param optionType
	 * @return une liste de quotations d'options
	 * @throws DAOException
	 */
	public List<OptionQuote> fetchOptionPrices(IInstrument underlying, DateTime onDate, ExerciseType exerciseType, OptionType optionType) throws DAOException;

}
