package com.sgam.jquant.instrument;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class TestForexInstrument {


	@Test
	public void testGetForwardPrice() {
		
//		Symbol symbol = new Symbol("AUDUSD");
//		Forex forex = new Forex(symbol,MICMarketPlace.NO_MIC);
//		forex.setExchangedCurrency(Currency.AUD);
//		forex.setPriceCurrency(Currency.USD);
//		
//		TickerQuoteDAO dao = JQuantApplicationContext.getTimeSerieDAO();
//		List<Candle> list = dao.selectAllCandle(symbol.toString(),MICMarketPlace.NO_MIC.getCode(),MarketDataProvider.BLOOMBERG); //ALCATEL-LUCENT
//		
//		forex.setPrices(new CandleSerie(list));
//		
//		DateTime startDate = new DateTime(2008,2,26,0,0,0, 0);
//		DateTime endDate = new DateTime(2008,6,16,0,0,0, 0);
//		double forwardPrice = forex.getForwardPrice(startDate, endDate);
//		
//		System.out.println("forwardPrice : "+forwardPrice*10000);
//		Assert.assertEquals(9196, (int)(forwardPrice*10000));
	}

}
