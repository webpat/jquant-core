package org.jquant.order;

import org.joda.time.DateTime;
import org.jquant.model.InstrumentId;
import org.jquant.serie.Candle.CandleData;

/**
 * <ul>
 * <li>Simulation MODE : Next Candle Market Order</li>
 * <li>Live Mode : see definition below</li>
 * </ul> 
 * <p>
 * An order that an investor makes through a broker or brokerage service to buy or sell an investment immediately at the best available current price. 
 * A market order is the default option and is likely to be executed because it does not contain restrictions on the buy/sell price or the timeframe in which the order can be executed. 
 * A market order is also sometimes referred to as an "unrestricted order."
 * <a href="http://www.investopedia.com/terms/m/marketorder.asp#ixzz1pA0MhHME">Read more</a> 
 * @author patrick.merheb
 *
 */
public class MarketOrder extends Order {

	
	private CandleData ohlc;
	
	/**
	 * Live Mode Constructor 
	 * @param side
	 * @param instrument
	 * @param quantity
	 * @param text
	 */
	public MarketOrder(OrderSide side, InstrumentId instrument, double quantity, String text) {
		super(side, instrument, quantity, text);
		
	}
	
	/**
	 * Backtest Mode Constructor 
	 * @param side
	 * @param instrument
	 * @param quantity
	 * @param ohlc
	 * @param text
	 * @param created
	 */
	public MarketOrder(OrderSide side, InstrumentId instrument, double quantity, CandleData ohlc, String text,DateTime created) {
		super(side, instrument, quantity, text,created);
		this.ohlc = ohlc;
		
	}

	/**
	 * show where execution takes place in the next candle (Open, High, LOW, CLOSE)  
	 * @return The {@link CandleData} of execution in Simulation MODE 
	 */
	public CandleData getOhlc() {
		return ohlc;
	}
	
	

}
