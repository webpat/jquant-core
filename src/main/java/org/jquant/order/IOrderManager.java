package org.jquant.order;

import org.jquant.model.InstrumentId;
import org.jquant.portfolio.Portfolio;
import org.jquant.serie.Candle;
import org.jquant.strategy.IStrategy;
import org.jquant.strategy.Strategy;

/**
 * Interface for all the execution providers 
 * @author patrick.merheb
 *
 */
public interface IOrderManager {

	/**
	 * Send an Order to the Broker using the BROKER protocol 
	 * @param order an {@link Order}
	 * @return The updated Order
	 */
	public Order sendOrder(Order order);
	
	
	/**
	 * Send an cancelling signal for a given Order 
	 * @param order the {@link Order} to cancel 
	 */
	public void cancelOrder(Order order);
	
	/**
	 * FIXME : vraiment pas content avec ça 
	 * Client Side Portfolio to update on Order Filling 
	 * @param ptf a {@link Portfolio}
	 */
	public void setPortfolio(Portfolio ptf);

	
	/**
	 * Add a stategy which is listening to the changes in positions 
	 * @param strategy a {@link Strategy}
	 */
	public void addStrategy(IStrategy strategy);


	/**
	 * This method is called when the Order is Filled by the Execution Provider 
	 * @param order the {@link Order} that has been Filled
	 */
	public void onOrderFilled(Order order);
	
	/**
	 * This method is called when the Order is Cancelled 
	 * @param order the {@link Order} that has been Cancelled
	 */
	public void onOrderCancelled(Order order);
	
	/**
	 * The MarketManager updates are propagated to the Order Manager for the pending instruments 
	 * @param instrument an {@link InstrumentId}
	 * @param candle a {@link Candle} for the instrument 
	 */
	public void onCandle(InstrumentId instrument, Candle candle);
	
	
}
