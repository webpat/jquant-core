package org.jquant.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * <p>Discover the strategies annotated by Strategy in the classpath, initialize their market with the help of the MarketManager, 
 * dispatch the Candles and the Quotes during the Simulation.
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
	private Map<String,IStrategy> strategies;
	
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
		
		
		// Find all strategies  
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AnnotationTypeFilter(Strategy.class));
		
		String root = getBasePackage();
		if (StringUtils.isEmpty(root)){
			root = this.getClass().getPackage().getName();
		}
		Set<BeanDefinition> components = provider.findCandidateComponents(root);
		
		strategies = new HashMap<String, IStrategy>();
		
		/**
		 *  For All Strategies
		 */
		for (BeanDefinition strategyBean : components){

			// FIXME : ensure strategy inherits AbstractStrategy 
			AbstractStrategy strategy = (AbstractStrategy) Class.forName(strategyBean.getBeanClassName()).newInstance();
			strategies.put(strategy.getId(), strategy);
			
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
			 * Give the strategy a reference to the instruments table 
			 */
			strategy.setCandleSerieMap(instruments);
			
			/*
			 * Give the strategy a grab on the exec provider 
			 */
			strategy.setOrderManager(orderManager);
	
			
			/*
			 * Give the strategy the access to the global Portfolio
			 * TODO replace by new Portfolio and Allocation by MoneyManager
			 */
			
			strategy.setPortfolio(globalPortfolio);
			
			/*
			 * Give the OrderManager access to the global Portfolio to turn filled orders into Trades 
			 */
			orderManager.setPortfolio(globalPortfolio);
			
			/*
			 * Tell the OrderManager the strategy is listening to the changes in Positions 
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

				List<Pair<InstrumentId, Candle>> slice = marketMgr.getMarketSlice(dt);

				for (Pair<InstrumentId, Candle> pair : slice){
					// Grow the instruments table
					 CandleSerie cs = instruments.get(pair.getLeft());
					 
					 if (cs == null){
						 cs = new CandleSerie(pair.getLeft());
						 instruments.put(pair.getLeft(), cs);
					 }
					 cs.addValue(pair.getRight());
					 
					// TODO : Multi Threading here ...
					/*
					 * Runtime.availableProcessors() 
					 * create that many java.util.concurrent.Callable Objects
					 * use java.util.concurrent.ExecutorService with a pool of java.util.concurrent.Executors
					 * distribuer les stratégies aux processeurs 
					 * exécuter la boucle dans les executors 
					 */ 
					
					// Feed the Strategies 
					for (IStrategy s : strategies.values()){
						if (s.getMarket().contains(pair.getLeft())){
							
							s.onCandle(pair.getLeft(), pair.getRight());
						}
					}
				}
			}
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
	 * 
	 * @return The Strategy Scan Entry Point
	 */
	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}
	
	

}
