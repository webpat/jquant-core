package org.jquant.strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.jquant.data.MarketManager;
import org.jquant.model.Currency;
import org.jquant.model.InstrumentId;
import org.jquant.model.MarketDataPrecision;
import org.jquant.order.IOrderManager;
import org.jquant.portfolio.Portfolio;
import org.jquant.portfolio.PortfolioStatistics;
import org.jquant.serie.Candle;
import org.jquant.serie.CandleSerie;
import org.jquant.time.calendar.CalendarFactory;
import org.jquant.time.calendar.IDateTimeCalendar;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;


/**
 * The StrategyRunner aka the <b>BackTestRunner</b> is the simulation player, he plays the strategies on <b>historical market data</b>
 * <p>In Multi strategy Mode : discover the strategies annotated by Strategy in <b>basePackage</b>, initialize their market with the help of the MarketManager, 
 * dispatch the Candles and the Quotes during the Simulation.
 * <p> In Single Strategy Mode : run the <b>strategyClassName</b>
 * @author patrick.merheb
 * @see AbstractStrategy
 * @see MarketManager
 */
public class StrategyRunner implements InitializingBean{
	/** logger */
	private static final Logger logger = Logger.getLogger(StrategyRunner.class);
	
	@Autowired
	private MarketManager marketMgr;
	
	@Autowired
	private IOrderManager orderManager;
	
	/**
	 * Map of all strategies
	 */
	private Map<String,AbstractStrategy> strategies;
	
	/*
	 * Growing Map of CandleSeries, the candleseries are growing gradually candle by candle during the simulation
	 */
	private final Map<InstrumentId,CandleSerie> instruments = new HashMap<InstrumentId, CandleSerie>();
	
	
	/**
	 * {@link #getEntryDate()}
	 */
	private DateTime entryDate;
	
	/**
	 * {@link #getExitDate()}
	 */
	private DateTime exitDate;
	
	/**
	 * {@link #getCurrency()}
	 */
	private Currency currency;
	
	/**
	 * {@link #getPrecision()}
	 */
	private MarketDataPrecision precision;
	
	/**
	 * The main Portfolio (common to all strategies)
	 * TODO: Replace by initial cash and MoneyManager with 1 portfolio for each strategy  
	 */
	private final Portfolio globalPortfolio;
	
	
	/**
	 * {@link #getBasePackage()}
	 */
	private String basePackage;
	
	private String strategyClassName;

	
	
	public StrategyRunner(Portfolio ptf, DateTime entryDate, DateTime exitDate, Currency currency, MarketDataPrecision precision) {
		super();
		this.globalPortfolio = ptf;
		this.entryDate = entryDate;
		this.exitDate = exitDate;
		this.currency = currency;
		this.precision = precision;
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		/*
		 * Scan Phase
		 */
		strategies = new HashMap<String, AbstractStrategy>();
		 
		String root = getBasePackage();

		if (StringUtils.isNotEmpty(root) && StringUtils.isNotEmpty(strategyClassName)){
			throw new RuntimeException("Cannot define strategyClassName and basePackage at the same time.");
		}
		
		if (StringUtils.isNotEmpty(root)){
			/*
			 *  If multi strategy scan mode, find all strategies
			 */
			ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
			provider.addIncludeFilter(new AnnotationTypeFilter(Strategy.class));
			
			Set<BeanDefinition> components = provider.findCandidateComponents(root);

			for (BeanDefinition strategyBean : components){
				// FIXME : ensure strategy inherits AbstractStrategy 
				AbstractStrategy strategy = (AbstractStrategy) Class.forName(strategyBean.getBeanClassName()).newInstance();
				strategies.put(strategy.getId(), strategy);
			}
		} else {
			/*
			 *  No detection Mode 
			 */
			AbstractStrategy strategy = (AbstractStrategy) Class.forName(strategyClassName).newInstance();
			strategies.put(strategy.getId(), strategy);
		}
		
		/*
		 *  TODO : Extract method for  this phase 
		 *  Init market for all strategies (build the MarketDataHistory (market data timeline) 
		 */
		for (AbstractStrategy strategy : strategies.values()){
			/*
			 * Init Market for the strategy
			 */
			strategy.initMarket();
			
			for (InstrumentId symbol :strategy.getMarket()){
				/*
				 * Add to Market Manager
				 */
				marketMgr.addInstrument(symbol, precision, getEntryDate(), getExitDate());
				/*
				 * Add to the general instruments Map 
				 */
				if (!instruments.containsKey(symbol)){
					instruments.put(symbol, new CandleSerie(symbol));
				}
			}
			
			/*
			 * Give the strategy a reference to the instruments table to manipulate market data series
			 */
			strategy.setCandleSerieMap(instruments);
			
			/*
			 * Give the strategy a grab on the exec provider to pass order
			 */
			strategy.setOrderManager(orderManager);
	
			
			/*
			 * Give the strategy the access to the global Portfolio
			 * TODO PortfolioManager ? 
			 */
			
			strategy.setPortfolio(globalPortfolio);
			
			/*
			 * Give the OrderManager access to the global Portfolio to turn filled orders into Trades 
			 */
			orderManager.setPortfolio(globalPortfolio);
			
			/*
			 * the strategies are listening to the Order Events
			 */
			orderManager.addStrategy(strategy);
		}
		
		
	}
	
	/**
	 * Execute Strategies 
	 * Dispatch candles/quotes to strategies 
	 */
	public void run(){

		if (strategies != null && strategies.size()>0){

			/**
			 *  Read Time Series in Market Manager 
			 */
			Pair<DateTime, DateTime> firstLast = marketMgr.getFirstLast();

			// Time Assertions 

			if (firstLast.getLeft().isAfter(getExitDate()) || firstLast.getRight().isBefore(getEntryDate())){
				throw new RuntimeException("Wrong simulation calendar.");
			}


			// Calendar trim 

			if (firstLast.getLeft().isAfter(getEntryDate())){
				setEntryDate(firstLast.getLeft());
			}

			if (firstLast.getRight().isBefore(getExitDate())){
				setExitDate(firstLast.getRight());
			}

			// Build the simulation calendar TODO: Use mic Market for holidays
			IDateTimeCalendar cal = CalendarFactory.getDailyTradingDayBrowser(getEntryDate(), getExitDate(), null);

			
			/*
			 * Call Init Method on Strategies 
			 */
			for (IStrategy s : strategies.values()){
				s.init();
			}
			
			
			// Begin Simulation rePlay
			for (DateTime dt : cal){

				Map<InstrumentId, Candle> slice = marketMgr.getMarketSlice(dt);

				for (Entry<InstrumentId, Candle> pair : slice.entrySet()){
					
					// Grow the instruments table
					 CandleSerie cs = instruments.get(pair.getKey());
					 if (cs == null){
						 cs = new CandleSerie(pair.getKey());
						 instruments.put(pair.getKey(), cs);
					 }
					 cs.addValue(pair.getValue());
					 
					 /*
					  * Call onCandleOpen in OrderManager (execution of pending orders) 
					  */
					 orderManager.onCandleOpen(pair.getKey(), pair.getValue());
					 
					 /*
					  * Call onCandleOpen in strategies 
					  */
					 for (IStrategy s : strategies.values()){
							if (s.getMarket().contains(pair.getKey())){
								
								s.onCandleOpen(pair.getKey(), pair.getValue());
							}
						}
					 
					 /*
					  * Call onCandle (completed candle) in the Order Manager 
					  */
					 orderManager.onCandle(pair.getKey(), pair.getValue());
					 
					/*
					 * Call onCandle (completed candle) in the strategies
					 *
					 * TODO : a bit of multithreading here 
					 * Runtime.availableProcessors() 
					 * create that many java.util.concurrent.Callable Objects
					 * use java.util.concurrent.ExecutorService with a pool of java.util.concurrent.Executors
					 * distribuer les stratégies aux processeurs 
					 * exécuter la boucle dans les executors 
					 */ 
					
					for (IStrategy s : strategies.values()){
						if (s.getMarket().contains(pair.getKey())){
							
							s.onCandle(pair.getKey(), pair.getValue());
						}
					}
				} // end slice loop 
			
				//transfer the slice to the global Portfolio for marking to market and build the equity curve
				globalPortfolio.markToMarket(dt, slice);
				
			}// End calendar loop 
			
			
			/*
			 * Compute and display statistics 
			 */
			PortfolioStatistics stats = new PortfolioStatistics(globalPortfolio);
			logger.info("Simulation summary");
			logger.info("-------------------------------------------------------");
			logger.info("Initial Wealth \t" + stats.getInitialWealth());
			logger.info("Final Wealth \t" + stats.getFinalWealth());
			logger.info("Annual Return \t");
			logger.info("Profit And Loss \t" + stats.getRealizedPnL());
			logger.info("Max DrawDown  \t" );
			logger.info("Max DrawDown % \t" + stats.getMaxDrawDown().getMaxDrawDown());
			logger.info("Winning Trades \t" + stats.getWinningTrades());
			logger.info("Losing Trades \t" + stats.getLosingTrades());
			logger.info("Average Winning Trade \t" + stats.getAverageWinningTrade());
			logger.info("Average Losing Trade \t" + stats.getAverageLosingTrade());
			logger.info("Largest Winning Trade \t" + stats.getLargestWinningTrade());
			logger.info("Largest Losing Trade \t" + stats.getLargestLosingTrade());
			
			
		}else {
			logger.error("No Strategies found, check the basePackage parameter");
		}
		
	}
	

	/**
	 * 
	 * @return Simulation/Replay  begining date
	 */
	public DateTime getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(DateTime entryDate) {
		this.entryDate = entryDate;
	}

	/**
	 * 
	 * @return Simulation/Replay End Date
	 */
	public DateTime getExitDate() {
		return exitDate;
	}

	public void setExitDate(DateTime exitDate) {
		this.exitDate = exitDate;
	}

	
	/**
	 * 
	 * @return Simulation Main Currency
	 */
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	/**
	 * 
	 * @return Simulation precision 
	 * @see MarketDataPrecision
	 */
	public MarketDataPrecision getPrecision() {
		return precision;
	}

	public void setPrecision(MarketDataPrecision precision) {
		this.precision = precision;
	}

	/**
	 * Multi Strategy Mode 
	 * @return The Strategy Scan Entry Point
	 */
	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	/**
	 * Single Strategy Mode
	 * @return The Strategy class Name
	 */
	public String getStrategyClassName() {
		return strategyClassName;
	}

	public void setStrategyClassName(String strategyClassName) {
		this.strategyClassName = strategyClassName;
	}
	
	

}
