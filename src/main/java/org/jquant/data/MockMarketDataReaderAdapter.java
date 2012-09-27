package org.jquant.data;

import org.joda.time.DateTime;
import org.jquant.instrument.GenericFuture;
import org.jquant.math.ItoProcess;
import org.jquant.model.InstrumentId;
import org.jquant.serie.Candle;
import org.jquant.serie.CandleSerie;
import org.jquant.serie.QuoteSerie;
import org.jquant.time.calendar.CalendarFactory;
import org.jquant.time.calendar.IDateTimeCalendar;
import org.jquant.time.calendar.Periods;


/**
 * 
 * @author patrick.merheb
 *
 */
//@Component
public class MockMarketDataReaderAdapter implements IMarketDataProviderAdapter {

	@Override
	public boolean supports(Object reader) {
		return true; //Always returns true 
	}

	@Override
	public CandleSerie readCandleSerie(InstrumentId symbol, DateTime start, DateTime end, Object reader) {
		
		CandleSerie cs = new CandleSerie();
		IDateTimeCalendar calendar = CalendarFactory.getDailyTradingDayBrowser(start, end, null);
		ItoProcess i = new ItoProcess();
		double baseValue = 65.0;
		for (DateTime dt : calendar){
			double close = baseValue + i.getValue(0.10, 0.20, 1/252f, baseValue);
			double open = baseValue;
			double high = close + i.getSd();
			double low = close - i.getSd();
			
			cs.addValue(new Candle(dt, Periods.ONE_DAY, open, high, low, close, 10000));
			
			baseValue = close;
		}
		
		return cs;
	}

	@Override
	public QuoteSerie readQuoteSerie(InstrumentId symbol, DateTime start, DateTime end, Object reader) {
		return null;
	}

	@Override
	public CandleSerie readCandleSerie(InstrumentId symbol, Object reader) {
		return null;
	}

	@Override
	public QuoteSerie readQuoteSerie(InstrumentId symbol, Object reader) {
		return null;
	}

	@Override
	public GenericFuture readGenericFuture(InstrumentId future, DateTime start, DateTime end, Object reader) {
		// TODO Auto-generated method stub
		return null;
	}

}
