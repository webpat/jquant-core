package org.jquant;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class JQuantApplicationContext {

	private static Logger logger = Logger.getLogger(JQuantApplicationContext.class);
	private static ApplicationContext ctx;
	
	
	static{
	
		String xmlFileList = "jquant.xml";
		
		String[] array = xmlFileList.split(";");
		
		
		try {
			ctx = new ClassPathXmlApplicationContext(array);
		
		} catch (Throwable e) {
			logger.error("Cannot create the Application Context :", e);
		}
	}
	
	
	
//	public static InstrumentFactory getInstrumentFactory(){
//		return (InstrumentFactory) ctx.getBean("instrumentFactory");
//	}
//	
//	public static TermStructureFactory getTermStructureFactory(){
//		return (TermStructureFactory) ctx.getBean("curveFactory");
//	}
//	
//	public static TickerQuoteDAO getTimeSerieDAO() {
//		return (TickerQuoteDAO) ctx.getBean("tickerQuoteDAO");
//	}
//	
//	public static TickerDAO getTickerDAO() {
//		return (TickerDAO) ctx.getBean("tickerDAO");
//	}
//	
//	public static VolatilityDAO getVolatilityDAO() {
//		return (VolatilityDAO) ctx.getBean("volatilityDAO");
//	}
//	
//	public static ITimeSerieReader getTimeSerieReader(){
//		return (ITimeSerieReader) ctx.getBean("timeSerieReader");
//	}
//	
//	public static TickerCdsDAO getTickerCdsDAO() {
//		return (TickerCdsDAO) ctx.getBean("tickerCdsDAO");
//	}
//	
//	public static TickerForexDAO getTickerForexDAO() {
//		return (TickerForexDAO) ctx.getBean("tickerForexDAO");
//	}
//	
//	public static TickerTimeDepositDAO getTickerTimeDepositDAO() {
//		return (TickerTimeDepositDAO) ctx.getBean("tickerTimeDepositDAO");
//	}
//	
//	public static TickerVarSwapDAO getTickerVarSwapDAO() {
//		return (TickerVarSwapDAO) ctx.getBean("tickerVarSwapDAO");
//	}
//	
//	public static TickerIndexDAO getTickerIndexDAO() {
//		return (TickerIndexDAO) ctx.getBean("tickerIndexDAO");
//	}
//	
//	public static TickerStockDAO getTickerStockDAO() {
//		return (TickerStockDAO) ctx.getBean("tickerStockDAO");
//	}
//	
//	public static TickerRateMaturityDAO getTickerRateMaturityDAO() {
//		return (TickerRateMaturityDAO) ctx.getBean("tickerRateMaturityDAO");
//	}
//	
//	public static VolatilityStructureFactory getVolatilityStructureFactory(){
//		return (VolatilityStructureFactory) ctx.getBean("volatilityStructureFactory");
//	}
//	
//	public static CalibrationModelFactory getCalibrationModelFactory(){
//		return (CalibrationModelFactory) ctx.getBean("calibrationModelFactory");
//	}
//
//	public static HestonParametersDAO getHestonParametersDAO() {
//		return (HestonParametersDAO) ctx.getBean("hestonParametersDAO");
//	}
//	
//	public static LxrCashDividendDAO getLxrCashDividendDAO() {
//		return (LxrCashDividendDAO) ctx.getBean("lxrCashDividendDAO");
//	}

}
