package org.jquant.portfolio;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.jquant.model.Currency;
import org.jquant.model.IInstrument;

/**
 * A grouping of financial assets such as stocks, bonds and cash equivalents
 * <p>
 * Technically, It's a collection of {@link Trade}, but you can deduce its composition from the past trades
 * <p>
 * The Portfolio is a stafull object, it's store the trades in the order they are received
 * 
 * TODO : LIFO, FIFO AVERAGE Inventory valuation 
 * @author patrick.merheb
 *
 */
public class Portfolio {
	
	private final Map<DateTime, Trade> transactions;
	
	private final Map<StockMovement,Double> inventory;
	
	private final Map<IInstrument, Double> positions;
	
	private final String name;
	
	private final Currency currency;
	
	private double cash;

	public Portfolio(String name, Currency currency) {
		super();
		this.name = name;
		this.currency = currency;
		transactions = new TreeMap<DateTime, Trade>();
		positions = new HashMap<IInstrument, Double>();
		inventory = new HashMap<StockMovement,Double>();
		cash = 0;
	}

	public String getName() {
		return name;
	}
	
	
	
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * Add a transaction to the portfolio
	 * @param trade
	 * @throws NotEnoughCashException 
	 */
	public void addTransaction(Trade trade) throws NotEnoughCashException{
		// Record the transaction
		transactions.put(trade.getTimestamp(), trade);
		
		switch (trade.getSide()) {
		case BUY:
			
			buy(trade);
			break;

		case SELL:
			sell(trade);
			break;
		} 
		
	}
	
	/**
	 * 
	 * @return Amount of cash available in this portfolio
	 */
	public double getCash() {
		return cash;
	}

	/**
	 * TODO : Maybe replace with a Cash transaction 
	 * Add some cash in the currency of the Portfolio 
	 * @param cash
	 */
	public void addCash(double cash) {
		this.cash += cash;
	}

	private void buy(Trade trade) throws NotEnoughCashException{
		
		IInstrument asset = trade.getInstrument();
		double quantity = trade.getQuantity();
		double unitPrice = trade.getPrice()/quantity;
		
		//check if enough cash
		if (getCash()<(trade.getPrice())){
			throw new NotEnoughCashException();
		}
		
		// update cash 
		this.cash -= trade.getPrice();
		
		// update positions
		if (positions.containsKey(asset)){
			double updatedPos = positions.get(asset) + quantity;
			positions.put(asset, updatedPos);
		}else{
			positions.put(asset, quantity);
		}
		//update inventory
//		StockMovement pa = new StockMovement(asset, unitPrice);
//		if (inventory.containsKey(pa)){
//			inventory.put(pa, inventory.get(pa)+quantity);
//		}else {
//			inventory.put(pa, quantity);
//		}
		
	}
	
	
	private void sell(Trade trade){
		
		IInstrument asset = trade.getInstrument();
		double quantity = trade.getQuantity();
		double unitPrice = trade.getPrice()/quantity;
		
		//TODO : update cash
		this.cash += trade.getPrice();
		
		//  update positions
		if (positions.containsKey(asset)){
			double updatedPos = positions.get(asset) - quantity;
			positions.put(trade.getInstrument(), updatedPos);
		}else{
			positions.put(trade.getInstrument(), -quantity);
		}
		//update inventory 
//		StockMovement pa = new StockMovement(asset, unitPrice);
//		if (inventory.containsKey(pa)){
//			double updatedQuantity = inventory.get(pa)-quantity;
//			if (updatedQuantity == 0){
//				inventory.remove(pa);
//				trade.setMouvement(StockMouvement.EXIT);
//			}else {
//				inventory.put(pa, updatedQuantity);
//				trade.setMouvement(StockMouvement.RELIEVE);
//			}
//		}else {
//			inventory.put(pa, -quantity);
//		}
		
		
		
	}
	
	

}
