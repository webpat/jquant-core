package org.jquant.strategy;

import java.util.List;

import org.jquant.model.InstrumentId;
import org.jquant.portfolio.Trade.TradeSide;
import org.jquant.serie.BBBA;
import org.jquant.serie.Candle;

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
	 * 
	 * @return The investment universe of the strategy
	 */
	public List<InstrumentId> getMarket();


	/**
	 * Init the strategy 
	 * <p>
	 * Indicators and watermarks 
	 */
	public void init();
	
	
	/**
	 * Called for each Candle received on instrument 
	 * @param instrument an {@link InstrumentId}
	 * @param candle a {@link Candle}
	 */
	public void onCandle(InstrumentId instrument, Candle candle);

	
	/**
	 * Called for each candle at the begining of the Candle
	 * @param instrument
	 * @param candle 
	 */
	public void onCandleOpen(InstrumentId instrument, Candle candle);
	
	
	/**
	 * Called when a new position is opened 
	 * @param side the {@link TradeSide} BUY : Long SELL : Short  
	 * @param instrumentId the {@link InstrumentId}
	 */
	public void onPositionOpened(TradeSide side,InstrumentId instrumentId);


	/**
	 * Call to AddInstruments in the strategy 
	 */
	void initMarket();


	
	
	
}
