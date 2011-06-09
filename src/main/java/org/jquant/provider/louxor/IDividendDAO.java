package org.jquant.provider.louxor;

import java.util.List;

import org.joda.time.DateTime;
import org.jquant.core.CashDividend;
import org.jquant.core.DividendYield;
import org.jquant.exception.DAOException;
import org.jquant.model.MarketDataProvider;


public interface IDividendDAO {
	
	public Double fetchDividendYield(String symbol, String micCode, 
			MarketDataProvider provider, DateTime expirationDate, DateTime onDate)
			throws DAOException;

	public List<DividendYield> fetchDividendYields(String symbol, String micCode, 
			MarketDataProvider provider, DateTime onDate)
			throws DAOException;

	public List<CashDividend> fetchCashDividends(String symbol, String micCode, 
			MarketDataProvider provider) throws DAOException;
}
