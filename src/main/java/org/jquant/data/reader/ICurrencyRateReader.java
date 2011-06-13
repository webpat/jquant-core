package org.jquant.data.reader;

import org.joda.time.DateTime;
import org.jquant.core.Rate;
import org.jquant.core.TermStructure;
import org.jquant.exception.MarketDataReaderException;
import org.jquant.instrument.rate.InterestRateIndex.RateType;
import org.jquant.model.Currency;
import org.jquant.time.TimeFrame;


public interface ICurrencyRateReader {

	
	public TermStructure fetchAllRate(Currency currency, TimeFrame pillar,RateType type) throws MarketDataReaderException;
	
	public Rate fetchRate(Currency currency, DateTime onDate, TimeFrame pillar, RateType type) throws MarketDataReaderException;
}
