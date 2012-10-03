package org.jquant;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.jquant.model.Currency;
import org.jquant.portfolio.Portfolio;
import org.jquant.portfolio.PortfolioStatistics;
import org.jquant.strategy.StrategyRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap {

	@Autowired
	StrategyRunner runner;
	
	/** logger */
	private static final Logger logger = Logger.getLogger(Bootstrap.class);
	
	public static void main(String[] args) {
		
		if (args.length < 5){
			throw new IllegalArgumentException("Wrong command line parameters." +
					"Usage is java Bootstrap strategyClassName entryDate exitDate Currency initialAmount");
		}
		
		//TODO : Check args syntax 
		//TODO: Check args for consistency
		
		DateTime entryDate =  new DateTime(args[0]);
		DateTime exitDate =  new DateTime(args[1]);
		Currency currency = Currency.valueOf(args[2]);
		Double initialAmount = Double.parseDouble(args[3]);
		
		
		ApplicationContext context = new ClassPathXmlApplicationContext("jquant-config.xml");

		StrategyRunner sr = context.getBean(StrategyRunner.class);
		sr.setStratClassNames(Arrays.asList(Arrays.copyOfRange(args, 4,args.length)));
		
			
		/*
		 * Init Portfolio 
		 */
		Portfolio ptf = new Portfolio("My strategy portfolio", currency,initialAmount);
		sr.setGlobalPortfolio(ptf);
		
		/*
		 * Init simulation calendar
		 */
		sr.init(entryDate,exitDate);
		
		/*
		 * Launch the simulation 
		 */
		PortfolioStatistics stats = sr.run();
		
		displayStats(stats);
		
		/*
		 * Serialize PortfolioStatistics
		 */
		 try {
			ObjectOutput out = new ObjectOutputStream(new FileOutputStream("simulation.bin"));
			 out.writeObject(stats);
			 out.close();
		} catch (IOException e) {
			logger.error("Error during simulation serialization",e);
		}
		
	}

	
	private static void displayStats(PortfolioStatistics stats) {
		/*
		 * Compute and display simulation summary
		 */
		logger.info("Simulation summary from " + stats.getStart().toString(DateTimeFormat.shortDate()) + " to " +  stats.getEnd().toString(DateTimeFormat.shortDate()));
		logger.info("-------------------------------------------------------");
		logger.info("Initial Wealth \t" + stats.getInitialWealth());
		logger.info("Final Wealth \t" + stats.getFinalWealth());
		logger.info("Annualized Return \t" + stats.getAnnualizedReturn());
		logger.info("Profit And Loss \t" + stats.getRealizedPnL());
		logger.info("Max DrawDown % \t" + stats.getMaxDrawDown().getMaxDrawDown());
		logger.info("Winning Trades \t" + stats.getWinningTrades());
		logger.info("Losing Trades \t" + stats.getLosingTrades());
		logger.info("Average Winning Trade \t" + stats.getAverageWinningTrade());
		logger.info("Average Losing Trade \t" + stats.getAverageLosingTrade());
		logger.info("Largest Winning Trade \t" + stats.getLargestWinningTrade());
		logger.info("Largest Losing Trade \t" + stats.getLargestLosingTrade());
	}
	

}
