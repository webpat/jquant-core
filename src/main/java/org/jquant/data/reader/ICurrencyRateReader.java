package org.jquant.data.reader;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.jquant.exception.MarketDataReaderException;
import org.jquant.instrument.rate.InterestRateIndex.RateType;
import org.jquant.model.Currency;
import org.jquant.model.Rate;
import org.jquant.serie.TermStructure;


public interface ICurrencyRateReader {

	
	public TermStructure fetchAllRate(Currency currency, Period term,RateType type) throws MarketDataReaderException;
	
	public Rate fetchRate(Currency currency, DateTime onDate, Period term, RateType type) throws MarketDataReaderException;
}
