package org.jquant.provider.louxor;

import java.util.List;

import org.joda.time.DateTime;
import org.jquant.core.Rate;
import org.jquant.core.TermStructure;
import org.jquant.exception.DAOException;
import org.jquant.instrument.rate.InterestRateIndex.RateType;
import org.jquant.model.Currency;
import org.jquant.time.TimeFrame;


public interface ICurrencyRateDAO {

	public List<Rate> fetchDepositAndSwapRates(Currency currency, DateTime onDate) throws DAOException;
	
	public List<Rate> fetchDepositRates(Currency currency, DateTime onDate) throws DAOException;
	
	public TermStructure fetchAllRate(Currency currency, TimeFrame pillar,RateType type) throws DAOException;
	
	public Rate fetchRate(Currency currency, DateTime onDate, TimeFrame pillar) throws DAOException;
}
