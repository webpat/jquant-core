package org.jquant.portfolio;

import org.joda.time.DateTime;
import org.jquant.data.Instruments;
import org.jquant.model.Currency;
import org.jquant.model.InstrumentId;
import org.jquant.portfolio.Trade.TradeSide;
import org.jquant.portfolio.Trade.TradeStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:jquant-test-config.xml"})
public class PortfolioTest {

	
	private Portfolio ptf;
	private InstrumentId ibm, google;

	@Before
	public void setup(){
		ptf = new Portfolio("Test Ptf", Currency.USD,1000);
		ibm = Instruments.IBM;
		google = Instruments.GOOG;
		
	}
	
	/**
	 * Cas Nominal Achat 10 ibm @ 100, Vente 10 ibm @ 110 => P&L = 100 
	 * @throws PortfolioException 
	 */
	@Test
	public void testBuySellNominal() throws PortfolioException {
		
		
		// Achat 10 IBM @ 100 
		Trade buy = new Trade(TradeSide.BUY,ibm, 10, 1000,  new DateTime());
		ptf.addTransaction(buy);
		Assert.assertEquals(0,ptf.getCash(),0.0);
		Assert.assertEquals(TradeStatus.OPEN,buy.getStatus());
		Assert.assertEquals(10,ptf.getPosition(ibm),0.0);
		Assert.assertEquals(0,ptf.getPosition(google),0.0);
		
		// Vente 10 IBM @ 110
		Trade sale = new Trade(TradeSide.SELL,ibm, 10, 1100,  new DateTime());
		ptf.addTransaction(sale);
		
		Assert.assertEquals(1100,ptf.getCash(),0.0);
		Assert.assertEquals(TradeStatus.CLOSED,buy.getStatus());
		Assert.assertEquals(TradeStatus.CLOSED,sale.getStatus());
		Assert.assertEquals(100,sale.getProfitAndLoss(),0.0);
		
		
	}
	
	/**
	 * Cas Allegement sur marché haussier, Achat 10 ibm @ 100, Vente 5 ibm @ 110 => P&L = 50 
	 * @throws PortfolioException 
	 */
	@Test
	public void testPartialSellOnBullMarket() throws PortfolioException {
		
		// Achat 10 IBM @ 100 
		Trade buy = new Trade(TradeSide.BUY,ibm, 10, 1000,  new DateTime());
		ptf.addTransaction(buy);
		Assert.assertEquals(0,ptf.getCash(),0.0);
		Assert.assertEquals(TradeStatus.OPEN,buy.getStatus());
		Assert.assertEquals(10,ptf.getPosition(ibm),0.0);
		Assert.assertEquals(0,ptf.getPosition(google),0.0);
		
		// Vente 5 IBM @ 110
		Trade sale = new Trade(TradeSide.SELL,ibm, 5, 550,  new DateTime());
		ptf.addTransaction(sale);
		
		Assert.assertEquals(550,ptf.getCash(),0.0);
		Assert.assertEquals(TradeStatus.OPEN,buy.getStatus());
		Assert.assertEquals(TradeStatus.CLOSED,sale.getStatus());
		Assert.assertEquals(5,ptf.getPosition(ibm),0.0);
		Assert.assertEquals(50,sale.getProfitAndLoss(),0.0);
		
		
	}
	
	
	/**
	 * Cas Allegement sur marché baissier, Achat 10 ibm @ 100, Vente 5 ibm @ 90 => P&L = -50 
	 * @throws PortfolioException 
	 */
	@Test
	public void testPartialSellOnBearMarket() throws PortfolioException {
		
		// Achat 10 IBM @ 100 
		Trade buy = new Trade(TradeSide.BUY,ibm, 10, 1000,  new DateTime());
		ptf.addTransaction(buy);
		Assert.assertEquals(0,ptf.getCash(),0.0);
		Assert.assertEquals(TradeStatus.OPEN,buy.getStatus());
		Assert.assertEquals(10,ptf.getPosition(ibm),0.0);
		
		// Vente 5 IBM @ 90
		Trade sale = new Trade( TradeSide.SELL,ibm, 5, 450, new DateTime());
		ptf.addTransaction(sale);
		
		Assert.assertEquals(450,ptf.getCash(),0.0);
		Assert.assertEquals(TradeStatus.OPEN,buy.getStatus());
		Assert.assertEquals(TradeStatus.CLOSED,sale.getStatus());
		Assert.assertEquals(5,ptf.getPosition(ibm),0.0);
		Assert.assertEquals(-50,sale.getProfitAndLoss(),0.0);
		
		
	}
	
	/**
	 * Cas Renforcement en 3 étapes , puis sortie totale  : Achat 3 ibm @ 100, Achat 3 ibm @ 110, Achat 3 ibm @ 120, Vente 9 ibm @ 115 => P&L = 45 
	 * @throws PortfolioException 
	 */
	@Test
	public void testExitAfterMultipleBuy() throws PortfolioException {
		
		// Achat 3 IBM @ 100 
		Trade buy1 = new Trade(TradeSide.BUY,ibm, 3, 300,  new DateTime());
		ptf.addTransaction(buy1);
		Assert.assertEquals(700,ptf.getCash(),0.0);
		Assert.assertEquals(3,ptf.getPosition(ibm),0.0);

		// Achat 3 IBM @ 100 
		Trade buy2 = new Trade(TradeSide.BUY,ibm, 3, 330,  new DateTime());
		ptf.addTransaction(buy2);
		Assert.assertEquals(370,ptf.getCash(),0.0);
		Assert.assertEquals(6,ptf.getPosition(ibm),0.0);
		
		// Achat 3 IBM @ 120 
		Trade buy3 = new Trade( TradeSide.BUY,ibm, 3, 360, new DateTime());
		ptf.addTransaction(buy3);
		Assert.assertEquals(10,ptf.getCash(),0.0);
		Assert.assertEquals(9,ptf.getPosition(ibm),0.0);
		
		
		// Vente 9 IBM @ 115
		Trade sale = new Trade(TradeSide.SELL,ibm, 9, 1035,  new DateTime());
		ptf.addTransaction(sale);
		
		Assert.assertEquals(0,ptf.getPosition(ibm),0.0);
		Assert.assertEquals(1045,ptf.getCash(),0.0);
		Assert.assertEquals(TradeStatus.CLOSED,sale.getStatus());
		Assert.assertEquals(TradeStatus.CLOSED,buy1.getStatus());
		Assert.assertEquals(TradeStatus.CLOSED,buy2.getStatus());
		Assert.assertEquals(TradeStatus.CLOSED,buy3.getStatus());
		Assert.assertEquals(45,sale.getProfitAndLoss(),0.0);
		
		
	}
	
	/**
	 * Cas Renforcement en 3 étapes , puis sortie partielle en 3 étapes  : 
	 * Achat 3 ibm @ 100, Achat 3 ibm @ 110, Achat 3 ibm @ 120, 
	 * Vente 2 ibm @ 115 => P&L FIFO = 30 , vente 2 ibm @ 110 => P&L FIFO = 10 
	 * @throws PortfolioException 
	 */
	@Test
	public void testMultipleSaleAfterMultipleBuy() throws PortfolioException {
		
		// Achat 3 IBM @ 100 
		Trade buy1 = new Trade(TradeSide.BUY,ibm, 3, 300,  new DateTime());
		ptf.addTransaction(buy1);
		Assert.assertEquals(700,ptf.getCash(),0.0);
		Assert.assertEquals(3,ptf.getPosition(ibm),0.0);

		// Achat 3 IBM @ 100 
		Trade buy2 = new Trade(TradeSide.BUY,ibm, 3, 330,  new DateTime());
		ptf.addTransaction(buy2);
		Assert.assertEquals(370,ptf.getCash(),0.0);
		Assert.assertEquals(6,ptf.getPosition(ibm),0.0);
		
		// Achat 3 IBM @ 120 
		Trade buy3 = new Trade(TradeSide.BUY,ibm, 3, 360,  new DateTime());
		ptf.addTransaction(buy3);
		Assert.assertEquals(10,ptf.getCash(),0.0);
		Assert.assertEquals(9,ptf.getPosition(ibm),0.0);
		
		
		// Vente 2 IBM @ 115
		Trade sale1 = new Trade( TradeSide.SELL,ibm, 2, 230, new DateTime());
		ptf.addTransaction(sale1);
		Assert.assertEquals(7,ptf.getPosition(ibm),0.0);
		Assert.assertEquals(240,ptf.getCash(),0.0);
		Assert.assertEquals(TradeStatus.CLOSED,sale1.getStatus());
		Assert.assertEquals(TradeStatus.OPEN,buy1.getStatus());
		Assert.assertEquals(30,sale1.getProfitAndLoss(),0.0);
		
		// Vente 2 IBM @ 110
		Trade sale2 = new Trade(TradeSide.SELL,ibm, 2, 220,  new DateTime());
		ptf.addTransaction(sale2);
		Assert.assertEquals(5,ptf.getPosition(ibm),0.0);
		Assert.assertEquals(460,ptf.getCash(),0.0);
		Assert.assertEquals(TradeStatus.CLOSED,sale2.getStatus());
		Assert.assertEquals(TradeStatus.CLOSED,buy1.getStatus());
		Assert.assertEquals(10,sale2.getProfitAndLoss(),0.0);
		
		
	}
	
	/**
	 * Cas achat vente achat vente 
	 * Achat 3 ibm @ 100, Achat 3 ibm @ 110, Achat 3 ibm @ 120, 
	 * Vente 2 ibm @ 115 => P&L FIFO = 30 , vente 2 ibm @ 110 => P&L FIFO = 10 
	 * @throws PortfolioException 
	 */
	@Test
	public void testBuySellBuySell() throws PortfolioException {
		
		// Achat 3 IBM @ 100 
		Trade buy1 = new Trade(TradeSide.BUY,ibm, 3, 300,  new DateTime());
		ptf.addTransaction(buy1);
		Assert.assertEquals(700,ptf.getCash(),0.0);
		Assert.assertEquals(3,ptf.getPosition(ibm),0.0);
		
		// Vente 2 IBM @ 115
		Trade sale1 = new Trade(TradeSide.SELL,ibm, 2, 230,  new DateTime());
		ptf.addTransaction(sale1);
		Assert.assertEquals(1,ptf.getPosition(ibm),0.0);
		Assert.assertEquals(930,ptf.getCash(),0.0);
		Assert.assertEquals(TradeStatus.CLOSED,sale1.getStatus());
		Assert.assertEquals(TradeStatus.OPEN,buy1.getStatus());
		Assert.assertEquals(30,sale1.getProfitAndLoss(),0.0);

		// Achat 3 IBM @ 110 
		Trade buy2 = new Trade(TradeSide.BUY,ibm, 3, 330,  new DateTime());
		ptf.addTransaction(buy2);
		Assert.assertEquals(600,ptf.getCash(),0.0);
		Assert.assertEquals(4,ptf.getPosition(ibm),0.0);
		
		// Vente 2 IBM @ 110
		Trade sale2 = new Trade(TradeSide.SELL,ibm, 2, 220,  new DateTime());
		ptf.addTransaction(sale2);
		Assert.assertEquals(2,ptf.getPosition(ibm),0.0);
		Assert.assertEquals(820,ptf.getCash(),0.0);
		Assert.assertEquals(TradeStatus.CLOSED,sale2.getStatus());
		Assert.assertEquals(TradeStatus.CLOSED,buy1.getStatus());
		Assert.assertEquals(10,sale2.getProfitAndLoss(),0.0);
		
		
	}
	@Test(expected = PortfolioException.class)
	public void testNotEnoughCash() throws PortfolioException{
		//100 is not enough to buy 3 IBM @ 100
		ptf = new Portfolio("Test Ptf", Currency.USD,100);
		
		// Buy 3 IBM @ 100 ==> leads to exception
		Trade buy1 = new Trade(TradeSide.BUY,ibm, 3, 300,  new DateTime());
		ptf.addTransaction(buy1);
		
	}
	
	/**
	 * Use case : Short Sell nominal
	 * @throws PortfolioException
	 */
	@Test
	public void testShortSellNominal() throws PortfolioException{
		
		// Vente Short 2 IBM @ 110
		Trade sale1 = new Trade(TradeSide.SELL,ibm, 2, 220,  new DateTime());
		ptf.addTransaction(sale1);
		Assert.assertEquals(-2,ptf.getPosition(ibm),0.0);
		Assert.assertEquals(1220,ptf.getCash(),0.0);
		Assert.assertEquals(TradeStatus.OPEN,sale1.getStatus());
		
		// RAchat 2 IBM @ 100 
		Trade buy1 = new Trade(TradeSide.BUY,ibm, 2, 200,  new DateTime());
		ptf.addTransaction(buy1);
		
		/*
		 * Normalement, il ne reste plus d'IBM en portefeuille, 
		 * les deux trades sont bouclés, il y a 1020 en cash 
		 * et le PnL est de 20$ sur le trade de débouclage 
		 * 
		 */
		Assert.assertEquals(1020,ptf.getCash(),0.0);
		Assert.assertEquals(0,ptf.getPosition(ibm),0.0);
		Assert.assertEquals(TradeStatus.CLOSED,sale1.getStatus());
		Assert.assertEquals(TradeStatus.CLOSED,buy1.getStatus());
		Assert.assertEquals(20,buy1.getProfitAndLoss(),0.0);
		
	}
	
	/**
	 * Use Case : short entry then short exit and long entry in two trades 
	 * @throws PortfolioException
	 */
	@Test
	public void testShortSellShortExitAndLongEntry() throws PortfolioException{
		
		// Vente Short 2 IBM @ 110
		Trade sale1 = new Trade(TradeSide.SELL,ibm, 2, 220,  new DateTime());
		ptf.addTransaction(sale1);
		Assert.assertEquals(-2,ptf.getPosition(ibm),0.0);
		Assert.assertEquals(1220,ptf.getCash(),0.0);
		Assert.assertEquals(TradeStatus.OPEN,sale1.getStatus());
		
		// RAchat 3 IBM @ 100 
		Trade buy1 = new Trade(TradeSide.BUY,ibm, 3, 300,  new DateTime());
		ptf.addTransaction(buy1);
		
		/*
		 * Normalement, il ne reste plus d'IBM en portefeuille, 
		 * les deux trades sont bouclés, il y a 1020 en cash 
		 * et le PnL est de 20$ sur le trade de débouclage 
		 * 
		 */
		Assert.assertEquals(920,ptf.getCash(),0.0);
		Assert.assertEquals(1,ptf.getPosition(ibm),0.0);
		Assert.assertEquals(TradeStatus.CLOSED,sale1.getStatus());
		
	}
	
	/**
	 * Use Case : long entry then long exit short entry in two trades 
	 * @throws PortfolioException
	 */
	@Test
	public void testLongEntryLongExitShortEntry() throws PortfolioException{
		
		// Achat 2 IBM @ 100 
		Trade buy1 = new Trade(TradeSide.BUY,ibm, 2, 200,  new DateTime());
		ptf.addTransaction(buy1);
		Assert.assertEquals(2,ptf.getPosition(ibm),0.0);
		Assert.assertEquals(800,ptf.getCash(),0.0);
		Assert.assertEquals(TradeStatus.OPEN,buy1.getStatus());
		
		// Vente 3 IBM @ 110
		Trade sale1 = new Trade(TradeSide.SELL,ibm, 3, 330,  new DateTime());
		ptf.addTransaction(sale1);
		Assert.assertEquals(-1,ptf.getPosition(ibm),0.0);
		Assert.assertEquals(1130,ptf.getCash(),0.0);
		Assert.assertEquals(TradeStatus.CLOSED,buy1.getStatus());
	
		
	}
	

}
