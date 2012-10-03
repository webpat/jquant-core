package org.jquant.serie;

import java.util.Iterator;

import junit.framework.Assert;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.joda.time.DateTime;
import org.jquant.time.calendar.CalendarFactory;
import org.jquant.time.calendar.IDateTimeCalendar;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:jquant-test-config.xml"})
public class DoubleSerieTest {

	/*
	 * Object under test 
	 */
	DoubleSerie ds = null;
	
	/*
	 * Test calendar
	 */
	private IDateTimeCalendar cal;
	
	DescriptiveStatistics stats;
	
	
	
	@Before
	public void setup(){
		
		/*
		 * Build a DoubleSerie with 366 TimeValues
		 */
		cal = CalendarFactory.getDailyBrowser(new DateTime("2000-01-01"), new DateTime("2000-12-31"));
		
		stats = new DescriptiveStatistics();

		ds = new DoubleSerie();
		for (DateTime dt : cal){
			double value = Math.random()*10.0;
			ds.addValue(new TimeValue(dt, value));
			stats.addValue(value);
		}
		
		
	}
	
	
	@Test
	public void testGetReturns() {
		DoubleSerie returns = ds.getReturns();
		Assert.assertEquals(returns.size(), ds.size()-1);
		Iterator<DateTime> it = cal.iterator();
		DateTime dt1 = it.next();
		while (it.hasNext()){
			DateTime dt2 = it.next();
			double rendement = (ds.getDouble(dt2)-ds.getDouble(dt1))/ds.getDouble(dt1);
			Assert.assertEquals(rendement, returns.getDouble(dt1));
			dt1 = dt2;
		}
		
	}

	@Test
	public void testSum() {
		DoubleSerie sum = ds.sum(ds);
		for (TimeValue tv : sum){
			Assert.assertEquals(tv.getValue(), ds.getDouble(tv.getDate())*2.0);
		}
	}

	@Test
	public void testProduct() {
		DoubleSerie product = ds.product(3.0);
		for (TimeValue tv : product){
			Assert.assertEquals(tv.getValue(), ds.getDouble(tv.getDate())*3.0);
		}
	}

	@Test
	public void testMean() {
		Assert.assertEquals(stats.getMean(), ds.mean());
	}

	@Test
	public void testVariance() {
		Assert.assertEquals(stats.getVariance(), ds.variance());
	}

}
