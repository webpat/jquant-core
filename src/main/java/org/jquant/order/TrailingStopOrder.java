package org.jquant.order;

import org.joda.time.DateTime;
import org.jquant.model.InstrumentId;

/**
 * A stop-loss order set at a percentage level below the market price - for a long position. 
 * The trailing stop price is adjusted as the price fluctuates. 
 * The trailing stop order can be placed as a trailing stop limit order, or a trailing stop market order. 
 * <p>
 * <a href="http://www.investopedia.com/terms/t/trailingstop.asp#ixzz1spJdxmwz">Read more</a> 
 * @author patrick.merheb
 *
 */
public class TrailingStopOrder extends Order {

	
	private double trigger;
	
	private double watermark;
	
	private final double percentageLevel;
	
	public TrailingStopOrder(OrderSide side, InstrumentId instrument,double percentageLevel,  double quantity, String text) {
		super(side, instrument, quantity, text);
		this.percentageLevel = percentageLevel;
		this.watermark = OrderSide.SELL.equals(side)?0:Double.MAX_VALUE;
	}
	
	public TrailingStopOrder(OrderSide side, InstrumentId instrument,double percentageLevel,  double quantity, String text,DateTime created) {
		super(side, instrument, quantity, text,created);
		this.percentageLevel = percentageLevel;
		this.watermark = OrderSide.SELL.equals(side)?0:Double.MAX_VALUE;
	}
	
	public void refresh(double lastClose){
		double  coef;
		if (OrderSide.SELL.equals(getSide())){
			coef = 1-percentageLevel;
		}else{
			coef = 1+percentageLevel;
		}
		
		trigger = lastClose*coef;
	}

	public double getTrigger() {
		return trigger;
	}

	public double getWatermark() {
		return watermark;
	}

	public void setWatermark(double watermark) {
		this.watermark = watermark;
	}
	
	

}
