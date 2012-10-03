package org.jquant.portfolio;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.jquant.model.Currency;
import org.jquant.model.IInstrument;
import org.jquant.model.InstrumentId;
import org.jquant.portfolio.StockMovement.MovementType;
import org.jquant.portfolio.Trade.TradeStatus;
import org.jquant.serie.Candle;
import org.jquant.serie.DoubleSerie;

/**
 * A grouping of financial assets such as stocks, bonds and cash equivalents
 * <p>
 * Technically, It's a collection of {@link Trade}, but you can deduce its composition from the past trades
 * <p>
 * The Portfolio is a stafull object, it store the trades and update its state as soon as trade are received
 * 
 * TODO : 
 * - AVERAGE Inventory valuation - getRealizedPnL()
 * - Assert |quantity| > 0  
 * @author patrick.merheb
 *
 */
public class Portfolio implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8310475844581589964L;

	/**
	 * List of Trades 
	 */
	private final List<Trade> transactions;
	
	/**
	 * Stock Movements History 
	 */
	private final Map<InstrumentId,Deque<StockMovement>> inventory;
	
	/**
	 * Portfolio breakdown 
	 */
	private final Map<InstrumentId, Double> positions;
	
	/**
	 * Portfolio Equity History
	 */ 
	private final DoubleSerie equityCurve;
	
	private final String name;
	
	private final Currency currency;
	
	/**
	 * The inventory valuation of the Portfolio (LIFO,FIFO, AVERAGE)
	 */
	private final InventoryValuationMode valuationMode;
	
	private final double initialWealth;
	
	private double cash;
	
	

	public Portfolio(String name, Currency currency, double initialCash) {
		super();
		this.name = name;
		this.currency = currency;
		transactions = new ArrayList<Trade>();
		positions = new HashMap<InstrumentId, Double>();
		inventory = new HashMap<InstrumentId,Deque<StockMovement>>();
		this.valuationMode = InventoryValuationMode.FIFO;
		cash = initialCash;
		initialWealth = initialCash;
		equityCurve = new DoubleSerie();
	}
	
	public Portfolio(String name, Currency currency,double initialCash,InventoryValuationMode mode) {
		super();
		this.name = name;
		this.currency = currency;
		transactions = new ArrayList<Trade>();
		positions = new HashMap<InstrumentId, Double>();
		inventory = new HashMap<InstrumentId,Deque<StockMovement>>();
		this.valuationMode = mode;
		cash = initialCash;
		initialWealth = initialCash;
		equityCurve = new DoubleSerie();
		
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
	public Map<InstrumentId,Double> getPositions(){
		return positions;
	}
	
	/**
	 * Return the Position on an instrument 
	 * @param instrument
	 * @return the amount of instrument hold in the portfolio
	 */
	public double getPosition(InstrumentId instrument){
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
	
	
	
	/**
	 * Marking to market of the portfolio 
	 * <p>Builds the equity curve 
	 * <p>
	 * TODO : Derivatives and exotic products
	 * @param time the {@link DateTime} of the marking to market 
	 * @param slice a "slice" of {@link InstrumentId} / {@link Candle}
	 */
	public void markToMarket(DateTime time,Map<InstrumentId, Candle> slice){
		double total = 0;
		
		
		if (positions.size()>0){

			for(Entry<InstrumentId,Double> pos : positions.entrySet()){
				if (slice.containsKey(pos.getKey())){
					// Still listed instrument
					total += pos.getValue()* slice.get(pos.getKey()).getClose();
				}else {
					// Unlisted instrument, take the last known valo from the StockMovements inventory
					StockMovement sm = getLastStockMovement(pos.getKey());
					if ( sm!=null ){
						double lastVal = sm.getUnitPrice();
						total += pos.getValue() * lastVal;
					}
				}
			}
			
			
		}
		total += getCash();
		equityCurve.add(time, total);
	}

	private void buy(Trade trade) throws PortfolioException{
		
		InstrumentId asset = trade.getInstrument();
		double quantity = trade.getQuantity();
		double currentPos = getPosition(asset);
		double unitPrice = trade.getPrice()/quantity;
		boolean split = false;
		
		// update cash 
		if (getCash()<(trade.getPrice())){
			throw new PortfolioException("Not Enough cash");
		}
		this.cash -= trade.getPrice();
		
		// update positions
		if (positions.containsKey(asset)){
			double updatedPos = positions.get(asset) + quantity;
			if (updatedPos==0){
				positions.remove(asset);
			}else {
				positions.put(asset, updatedPos);
			}
		}else{
			positions.put(asset, quantity);
		}
		//update inventory
		if (currentPos >=0){
			// Long Entry
			StockMovement sm = new StockMovement(MovementType.LONG_ENTRY,trade);
			addStockMovmentForAsset(asset, sm);
		}else if (currentPos <0){
			if (quantity > -currentPos ){
				// Short Exit + Long Entry
				Trade shortExitTrade = new Trade(trade.getSide(),trade.getInstrument(),-currentPos,unitPrice*(-currentPos),trade.getTimestamp());
				double pnL = getPnL(asset, MovementType.SHORT_ENTRY, quantity, unitPrice);
				shortExitTrade.setProfitAndLoss(pnL);
				shortExitTrade.setStatus(TradeStatus.CLOSED); // Débouclage
				StockMovement sm1 = new StockMovement(MovementType.SHORT_EXIT,shortExitTrade);
				addStockMovmentForAsset(asset, sm1);
				
				double delta = quantity + currentPos;
				Trade longEntryTrade = new Trade(trade.getSide(),trade.getInstrument(),delta,unitPrice*delta,trade.getTimestamp());
				StockMovement sm2 = new StockMovement(MovementType.LONG_ENTRY,longEntryTrade);
				addStockMovmentForAsset(asset, sm2);
				
				transactions.add(shortExitTrade);
				transactions.add(longEntryTrade);
				split = true;
				
			}else {
				// Short Exit 
				StockMovement sm = new StockMovement(MovementType.SHORT_EXIT,trade);
				addStockMovmentForAsset(asset, sm);
				//update Trade information (P&L) 
				double pnL = getPnL(asset, MovementType.SHORT_ENTRY, quantity, unitPrice);
				trade.setProfitAndLoss(pnL);
				trade.setStatus(TradeStatus.CLOSED); // Débouclage
			}
		}
		
		// Record the transaction
		if (!split){
			transactions.add(trade);
		}
		
	}
	
	
	private void sell(Trade trade) throws PortfolioException{

		InstrumentId asset = trade.getInstrument();
		double quantity = trade.getQuantity();
		double unitPrice = trade.getPrice()/quantity;
		double currentPos = getPosition(asset);
		boolean split = false;

		// update cash
		this.cash += trade.getPrice();

		//  update positions
		if (positions.containsKey(asset)){
			double updatedPos =  currentPos - quantity;
			if (updatedPos==0){
				positions.remove(asset);
			}else {
				positions.put(asset, updatedPos);
			}

		}else{
			positions.put(asset, - quantity);
		}
		
		//update inventory 
		if (currentPos > 0){
			if (quantity > currentPos){
				// long exit + short entry
				
				Trade longExitTrade = new Trade(trade.getSide(),asset,currentPos,unitPrice*currentPos,trade.getTimestamp());
				double pnL = getPnL(asset, MovementType.LONG_ENTRY, quantity, unitPrice);
				longExitTrade.setProfitAndLoss(pnL);
				longExitTrade.setStatus(TradeStatus.CLOSED); // Débouclage
				StockMovement sm1 = new StockMovement(MovementType.LONG_EXIT,longExitTrade);
				addStockMovmentForAsset(asset, sm1);
				
				double delta = quantity - currentPos;
				Trade shortEntryTrade = new Trade(trade.getSide(),asset,delta,unitPrice*delta,trade.getTimestamp());
				StockMovement sm2 = new StockMovement(MovementType.SHORT_ENTRY,shortEntryTrade);
				addStockMovmentForAsset(asset, sm2);
				
				transactions.add(longExitTrade);
				transactions.add(shortEntryTrade);
				split = true;
			}else {
				// long exit
				StockMovement sm = new StockMovement(MovementType.LONG_EXIT,trade);
				addStockMovmentForAsset(asset,sm);	
				//update Trade information (P&L) 
				double pnL = getPnL(asset, MovementType.LONG_ENTRY, quantity, unitPrice);
				trade.setProfitAndLoss(pnL);
				trade.setStatus(TradeStatus.CLOSED); // Débouclage
				
			}

		}else if (currentPos <= 0){
			// short entry (reinforcing short position) 
			StockMovement sm = new StockMovement(MovementType.SHORT_ENTRY,trade);
			addStockMovmentForAsset(asset,sm);
		}


		// Record the transaction
		if (!split){
			transactions.add(trade);
		}
	}

		
	

	private void addStockMovmentForAsset(InstrumentId asset,StockMovement sm) {
		if (inventory.containsKey(asset)){
			inventory.get(asset).add(sm);
		}else {
			ArrayDeque<StockMovement> sml = new ArrayDeque<StockMovement>();
			sml.add(sm);
			inventory.put(asset, sml);
		}
	}

	
	/**
	 * 
	 * @param asset l'instrument 
	 * @param mvtType type de mouvement qui contre balance 
	 * @param qty Quantité à écouler 
	 * @param unitPrice le prix du trade 
	 * @return le P&L sur le trade 
	 */
	private double getPnL(InstrumentId asset, MovementType mvtType, double qty, double unitPrice){
		List<StockMovement> sml = getInventory(asset, mvtType,valuationMode);
		double pnL = 0;
		double mvtPnL = 0;
		if (sml != null){
			Iterator<StockMovement> it = sml.iterator();

			while (qty >0 && it.hasNext()){
				StockMovement mvt = it.next();

				mvtPnL = (unitPrice - mvt.getPrice()/mvt.getQuantity());
				
				if (MovementType.SHORT_ENTRY.equals(mvtType)){
					mvtPnL*=-1;
				}
				// Update P&L 
				pnL = pnL + mvtPnL * Math.min(mvt.getRemainingQuantity(), qty);
				
				// Update remaining quantity
				if (qty >= mvt.getRemainingQuantity()){
					mvt.getTrade().setStatus(TradeStatus.CLOSED);
					qty = qty - mvt.getRemainingQuantity();
					
					// the StockMovment will be removed from the Queue next P&L calculation
					mvt.setRemainingQuantity(0);
					

				}else{
					mvt.setRemainingQuantity(mvt.getRemainingQuantity()- qty);
					qty = 0 ;
				}


			}
			
		}
		return pnL;	
	}
	/**
	 * Return the Stock Movement List for an asset and a Movement type
	 * TODO : support the AVERAGE Inventory valuation Mode  
	 * @param asset the {@link IInstrument}
	 * @param mvt ENTRY or EXIT 
	 * @return a {@link StockMovement} references List or <code>null</code> if no asset in inventory
	 */
	private List<StockMovement> getInventory(InstrumentId asset, MovementType mvt,InventoryValuationMode mode){
		
		if (inventory.get(asset)!= null){
			
			Deque<StockMovement> deq = inventory.get(asset);
			
					
			List<StockMovement> result = new ArrayList<StockMovement>();
			Iterator<StockMovement> it = InventoryValuationMode.LIFO.equals(mode)?deq.descendingIterator():deq.iterator();
			
			while(it.hasNext()){
				
				StockMovement sm = it.next();
				
				if (sm.getRemainingQuantity()==0){
					it.remove();
					continue;
				}
				
				if (mvt.equals(sm.getMovement())){
					result.add(sm);
				}
			}
			
			return result;
		}
		return null;
		
	}
	
	private StockMovement getLastStockMovement(InstrumentId asset){
		
		Deque<StockMovement> deq = inventory.get(asset);
		
		if (deq == null)
			return null;
		
		return deq.peekLast();
	}
	
	/**
	 * 
	 * @return The inventory valuation of the Portfolio (LIFO,FIFO, AVERAGE)
	 */
	public InventoryValuationMode getValuationMode() {
		return valuationMode;
	}


	/**
	 * 
	 *  @return the initial Cash amount 
	 */
	public double getInitialWealth() {
		return initialWealth;
	}


	/**
	 * 
	 * @return The {@link Trade} collection
	 */
	public List<Trade> getTransactions() {
		return transactions;
	}

	


	public DoubleSerie getEquityCurve() {
		return equityCurve;
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
