package org.jquant.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.jquant.data.MarketManager;
import org.jquant.exception.MarketDataReaderException;
import org.jquant.model.Currency;
import org.jquant.model.InstrumentId;
import org.jquant.model.InstrumentType;
import org.jquant.model.MarketIdentifierCode;
import org.jquant.model.StitchingMethod;
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
	
	private List<String> stratClassNames;
	
	
	/**
	 * The main Portfolio (common to all strategies)
	 * TODO: Replace by initial cash and MoneyManager with 1 portfolio for each strategy  
	 */
	private Portfolio globalPortfolio;
	
	
	private boolean monoStrategyMode = false;
	
	
	public StrategyRunner() {
		super();
		
	}


	/**
	 * Initialization of the StrategyRunner prior to call run()
	 * @param entry {@link DateTime} entry date of the simulation
	 * @param exit {@link DateTime} exit date of the simulation
	 */
	public void init(DateTime entry, DateTime exit){
		
		entryDate = entry;
		exitDate = exit;
		
		try{
			strategies = new HashMap<String, AbstractStrategy>();
			for (String stratClassName : stratClassNames){


				@SuppressWarnings("unchecked")
				Class<? extends AbstractStrategy> stratClass = (Class<? extends AbstractStrategy>) Class.forName(stratClassName);
				
					
				if (stratClass.getSuperclass().equals(MonoAssetStrategy.class)){
					// MonoStrategy Mode;
					monoStrategyMode = true;
					initMonoStrategy(stratClass);
				}else{
					// MultiAssetStrategy Mode
					initMultiStrategy(stratClass);
				}
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		/*
		 * Trim calendar
		 */
		
		trimCalendar();
		
		/*
		 * Give the OrderManager access to the global Portfolio to turn filled orders into Trades 
		 */
		orderManager.setPortfolio(getGlobalPortfolio());
	}
	
	
			
		private void initMultiStrategy(Class<? extends AbstractStrategy> stratClass) throws InstantiationException, IllegalAccessException {
			
			if (monoStrategyMode){
				throw new RuntimeException("Can not combine multi asset strategy with mono asset strategy");
			}
			
			/*
			 * Get the market from the strategy class
			 * Use one instance to do so (maybe a better way to do this) 
			 */
			MultiAssetStrategy strategy = (MultiAssetStrategy) stratClass.newInstance();
			
			/*
			 * Add instruments to market
			 */
			strategy.initMarket();
			
			for (InstrumentId symbol :strategy.getMarket()){
				
				/*
				 * Add to Market Manager
				 */
//				marketMgr.addInstrument(symbol, getEntryDate(), getExitDate());
				
				
				/*
				 * Add to the general instruments Map 
				 */
				if (!series.containsKey(symbol)){
					series.put(symbol, new CandleSerie(symbol));
				}
			}
			
			/*
			 * Instanciate a new strategy 
			 * MultiAssetStrategy mode = n instrument --> 1 strategy instance  
			 */
			MultiAssetStrategy strat = (MultiAssetStrategy) stratClass.newInstance();
			strat.setOrderManager(orderManager);
			strat.setPortfolio(getGlobalPortfolio());
			strat.setCandleSerieMap(series);
			
			/*
			 * the strategies are listening to the Order Events
			 */
			orderManager.addStrategy(strat);
			
			
			
			strategies.put(strat.getId() , strat);
			
		
	}



		private void initMonoStrategy(Class<? extends AbstractStrategy> stratClass) throws InstantiationException, IllegalAccessException {


			/*
			 * Get the market from the strategy class
			 * Use one instance to do so (maybe a better way to do this) 
			 */
			MonoAssetStrategy strategy = (MonoAssetStrategy) stratClass.newInstance();


			/*
			 * Add the instruments to the mktManager
			 */
			strategy.initMarket();

			for (InstrumentId symbol :strategy.getMarket()){

				/*
				 * Add to Market Manager
				 */
				addInstrument(symbol);


				/*
				 * Add to the general instruments Map 
				 */
				if (!series.containsKey(symbol)){
					series.put(symbol, new CandleSerie(symbol));
				}

				/*
				 * Instanciate a new strategy 
				 * MonoAssetStrategy mode = 1 instrument --> 1 strategy instance  
				 */
				MonoAssetStrategy strat = (MonoAssetStrategy) stratClass.newInstance();

				strat.setInstrument(symbol);
				strat.setSerie(series.get(symbol));
				strat.setOrderManager(orderManager); // TODO : Variable de classe et non d'instance
				strat.setPortfolio(getGlobalPortfolio());

				/*
				 * the strategies are listening to the Order Events
				 */
				orderManager.addStrategy(strat);

				strategies.put(strat.getId() + ":" + symbol.getCode(), strat);
			}

			// We do not need this instance anymore
			strategy = null;
		}

		/**
		 * StrategyRunner Calendar must start from the oldest historical market data 
		 * and end at the youngest one.  
		 */
		private void trimCalendar() {
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
				entryDate = firstLast.getLeft();
			}

			if (firstLast.getRight().isBefore(getExitDate())){
				exitDate = firstLast.getRight();
			}
		}


		/**
		 * Use the right Add method of the mktManager depending on tge {@link InstrumentType} of the symbol 
		 * @param symbol {@link InstrumentId}
		 * @throws MarketDataReaderException
		 */
		private void addInstrument(InstrumentId symbol) {
			try {
				switch (symbol.getType()) {
				case GENERIC_FUTURE:
					//FIXME : StitchingMethod as StrategyRunner parameter
					marketMgr.addGenericFuture(symbol, getEntryDate(), getExitDate(),StitchingMethod.RETURN_ADJUSTED);
					break;

				default:
					marketMgr.addInstrument(symbol, getEntryDate(), getExitDate());
					break;
				}
				
				
			} catch (MarketDataReaderException e) {
				throw new RuntimeException("Problem initializing historical market data.",e);
			}
		}

		/**
		 * Execute Strategies 
		 * Dispatch candles/quotes to strategies 
		 * @return a {@link PortfolioStatistics}
		 */
		public PortfolioStatistics run(){

			logger.info("Beginning strategy(ies) back testing");
			final long start = System.nanoTime();
			
			/*
			 * Call Init Method on Strategies 
			 */
			for (IStrategy s : strategies.values()){
				s.init();
			}

			IDateTimeCalendar cal = CalendarFactory.getDailyTradingDayBrowser(entryDate, exitDate, MarketIdentifierCode.NO_MIC);
			

			/* 
			 * Simulation rePlay
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
						if ((monoStrategyMode && ((MonoAssetStrategy)s).getInstrument().equals(instrument)) || (!monoStrategyMode && ((MultiAssetStrategy)s).getMarket().contains(instrument))){

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
						if ((monoStrategyMode && ((MonoAssetStrategy)s).getInstrument().equals(instrument)) || (!monoStrategyMode && ((MultiAssetStrategy)s).getMarket().contains(instrument))){
							s.onCandle(instrument, candle);
						}
					}

					// TODO : End of the day Orders 


				} // end slice loop 

				//transfer the slice to the global Portfolio for marking to market and build the equity curve
				getGlobalPortfolio().markToMarket(dt, slice);

			}// End calendar loop 

			PortfolioStatistics stats = new PortfolioStatistics(getGlobalPortfolio(),entryDate.toDate(),exitDate.toDate());
			
			final long end = System.nanoTime();
			
			logger.info("Backtesting Time (seconds) taken is " + (end - start)/1.0e9);
			return stats;
		}

		/**
		 * 
		 * @return Simulation/Replay  begining date
		 */
		public DateTime getEntryDate() {
			return entryDate;
		}


		/**
		 * 
		 * @return Simulation/Replay End Date
		 */
		public DateTime getExitDate() {
			return exitDate;
		}

		/**
		 * 
		 * @return Simulation Main Currency
		 */
		public Currency getCurrency() {
			return currency;
		}


		public Portfolio getGlobalPortfolio() {
			return globalPortfolio;
		}


		public void setGlobalPortfolio(Portfolio globalPortfolio) {
			this.globalPortfolio = globalPortfolio;
		}


		public List<String> getStratClassNames() {
			return stratClassNames;
		}


		public void setStratClassNames(List<String> stratClassNames) {
			this.stratClassNames = stratClassNames;
		}

	
	

}
