package org.jquant.portfolio;

import org.jquant.model.InstrumentId;

/**
 * used in the  {@link Portfolio} inventory
 * <p>
 * natural key is asset/price/movementType
 * @author patrick.merheb
 */
public final class StockMovement {


	private final Trade trade;
	
	private double remainingQuantity;
	
	private final MovementType movement;
	
	
	public StockMovement(MovementType movement, Trade trade) {
		super();
		this.trade = trade;
		this.movement = movement;
		this.remainingQuantity = trade.getQuantity();
		
	}



	public Double getPrice() {
		return trade.getPrice();
	}



	public InstrumentId getInstrument() {
		return trade.getInstrument();
	}
	
	
	public Double getQuantity(){
		return trade.getQuantity();
	}
	

	public Trade getTrade() {
		return trade;
	}


	

	public MovementType getMovement() {
		return movement;
	}




	public double getRemainingQuantity() {
		return remainingQuantity;
	}



	public void setRemainingQuantity(double remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}



	@Override
	public String toString() {
		return "StockMovement [" + trade.getSide() + " " + getInstrument() + "@"+ getPrice() +"]";
	}

	public enum MovementType{
		LONG_ENTRY,LONG_EXIT,SHORT_ENTRY,SHORT_EXIT;
	}
	
	
}
