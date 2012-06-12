package org.jquant.strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.jquant.data.MarketManager;
import org.jquant.exception.MarketDataReaderException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * The StrategyRunner aka the <b>BackTestRunner</b> is the simulation player, he plays the strategies on <b>historical market data</b>
 * <p>In Multi strategy Mode : discover the strategies annotated by Strategy in <b>basePackage</b>, initialize their market with the help of the MarketManager, 
 * dispatch the Candles and the Quotes during the Simulation.
 * <p> In Single Strategy Mode : run the <b>strategyClassName</b>
 * <p>TODO: In Multi Strategy Mode : run the <b>strategyClasses</b> list 
 * 
 * @author patrick.merheb
 * @see AbstractStrategy
 * @see MarketManager
 */
@Component
public class StrategyRunner {
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
	private final Map<InstrumentId,CandleSerie> series = new HashMap<InstrumentId, CandleSerie>();
	
	
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
	private Portfolio globalPortfolio;
	
	
	private boolean monoStrategyMode = false;
	

	/**
	 * {@link StrategyRunner#getStrategyClassName()}
	 */
	private String strategyClassName;

	
	
	public StrategyRunner() {
		super();
		
	}

	
		
		/*
		 * Scan Phase
		 */
		
		 
//		String root = getBasePackage();
//
//		if (StringUtils.isNotEmpty(root) && StringUtils.isNotEmpty(strategyClassName)){
//			throw new RuntimeException("Cannot define strategyClassName and basePackage at the same time.");
//		}
//		
//		if (StringUtils.isNotEmpty(root)){
//			/*
//			 *  If scan mode, find all strategies
//			 */
//			ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
//			provider.addIncludeFilter(new AnnotationTypeFilter(Strategy.class));
//			
//			Set<BeanDefinition> components = provider.findCandidateComponents(root);
//
//			for (BeanDefinition strategyBean : components){
//				// FIXME : ensure strategy inherits AbstractStrategy 
//				AbstractStrategy strategy = (AbstractStrategy) Class.forName(strategyBean.getBeanClassName()).newInstance();
//				strategies.put(strategy.getId(), strategy);
//			}
//		} else {
//			/*
//			 *  No detection Mode 
//			 */
//			AbstractStrategy strategy = (AbstractStrategy) Class.forName(strategyClassName).newInstance();
//			strategies.put(strategy.getId(), strategy);
//		}
		
		
		
		


	/**
	 * Initialization of the StrategyRunner prior to call run()
	 *  TODO : Many Strategies 
	 */
	public void init(){
		
		try{
		strategies = new HashMap<String, AbstractStrategy>();
		@SuppressWarnings("unchecked")
		Class<? extends AbstractStrategy> stratClass = (Class<? extends AbstractStrategy>) Class.forName(getStrategyClassName());
		
		if (stratClass.getSuperclass().equals(MonoStrategy.class)){
			// MonoStrategy Mode;
			monoStrategyMode = true;
			initMonoStrategy(stratClass);
		}else{
			// MultiStrategy Mode
			initMultiStrategy(stratClass);
		}
		}catch(MarketDataReaderException mde){
			throw new RuntimeException("Problem initializing historical market data.");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		/*
		 * Give the OrderManager access to the global Portfolio to turn filled orders into Trades 
		 */
		orderManager.setPortfolio(globalPortfolio);
	}
	
	
			
		private void initMultiStrategy(Class<? extends AbstractStrategy> stratClass) throws InstantiationException, IllegalAccessException, MarketDataReaderException {
			/*
			 * Get the market from the strategy class
			 * Use one instance to do so (maybe a better way to do this) 
			 */
			MultiStrategy strategy = (MultiStrategy) stratClass.newInstance();
			
			for (InstrumentId symbol :strategy.getMarket()){
				
				/*
				 * Add to Market Manager
				 */
				marketMgr.addInstrument(symbol, precision, getEntryDate(), getExitDate());
				
				
				/*
				 * Add to the general instruments Map 
				 */
				if (!series.containsKey(symbol)){
					series.put(symbol, new CandleSerie(symbol));
				}
			}
			
			/*
			 * Instanciate a new strategy 
			 * MultiStrategy mode = n instrument --> 1 strategy instance  
			 */
			MultiStrategy strat = (MultiStrategy) stratClass.newInstance();
			strat.setOrderManager(orderManager);
			strat.setPortfolio(globalPortfolio);
			strat.setCandleSerieMap(series);
			
			/*
			 * the strategies are listening to the Order Events
			 */
			orderManager.addStrategy(strat);
			
			strategies.put(strat.getId() , strat);
			
		
	}



		private void initMonoStrategy(Class<? extends AbstractStrategy> stratClass) throws InstantiationException, IllegalAccessException, MarketDataReaderException {
			
					
			/*
			 * Get the market from the strategy class
			 * Use one instance to do so (maybe a better way to do this) 
			 */
			MonoStrategy strategy = (MonoStrategy) stratClass.newInstance();
	
	
			for (InstrumentId symbol :strategy.getMarket()){
					
					/*
					 * Add to Market Manager
					 */
					marketMgr.addInstrument(symbol, precision, getEntryDate(), getExitDate());
					
					
					/*
					 * Add to the general instruments Map 
					 */
					if (!series.containsKey(symbol)){
						series.put(symbol, new CandleSerie(symbol));
					}
					
					/*
					 * Instanciate a new strategy 
					 * MonoStrategy mode = 1 instrument --> 1 strategy instance  
					 */
					MonoStrategy strat = (MonoStrategy) stratClass.newInstance();
					
					strat.setInstrument(symbol);
					strat.setSerie(series.get(symbol));
					strat.setOrderManager(orderManager);
					strat.setPortfolio(globalPortfolio);
					
					/*
					 * the strategies are listening to the Order Events
					 */
					orderManager.addStrategy(strat);
					
					strategies.put(strat.getId() + ":" + symbol.getCode(), strat);
				}
			
			// We don not need this instance anymore
			strategy = null;
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
			
			
			/* Begin Simulation rePlay
			 * TODO : a bit of multithreading here 
			 * Runtime.availableProcessors() 
			 * create that many java.util.concurrent.Callable Objects
			 * use java.util.concurrent.ExecutorService with a pool of java.util.concurrent.Executors
			 * distribuer les stratégies aux processeurs 
			 * exécuter la boucle dans les executors 
			 */ 
			for (DateTime dt : cal){

				Map<InstrumentId, Candle> slice = marketMgr.getMarketSlice(dt);

				for (Entry<InstrumentId, Candle> pair : slice.entrySet()){
					
					InstrumentId instrument = pair.getKey();
					Candle candle = pair.getValue();
					
					// Grow the instruments table
					 CandleSerie cs = series.get(instrument);
					 if (cs == null){
						 cs = new CandleSerie(instrument);
						 series.put(instrument, cs);
					 }
					 cs.addValue(candle);
					 
					 
					 
					 /*
					  * Call onCandleOpen in strategies 
					  */
					 for (IStrategy s : strategies.values()){
							if ((monoStrategyMode && ((MonoStrategy)s).getInstrument().equals(instrument)) || (!monoStrategyMode && s.getMarket().contains(instrument))){
								
								s.onCandleOpen(instrument, candle);
							}
						}
					 
					 /*
					  * Call onCandleOpen in OrderManager (execution of start of the day orders ) 
					  */
					 orderManager.onCandleOpen(instrument, candle);
					 
					 
					 /*
					  * Call onCandle (completed candle) in the Order Manager (intra day orders ) 
					  */
					 orderManager.onCandle(instrument, candle);
					 
					/*
					 * Call onCandle (completed candle) in the strategies
					 */
					for (AbstractStrategy s : strategies.values()){
						
						s.setNow(dt); // Internal clock of the strategy
						if ((monoStrategyMode && ((MonoStrategy)s).getInstrument().equals(instrument)) || (!monoStrategyMode && s.getMarket().contains(instrument))){
							s.onCandle(instrument, candle);
						}
					}
					
					// TODO : special end of the day Orders 
					
					
				} // end slice loop 
			
				//transfer the slice to the global Portfolio for marking to market and build the equity curve
				globalPortfolio.markToMarket(dt, slice);
				
			}// End calendar loop 
			
			
			displayStats(cal);
			
			
		}else {
			logger.error("No Strategies found, check the basePackage parameter");
		}
		
	}



	private void displayStats(IDateTimeCalendar cal) {
		/*
		 * Compute and display simulation summary
		 */
		PortfolioStatistics stats = new PortfolioStatistics(globalPortfolio);
		logger.info("Simulation summary from " + cal.getStartDay().toString(DateTimeFormat.shortDate()) + " to " +  cal.getEndDay().toString(DateTimeFormat.shortDate()));
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
	 * Single Strategy Mode
	 * @return The Strategy class Name
	 */
	public String getStrategyClassName() {
		return strategyClassName;
	}

	public void setStrategyClassName(String strategyClassName) {
		this.strategyClassName = strategyClassName;
	}

	public Portfolio getGlobalPortfolio() {
		return globalPortfolio;
	}

	public void setGlobalPortfolio(Portfolio globalPortfolio) {
		this.globalPortfolio = globalPortfolio;
	}
	
	

}
