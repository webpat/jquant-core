package org.jquant;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.jquant.core.Candle;
import org.jquant.core.Candle.CandlePeriod;
import org.jquant.core.MICMarketPlace;
import org.jquant.data.ProviderAdapterFactory;
import org.jquant.data.Symbols;
import org.jquant.data.reader.ICandleReader;
import org.jquant.exception.MarketDataReaderException;
import org.jquant.instrument.Equity;
import org.jquant.manager.MarketManager;
import org.jquant.serie.CandleSerie;
import org.jquant.serie.TimeSerie.SerieFrequency;
import org.jquant.time.calendar.CalendarFactory;
import org.jquant.time.calendar.IReportingDayCalendar;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.easymock.EasyMockUnitils;
import org.unitils.easymock.annotation.Mock;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.spring.annotation.SpringApplicationContext;


@RunWith(UnitilsJUnit4TestClassRunner.class)
@SpringApplicationContext(value="jquant-test-config.xml")
public class MarketManagerTest {

	Logger logger  = Logger.getLogger(MarketManagerTest.class);
	
	@Mock
	private ICandleReader candleReader;
	
	@Mock
	@InjectInto(property="readerFactory")
	private ProviderAdapterFactory readerFactory;
	
	@TestedObject
	private MarketManager marketMgr;

	private List<Candle> candleList;

	private CandleSerie candleSerie;
	
	@Before
	public void setUp() throws Exception {
		candleList = new ArrayList<Candle>();
		IReportingDayCalendar cal = CalendarFactory.getDailyBrowser(new DateTime(2010, DateTimeConstants.JANUARY, 1, 0, 0, 0, 0), new DateTime(2010, DateTimeConstants.DECEMBER, 31, 0, 0, 0, 0));
		for (DateTime date : cal){
			Candle c = new Candle(date,CandlePeriod.DAILY,Math.random(),Math.random(),Math.random(),Math.random(),Math.random());
			candleList.add(c);
		}
		candleSerie = new CandleSerie(candleList);
	}
	
	@Test
	public void testReadCandle() throws MarketDataReaderException{
		
		EasyMock.expect(readerFactory.getCandleReader(null)).andReturn(candleReader);
		EasyMock.expect(candleReader.fetchAllCandle(Symbols.ALCATEL.getId())).andReturn(candleSerie);
		EasyMockUnitils.replay();
		
		Equity alcatel = new Equity(Symbols.ALCATEL, MICMarketPlace.XFRA);
		
		marketMgr.addInstrument(alcatel, SerieFrequency.DAILY);
		
		
		logger.debug("On est ici");
		
	}

}
