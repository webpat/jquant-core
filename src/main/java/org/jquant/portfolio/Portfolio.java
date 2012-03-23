package org.jquant.portfolio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.jquant.model.Currency;
import org.jquant.model.IInstrument;
import org.jquant.portfolio.StockMovement.MovementType;
import org.jquant.portfolio.Trade.TradeStatus;

/**
 * A grouping of financial assets such as stocks, bonds and cash equivalents
 * <p>
 * Technically, It's a collection of {@link Trade}, but you can deduce its composition from the past trades
 * <p>
 * The Portfolio is a stafull object, it store the trades and update its state as soon as trade are received
 * 
 * TODO : - AVERAGE Inventory valuation - getRealizedPnL() 
 * @author patrick.merheb
 *
 */
public class Portfolio {
	
	private final Map<DateTime, Trade> transactions;
	
	private final Map<IInstrument,List<StockMovement>> inventory;
	
	private final Map<IInstrument, Double> positions;
	
	private final String name;
	
	private final Currency currency;
	
	/**
	 * The inventory valuation of the Portfolio (LIFO,FIFO, AVERAGE)
	 */
	private final InventoryValuationMode valuationMode;
	
	
	private double cash;
	
	

	public Portfolio(String name, Currency currency) {
		super();
		this.name = name;
		this.currency = currency;
		transactions = new TreeMap<DateTime, Trade>();
		positions = new HashMap<IInstrument, Double>();
		inventory = new HashMap<IInstrument,List<StockMovement>>();
		this.valuationMode = InventoryValuationMode.FIFO;
		cash = 0;
	}
	
	public Portfolio(String name, Currency currency,InventoryValuationMode mode) {
		super();
		this.name = name;
		this.currency = currency;
		transactions = new TreeMap<DateTime, Trade>();
		positions = new HashMap<IInstrument, Double>();
		inventory = new HashMap<IInstrument,List<StockMovement>>();
		this.valuationMode = mode;
		cash = 0;
	}

	/**
	 * 
	 * @return the portfolio Name 
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * 
	 * @return The portfolio Main currency ( {@link Currency} )
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * Add a transaction to the portfolio
	 * @param trade
	 * @throws PortfolioException 
	 */
	public void addTransaction(Trade trade) throws PortfolioException{
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
	 * @return A Map containing all the instruments positions in the Portfolio
	 */
	public Map<IInstrument,Double> getPositions(){
		return positions;
	}
	
	/**
	 * Return the Position on an instrument 
	 * @param instrument
	 * @return the amount of instrument hold in the portfolio
	 */
	public double getPosition(IInstrument instrument){
		if (positions.containsKey(instrument)){
			return positions.get(instrument);
		}else {
			return 0;
		}
	}
	
	

	/**
	 * Add some cash in the currency of the Portfolio 
	 * @param cash
	 */
	public void addCash(double cash) {
		this.cash += cash;
	}

	private void buy(Trade trade) throws PortfolioException{
		
		IInstrument asset = trade.getInstrument();
		double quantity = trade.getQuantity();
		double unitPrice = trade.getPrice()/quantity;
		
		// update cash 
		if (getCash()<(trade.getPrice())){
			throw new PortfolioException("Not Enough cash");
		}
		this.cash -= trade.getPrice();
		
		// update positions
		if (positions.containsKey(asset)){
			double updatedPos = positions.get(asset) + quantity;
			positions.put(asset, updatedPos);
		}else{
			positions.put(asset, quantity);
		}
		//update inventory
		StockMovement sm = new StockMovement(MovementType.ENTRY,trade);
		if (inventory.containsKey(asset)){
			inventory.get(asset).add(sm);
		}else {
			List<StockMovement> sml = new ArrayList<StockMovement>();
			sml.add(sm);
			inventory.put(asset, sml);
		}
	
		
	}
	
	
	private void sell(Trade trade) throws PortfolioException{
		
		IInstrument asset = trade.getInstrument();
		double sellQuantity = trade.getQuantity();
		double unitPrice = trade.getPrice()/sellQuantity;
		
		//TODO : update cash
		this.cash += trade.getPrice();
		
		//  update positions
		if (positions.containsKey(asset)){
			double updatedPos = positions.get(asset) - sellQuantity;
			if (updatedPos < 0 ){
				
				throw new PortfolioException("Can't go short");
				
			}else {
			
				positions.put(trade.getInstrument(), updatedPos);
			}
		}else{
			throw new PortfolioException("Can't go short");
		}
		//update inventory 
		StockMovement sm = new StockMovement(MovementType.EXIT,trade);

		if (inventory.containsKey(asset)){
			inventory.get(asset).add(sm);
		}else {
			List<StockMovement> sml = new ArrayList<StockMovement>();
			sml.add(sm);
			inventory.put(asset, sml);
		}
		
		//update Trade information (P&L) 
		List<StockMovement> sml = getInventory(asset, MovementType.ENTRY,valuationMode);
		if (sml != null){
			Iterator<StockMovement> it = sml.iterator();
			double pnL = 0;
			while (sellQuantity >0 && it.hasNext()){
				StockMovement mvt = it.next();
				
				// Update P&L 
				pnL = pnL + (unitPrice - mvt.getPrice()/mvt.getQuantity())* Math.min(mvt.getRemainingQuantity(), sellQuantity);
				// Update remaining quantity
			
				
				// Mise à jour des remaining quantity dans la liste des StockMovment et de la Quantité restant à écouler
				if (sellQuantity >= mvt.getRemainingQuantity()){
					mvt.getTrade().setStatus(TradeStatus.CLOSED);
					mvt.setRemainingQuantity(0);
					sellQuantity = sellQuantity - mvt.getRemainingQuantity();
					
				}else{
					mvt.setRemainingQuantity(mvt.getRemainingQuantity()-sellQuantity);
					sellQuantity = 0 ;
				}
				
				
			}
			trade.setProfitAndLoss(pnL);
			trade.setStatus(TradeStatus.CLOSED);
			
		}
		
		
	}

	/**
	 * Return the Stock Movment List for an asset and a Movement type
	 * TODO : support the AVERAGE Inventory valuation Mode  
	 * @param asset the {@link IInstrument}
	 * @param mvt ENTRY or EXIT 
	 * @return a {@link StockMovement} references List or <code>null</code> if no asset in inventory
	 */
	private List<StockMovement> getInventory(IInstrument asset, MovementType mvt,InventoryValuationMode mode){
		
		if (inventory.get(asset)!= null){
			List<StockMovement> result = new ArrayList<StockMovement>();
			for (StockMovement sm : inventory.get(asset)){
				if (mvt.equals(sm.getMovement())&& sm.getRemainingQuantity()>0){
					result.add(sm);
				}
			}
			if (InventoryValuationMode.LIFO.equals(mode)){
				Collections.reverse(result);
			}
			return result;
		}
		return null;
		
	}
	
	/**
	 * 
	 * @return The PnL of the Portfolio depending on the {@link #getValuationMode()}
	 */
	public double getRealizedPnL(){
		// NOT Yet Implemented 
		throw new UnsupportedOperationException();
		
	}
	
	
	
	/**
	 * 
	 * @return The inventory valuation of the Portfolio (LIFO,FIFO, AVERAGE)
	 */
	public InventoryValuationMode getValuationMode() {
		return valuationMode;
	}




	/**
	 * Portfolio inventory valuation valuationMode 
	 * @author patrick.merheb
	 *
	 */
	public enum InventoryValuationMode{
		FIFO,LIFO,AVERAGE;
	}
	

}
