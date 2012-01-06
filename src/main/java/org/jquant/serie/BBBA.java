package org.jquant.serie;

import org.joda.time.DateTime;


/**
 * Best Bid Best Ask  for a particular {@link DateTime}
 * <p>
 * <b>Ask</b> The price a seller is willing to accept for a security, also known as the offer price.
 * <b>Bid</b> An offer made by an investor, a trader or a dealer to buy a security.
 * 
 * <p> This class is immutable
 * @author patrick.merheb
 *
 * TODO : amounts  
 *
 */
public final class BBBA extends AbstractTimeValue {

	private final double bestBid;
	private final double bestAsk;
	

	
	public BBBA(DateTime date,double bestBid, double bestAsk) {
		super(date);
		this.bestBid = bestBid;
		this.bestAsk = bestAsk;
	}

	public double getBestBid() {
		return bestBid;
	}


	public double getBestAsk() {
		return bestAsk;
	}

	/**
	 * {@link #getSpread()}
	 * @return the SPREAD = ASK -BID 
	 */
	public double getValue() {
		return getSpread();
	}
	
	/**
	 *  <b>Definition of 'Bid-Ask Spread'</b>
	 * The amount by which the ask price exceeds the bid. This is essentially the difference in price between the highest price that a buyer is willing to pay for an asset and the lowest price for which a seller is willing to sell it.
	 * Read more: http://www.investopedia.com/terms/b/bid-askspread.asp#ixzz1idoTriE8
	 * @return the SPREAD = ASK -BID 
	 */
	public double getSpread(){
		return bestAsk-bestBid;
	}

	@Override
	public String toString() {
		return "BBBA [bestBid=" + bestBid + ", bestAsk=" + bestAsk + "]";
	}
	
	@Override
	public BBBA clone(){
		
		BBBA dolly = new BBBA(this.getDate(),this.getBestBid(),this.getBestAsk());
		
		return dolly;
		
	}
	
	
	


}
