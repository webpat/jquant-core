package org.jquant.strategy;

import java.util.List;

import org.jquant.model.InstrumentId;
import org.jquant.serie.BBBA;
import org.jquant.serie.Candle;
import org.jquant.serie.CandleSerie;

/**
 * Interface to be implemented by Strategies 
 * <p> A Strategy is a component that defines a market (insvestment universe) and a handles a Candle  or a BBBA 
 * <p> TODO : onQuote() for INTRADAY
 * @author patrick.merheb
 * @see BBBA
 * @see Candle
 */
public interface IStrategy {

	
	/**
	 * Build the strategy universe using {@link AbstractStrategy#addInstrument(InstrumentId)}
	 * <p>
	 * Add the instruments 
	 */
	public  void initMarket();
	
	
	/**
	 * Init the strategy 
	 * <p>
	 * Indicators and watermarks 
	 */
	public void init();
	
	
	/**
	 * Receive Candle Event From the Simulation/MarketData Feed 
	 * @param instrument
	 * @param candle
	 */
	public void onCandle(InstrumentId instrument, Candle candle);


	/**
	 * 
	 * @return The investment universe of the strategy
	 */
	public List<InstrumentId> getMarket();

	
	/**
	 * 
	 * @param symbol a InstrumentId
	 * @return the Candleserie associated with the symbol
	 */
	public CandleSerie getCandleSerie(InstrumentId symbol);
	
	
	
}
