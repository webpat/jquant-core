package org.jquant.order;

import org.joda.time.DateTime;
import org.jquant.data.Instruments;
import org.jquant.model.Currency;
import org.jquant.model.InstrumentId;
import org.jquant.order.Order.OrderSide;
import org.jquant.portfolio.Portfolio;
import org.jquant.serie.Candle;
import org.jquant.time.calendar.Periods;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * TODO : test GAP on stop and limit Orders
 * TODO : test trailing Orders
 * @author patrick.merheb
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:jquant-test-config.xml"})
public class OrderManagerTest {

	@Autowired
	private IOrderManager orderManager;
	
	private Portfolio ptf;
	private InstrumentId ibm, google;
	
	@Before
	public void setup(){
		ptf = new Portfolio("Test Ptf", Currency.USD);
		ptf.addCash(1000);
		ibm = Instruments.IBM;
		google = Instruments.GOOG;
		orderManager.setPortfolio(ptf);
		
	}
	
	@Test
	public void testMarketOrder() {
		Order mktOrder = new MarketOrder(OrderSide.BUY, google, 1, "TU Market Order");
		
		// Send a Market Order for Google 
		orderManager.sendOrder(mktOrder);
		// Simulate the reception of one candle about google 
		orderManager.onCandle(google, new Candle(new DateTime(),Periods.ONE_DAY, 100,110,90,100,1000000));
		
		Assert.assertEquals(1.0,ptf.getPosition(Instruments.GOOG),0.0);
		Assert.assertEquals(900,ptf.getCash(),0.0);
		
		
	}
	
	
	@Test
	public void testBuyLimitOrder() {
		
		// Send a BUY Limit Order for Google 
		Order buyLimitOrder = new LimitOrder(OrderSide.BUY, google, 1,90, "TU Buy Limit Order @90 ");
		orderManager.sendOrder(buyLimitOrder);
		
		// 1st Candle limit is not hit 
		orderManager.onCandle(google, new Candle(new DateTime(),Periods.ONE_DAY, 100,110,95,100,1000000));
		Assert.assertEquals(0.0,ptf.getPosition(Instruments.GOOG),0.0);
		// 2nd Candle limit is not hit 
		orderManager.onCandle(google, new Candle(new DateTime(),Periods.ONE_DAY, 105,120,95,115,1000000));
		Assert.assertEquals(0.0,ptf.getPosition(Instruments.GOOG),0.0);
		// 3rd Candle limit is hit 
		orderManager.onCandle(google, new Candle(new DateTime(),Periods.ONE_DAY, 115,120,85,91,1000000));
		Assert.assertEquals(1.0,ptf.getPosition(Instruments.GOOG),0.0);
		Assert.assertEquals(910,ptf.getCash(),0.0);
		
		
	}
	
	@Test
	public void testSellLimitOrder() {
		
		// Send a Sell Limit Order for Google 
		Order sellLimitOrder = new LimitOrder(OrderSide.SELL, google, 1,110, "TU Sell Limit Order @110 ");
		orderManager.sendOrder(sellLimitOrder);
		
		// 1st Candle limit is not hit 
		orderManager.onCandle(google, new Candle(new DateTime(),Periods.ONE_DAY, 100,105,95,100,1000000));
		Assert.assertEquals(0.0,ptf.getPosition(Instruments.GOOG),0.0);
		// 2nd Candle limit is hit 
		orderManager.onCandle(google, new Candle(new DateTime(),Periods.ONE_DAY, 105,120,95,115,1000000));
		Assert.assertEquals(-1.0,ptf.getPosition(Instruments.GOOG),0.0);
		Assert.assertEquals(1110,ptf.getCash(),0.0);
		
		
	}
	
	@Test
	public void testStopLossOrder() {
		Order stopLossOrder = new StopOrder(OrderSide.SELL, google, 1,85, "TU Stop Loss Order @85");
		
		// Send a BUY Limit Order for Google 
		orderManager.sendOrder(stopLossOrder);
		
		// 1st Candle stop is not hit 
		orderManager.onCandle(google, new Candle(new DateTime(),Periods.ONE_DAY, 100,110,95,100,1000000));
		Assert.assertEquals(0.0,ptf.getPosition(Instruments.GOOG),0.0);
		// 2nd Candle stop is not hit 
		orderManager.onCandle(google, new Candle(new DateTime(),Periods.ONE_DAY, 105,120,95,115,1000000));
		Assert.assertEquals(0.0,ptf.getPosition(Instruments.GOOG),0.0);
		// 3rd Candle stop is hit 
		orderManager.onCandle(google, new Candle(new DateTime(),Periods.ONE_DAY, 115,120,84,91,1000000));
		Assert.assertEquals(-1.0,ptf.getPosition(Instruments.GOOG),0.0);
		Assert.assertEquals(1085,ptf.getCash(),0.0);
		
		
	}
	
	@Test
	public void testSellTrailingStopOrder() {
		
		Order mktOrder = new MarketOrder(OrderSide.BUY, google, 1, "TU Market Order");
		Order trailingStopOrder = new TrailingStopOrder(OrderSide.SELL, google,0.2, 1, "Trailing stop Order -20%");
		
		//Send a market Order for google
		orderManager.sendOrder(mktOrder);
		// Send a Sell Trailing Stop Order for Google 
		orderManager.sendOrder(trailingStopOrder);
		
		// 1st Candle engage trailing stop 
		orderManager.onCandle(google, new Candle(new DateTime(),Periods.ONE_DAY, 100,110,95,100,1000000));
		Assert.assertEquals(1.0,ptf.getPosition(Instruments.GOOG),0.0);
		// 2nd Candle stop is not hit
		orderManager.onCandle(google, new Candle(new DateTime(),Periods.ONE_DAY, 105,120,95,115,1000000));
		Assert.assertEquals(1.0,ptf.getPosition(Instruments.GOOG),0.0);
		// 3rd Candle stop is hit @92 (115 *0.8 = 92) 
		orderManager.onCandle(google, new Candle(new DateTime(),Periods.ONE_DAY, 115,115,75,80,1000000));
		Assert.assertEquals(0.0,ptf.getPosition(Instruments.GOOG),0.0);
		Assert.assertEquals(992,ptf.getCash(),0.0);
		
	}
	
	
	

}
