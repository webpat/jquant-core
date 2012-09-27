package org.jquant.instrument;


import java.util.concurrent.Future;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.jquant.data.Instruments;
import org.jquant.data.JQuantDataProvider;
import org.jquant.model.Currency;
import org.jquant.model.InstrumentId;
import org.jquant.model.InstrumentType;
import org.jquant.model.MICMarketPlace;
import org.jquant.model.StitchingMethod;
import org.jquant.serie.Candle;
import org.jquant.serie.Candle.CandleData;
import org.jquant.serie.CandleSerie;
import org.jquant.serie.DoubleSerie;
import org.jquant.serie.TimeValue;
import org.jquant.time.calendar.Periods;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:jquant-test-config.xml" })
public class GenericFutureTest {

	private GenericFuture gf;

	@Before
	public void setup() {

	}

	@Test
	public void testReturnAdjusted() {
		
		gf = new GenericFuture(Instruments.COFFEE);

		/*
		 * 1er strip, rendement constant de 10% chaque mois Commence à 1.0 aujourd'hui
		 */

		CandleSerie cs1 = new CandleSerie();

		DateTime dt = new DateTime().toDateMidnight().toDateTime();
		double value = 1.0;
		for (int i = 0; i < 5; i++) {

			cs1.addValue(new Candle(dt, Periods.ONE_MONTH, value, value, value, value, 10000));
			dt = dt.plusMonths(1).toDateMidnight().toDateTime();
			value *= 1.1;
		}

		/*
		 * 2ème strip, rendement constant de 10% chaque mois Commence à 2.0 dans 3 mois
		 */
		CandleSerie cs2 = new CandleSerie();

		dt = new DateTime().plusMonths(3).toDateMidnight().toDateTime();
		value = 2.0;
		for (int i = 0; i < 5; i++) {

			cs2.addValue(new Candle(dt, Periods.ONE_MONTH, value, value, value, value, 10000));
			dt = dt.plusMonths(1).toDateMidnight().toDateTime();;
			value *= 1.1;
		}

		/*
		 * 3ème strip, rendement constant de 10% chaque mois Commence à 3.0 dans 6 mois
		 */
		CandleSerie cs3 = new CandleSerie();

		dt = new DateTime().plusMonths(6).toDateMidnight().toDateTime();
		value = 3.0;
		for (int i = 0; i < 5; i++) {

			cs3.addValue(new Candle(dt, Periods.ONE_MONTH, value, value, value, value, 10000));
			dt = dt.plusMonths(1).toDateMidnight().toDateTime();
			value *= 1.1;
		}

		Future f1 = new Future(new InstrumentId(JQuantDataProvider.LOUXOR, "strip1", InstrumentType.FUTURE, MICMarketPlace.XCBT, Currency.USD));
		f1.setLastDeliveryDate(new DateTime().plusMonths(3).toDateMidnight().toDate());
		Future f2 = new Future(new InstrumentId(JQuantDataProvider.LOUXOR, "strip2", InstrumentType.FUTURE, MICMarketPlace.XCBT, Currency.USD));
		f2.setLastDeliveryDate(new DateTime().plusMonths(6).toDateMidnight().toDate());
		Future f3 = new Future(new InstrumentId(JQuantDataProvider.LOUXOR, "strip3", InstrumentType.FUTURE, MICMarketPlace.XCBT, Currency.USD));
		f3.setLastDeliveryDate(new DateTime().plusMonths(9).toDateMidnight().toDate());
		
		gf.add(f1, cs1);
		gf.add(f2, cs2);
		gf.add(f3, cs3);
		gf.stitch(StitchingMethod.RETURN_ADJUSTED);
		
		/*
		 * Le future générique doit avoir un rendement constant de 10% Il 'mesure' 11 mois Il contient 3 strips
		 */
		Assert.assertEquals(11,gf.getSerie().size());
		DoubleSerie returns = gf.getSerie().getDoubleSerie(CandleData.CLOSE).getReturns();
		
		for (TimeValue tv : returns){
			Assert.assertEquals(0.10d, tv.getValue(),0.01d);
		}
		
	}
	
	@Test
	public void testContinuousAdjusted(){
		gf = new GenericFuture(Instruments.COFFEE);

		/*
		 * 1er strip, rendement constant de 10% chaque mois Commence à 1.0 aujourd'hui
		 */

		CandleSerie cs1 = new CandleSerie();

		DateTime dt = new DateTime().toDateMidnight().toDateTime();
		double value = 1.0;
		for (int i = 0; i < 5; i++) {

			cs1.addValue(new Candle(dt, Periods.ONE_MONTH, value, value, value, value, 10000));
			dt = dt.plusMonths(1).toDateMidnight().toDateTime();
			value *= 1.1;
		}

		/*
		 * 2ème strip, rendement constant de 10% chaque mois Commence à 2.0 dans 3 mois
		 */
		CandleSerie cs2 = new CandleSerie();

		dt = new DateTime().plusMonths(3).toDateMidnight().toDateTime();
		value = 2.0;
		for (int i = 0; i < 5; i++) {

			cs2.addValue(new Candle(dt, Periods.ONE_MONTH, value, value, value, value, 10000));
			dt = dt.plusMonths(1).toDateMidnight().toDateTime();;
			value *= 1.1;
		}

		/*
		 * 3ème strip, rendement constant de 10% chaque mois Commence à 3.0 dans 6 mois
		 */
		CandleSerie cs3 = new CandleSerie();

		dt = new DateTime().plusMonths(6).toDateMidnight().toDateTime();
		value = 3.0;
		for (int i = 0; i < 5; i++) {

			cs3.addValue(new Candle(dt, Periods.ONE_MONTH, value, value, value, value, 10000));
			dt = dt.plusMonths(1).toDateMidnight().toDateTime();
			value *= 1.1;
		}

		Future f1 = new Future(new InstrumentId(JQuantDataProvider.LOUXOR, "strip1", InstrumentType.FUTURE, MICMarketPlace.XCBT, Currency.USD));
		f1.setLastDeliveryDate(new DateTime().plusMonths(3).toDateMidnight().toDate());
		Future f2 = new Future(new InstrumentId(JQuantDataProvider.LOUXOR, "strip2", InstrumentType.FUTURE, MICMarketPlace.XCBT, Currency.USD));
		f2.setLastDeliveryDate(new DateTime().plusMonths(6).toDateMidnight().toDate());
		Future f3 = new Future(new InstrumentId(JQuantDataProvider.LOUXOR, "strip3", InstrumentType.FUTURE, MICMarketPlace.XCBT, Currency.USD));
		f3.setLastDeliveryDate(new DateTime().plusMonths(9).toDateMidnight().toDate());
		
		gf.add(f1, cs1);
		gf.add(f2, cs2);
		gf.add(f3, cs3);
		gf.stitch(StitchingMethod.CONTINUOUS_ADJUSTED);
		
		/**
		 * Le future générique 'mesure' 11 mois 
		 */
		Assert.assertEquals(11,gf.getSerie().size());
		
	}

}
