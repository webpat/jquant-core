package org.jquant;

import org.joda.time.DateTime;
import org.jquant.model.Currency;
import org.jquant.model.MarketDataPrecision;
import org.jquant.portfolio.Portfolio;
import org.jquant.strategy.StrategyRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap {

	@Autowired
	StrategyRunner runner;
	
	public static void main(String[] args) {
		
		if (args.length!= 5){
			throw new IllegalArgumentException("Wrong command line parameters." +
					"Usage is java Bootstrap strategyClassName entryDate exitDate Currency initialAmount");
		}
		
		//TODO : Check args syntax 
		//TODO: Check args for consistency
		
		String strategyClassName = args[0];
		DateTime entryDate =  new DateTime(args[1]);
		DateTime exitDate =  new DateTime(args[2]);
		Currency currency = Currency.valueOf(args[3]);
		Double initialAmount = Double.parseDouble(args[4]);
		
		
		ApplicationContext context = new ClassPathXmlApplicationContext("jquant-config.xml");

		StrategyRunner sr = context.getBean(StrategyRunner.class);
		sr.setStrategyClassName(strategyClassName);
		sr.setEntryDate(entryDate);
		sr.setExitDate(exitDate);
		sr.setCurrency(currency);
		
		sr.setPrecision(MarketDataPrecision.CANDLE);
		
		/*
		 * Init Portfolio 
		 */
		Portfolio ptf = new Portfolio("My strategy portfolio", currency,initialAmount);
		sr.setGlobalPortfolio(ptf);
		
		sr.init();
		sr.run();
		
	}

	

}
