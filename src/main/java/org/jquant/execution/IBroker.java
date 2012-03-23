package org.jquant.execution;

import org.jquant.order.Order;
import org.jquant.order.OrderStatus;
import org.jquant.portfolio.Portfolio;

/**
 * Interface for all the execution providers 
 * @author patrick.merheb
 *
 */
public interface IBroker {

	/**
	 * Send an Order to the Broker using the BROKER protocol 
	 * @param order an {@link Order}
	 */
	public void sendOrder(Order order);
	
	/**
	 * Get the Order satus on the broker stack
	 * TODO : Map with Fix Protocol status  
	 * @param order an Order
	 * @return an {@link OrderStatus}
	 */
	public OrderStatus getOrderStatus(Order order);
	
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
	 * The callback for a Filling 
	 * @param filledOrder the Order that have been filled
	 */
	public void onOrderFilled(Order filledOrder);
	
	/**
	 * The callback for a Filling 
	 * @param filledOrder the Order that have been filled
	 */
	public void onOrderPartiallyFilled(Order filledOrder);
	
	/**
	 * The callback for a Filling 
	 * @param cancelledOrder the Order that have been filled 
	 */
	public void onOrderCancelled(Order cancelledOrder);
}
