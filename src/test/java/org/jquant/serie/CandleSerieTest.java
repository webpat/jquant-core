package org.jquant.serie;

import java.util.Iterator;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.jquant.serie.Candle.CandleData;
import org.jquant.time.calendar.CalendarFactory;
import org.jquant.time.calendar.IDateTimeCalendar;
import org.jquant.time.calendar.Periods;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:jquant-test-config.xml"})
public class CandleSerieTest {

	
	private IDateTimeCalendar cal;

	private CandleSerie cs;
	
	@Before
	public void setup(){
		
		/*
		 * Build a CandleSerie with 366 Candles
		 */
		
		cal = CalendarFactory.getDailyBrowser(new DateTime("2000-01-01"), new DateTime("2000-12-31"));

		cs = new CandleSerie();
		
		double open = 0.0;
		for (DateTime dt : cal){
			double close = Math.random()*10.0;
			double high = close*1.1;
			double low = close*0.9;
			
			cs.addValue(new Candle(dt,Periods.ONE_DAY,open,high,low,close,Math.random()*1000000));
			open = close;
		}
		
		
	}
	
	
	@Test
	public void testGetData() {
		double[] opens = cs.getData(CandleData.OPEN);
		for (int i = 0; i < opens.length; i++) {
			Assert.assertEquals(cs.get(i).getOpen(), opens[i]);
		}
	}

	@Test
	public void testGetDoubleSerie() {
		DoubleSerie closes = cs.getDoubleSerie(CandleData.CLOSE);
		for(TimeValue tv : closes){
			Assert.assertEquals(cs.getValue(tv.getDate()).getClose(),tv.getValue());
		}
	}

	@Test
	public void testReverseIterator() {
		Iterator<Candle> it = cs.reverseIterator();
		/*
		 * Fast forward calendar 
		 */
		cal.reset();
		while (cal.hasNext()){
			cal.next();
		}
		it.next();
		while (it.hasNext()){
			Candle c = it.next();
			Assert.assertEquals(cal.previous(), c.getDate());
		}
			
		
	}

	@Test
	public void testToArray() {
		Candle[] array = cs.toArray();
		for (int i = 0; i < array.length; i++) {
			Assert.assertEquals(cs.get(i), array[i]);
		}
	}

	@Test
	public void testContainsDate() {
		Assert.assertTrue(cs.containsDate(cal.getStartDay()));
		Assert.assertTrue(cs.containsDate(new DateTime("2000-09-29")));
		Assert.assertTrue(cs.containsDate(cal.getEndDay()));
		Assert.assertFalse(cs.containsDate(new DateTime("2012-01-17")));
	}

	@Test
	public void testGetFirstDate() {
		Assert.assertEquals(cs.getFirstDate(),cal.getStartDay());
	}

	@Test
	public void testGetLastDate() {
		Assert.assertEquals(cs.getLastDate(),cal.getEndDay());
	}

	@Test
	public void testGetLast() {
		Assert.assertEquals(cs.getLast(),cs.getValue(cal.getEndDay()));
	}

}
