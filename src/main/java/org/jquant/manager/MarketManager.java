package org.jquant.manager;

import org.joda.time.DateTime;
import org.jquant.core.BaseInstrument;
import org.jquant.core.IInstrument;
import org.jquant.core.Symbol;
import org.jquant.data.ProviderAdapterFactory;
import org.jquant.data.reader.ICandleReader;
import org.jquant.data.reader.IQuoteReader;
import org.jquant.exception.MarketDataReaderException;
import org.jquant.serie.CandleSerie;
import org.jquant.serie.QuoteSerie;
import org.jquant.serie.TimeSerie.SerieFrequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * InstrumentRepository for simulation and backtesting
 * @author pat
 *
 */
@Service
public class MarketManager {

	@Autowired
	private InstrumentManager instrumentMgr;
	
	@Autowired
	private ProviderAdapterFactory readerFactory;
	
	/**
	 *  Add an Instrument in the simulation scope
	 *  <ul>
	 *  <li>Load all Historical Data</li>
	 *  <li>Subscribe for new Market Data Events concerning this instrument</li>
	 *  </ul>
	 * @param instrument add the instrument to the simulation universe
	 * @param frequency resolution switch DAILY, WEEKLY, INTRADAY
	 * @throws MarketDataReaderException 
	 */
	public void addInstrument(BaseInstrument instrument,SerieFrequency frequency) throws MarketDataReaderException{
		// LOOKUP Instrument
		Symbol symbol = instrument.getSymbol();
		
		switch (frequency){
		case DAILY: 
			// Get Appropriate Candle Reader
			ICandleReader reader = readerFactory.getCandleReader(symbol.getProvider());
			// Read All historical market data 
			CandleSerie candles = reader.fetchAllCandle(symbol.getId());
			instrument.setCandles(candles);
			
			// TODO: Manage Implied Volatility
			
			// TODO : Subscribe for New CandleEvents
			
			break;
			
		case QUOTES:
			// Get Appropriate (data-provider dependant)  Quote Reader
			IQuoteReader quoteReader = readerFactory.getQuoteReader(symbol.getProvider());
			// Read All historical market data 
			QuoteSerie serie = quoteReader.fetchAllQuote(symbol.getId());
			instrument.setQuotes(serie);
			
			// TODO : Subscribe for new QuoteEvents
			
			
			break;
			
		case TRADE: 
			throw new NotImplementedException();
		
		}
		
		
	}
	
	public void addInstrument(BaseInstrument instrument,DateTime debut, DateTime fin,SerieFrequency frequency) throws MarketDataReaderException{
		
		// LOOKUP Instrument
		Symbol symbol = instrument.getSymbol();
		
		switch (frequency){
		case DAILY: 
			// Get Appropriate Candle Reader
			ICandleReader reader = readerFactory.getCandleReader(symbol.getProvider());
			// Read All historical market data 
			CandleSerie candles = reader.fetchAllCandle(symbol.getId(),debut,fin);
			instrument.setCandles(candles);
			
			// TODO: Manage Daily Implied Volatility (CALL PUT MID)
			
			// TODO : Subscribe for New CandleEvents
			
			break;
			
		case QUOTES:
			// Get Appropriate (data-provider dependant)  Quote Reader
			IQuoteReader quoteReader = readerFactory.getQuoteReader(symbol.getProvider());
			// Read All historical market data 
			QuoteSerie serie = quoteReader.fetchAllQuote(symbol.getId(),debut,fin);
			instrument.setQuotes(serie);
			
			// TODO : Subscribe for new QuoteEvents
			
			
			break;
			
		case TRADE: 
			throw new NotImplementedException();
		
		}
		
	}
}
