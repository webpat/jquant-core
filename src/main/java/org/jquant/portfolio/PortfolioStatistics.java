package org.jquant.portfolio;

import java.util.List;

import org.jquant.portfolio.Trade.TradeStatus;
import org.jquant.serie.DoubleSerie;

/**
 * Wrapper around the Portfolio class, to compute strategy statistics and display the strategy 
 * <b>Performance Summary</b>
 * <p>
 *  
 * @author patrick.merheb
 *@see Portfolio
 */
public class PortfolioStatistics {

	private final Portfolio ptf;
	
	private final List<Trade> transactions;
	
	private final DoubleSerie equityCurve;

	private double realizedPnL;

	private int nbWinningTrades;

	private int nbLosingTrades;

	private double averageLosingTrade;
	
	private double averageWinningTrade;

	private int nbTrades;

	private double averageTrade;

	private double largestWinningTrade;

	private double largestLosingTrade;

	private double grossLoss;

	private double grossProfit;

	private DrawDownData ddStats;
	
	
	public PortfolioStatistics(Portfolio ptf) {
		this.ptf = ptf;
		this.transactions = ptf.getTransactions();
		this.equityCurve = ptf.getEquityCurve();
		computeStatistics();
	}

	private void computeStatistics() {
		realizedPnL = 0;
		nbWinningTrades = 0;
		nbLosingTrades = 0;
		averageLosingTrade = 0;
		largestWinningTrade = 0;
		largestLosingTrade = 0;

		/*
		 * Trade statistics 
		 */
		
		for (Trade tr : transactions){
			if (TradeStatus.CLOSED.equals(tr.getStatus())){
			
				double pnl = tr.getProfitAndLoss();
				realizedPnL+= pnl;
				nbTrades ++;
				if (pnl>=0){
					nbWinningTrades++;
					averageWinningTrade += pnl;
					if (pnl >largestWinningTrade){
						largestWinningTrade = pnl;
					}
			
				}else{
					nbLosingTrades++;
					averageLosingTrade += pnl;
					if (pnl <largestLosingTrade){
						largestLosingTrade = pnl;
					}
				}
			}

		}
		grossLoss = averageLosingTrade;
		grossProfit = averageWinningTrade;
		averageLosingTrade /= nbLosingTrades;
		averageWinningTrade /= nbWinningTrades;
		averageTrade = realizedPnL/nbTrades;
		
		
		ddStats = StatisticsHelper.getDrawDownsData(equityCurve.getData());
		
	}

	/**
	 * 
	 * @return the underlying portfolio 
	 */
	public Portfolio getPortfolio() {
		return ptf;
	}

	public double getInitialWealth(){
		return ptf.getInitialWealth();
	}
	
	/**
	 * 
	 * @return CASH + Equity for the last day of 
	 */
	public double getFinalWealth(){
		
		double equity = equityCurve.getLast()!=null?equityCurve.getLast().getValue():0;
		
		return equity;
	}
	
	public double getGrossProfit(){
		return grossProfit;
		
	}
	
	public double getGrossLoss(){
		return grossLoss;
		
	}
	
	/**
	 * 
	 * @return The PnL of the Portfolio depending on the {@link Portfolio#getValuationMode()}
	 */
	public double getRealizedPnL(){
		
		return realizedPnL;
	}
	
	
	
	
	public int getTotalTrades(){
		return transactions.size();
		
	}
	
	public int getWinningTrades(){
		return nbWinningTrades;
		
	}
	
	public int getLosingTrades(){
		return nbLosingTrades;
		
	}
	
	public double getAverageWinningTrade(){
		return averageWinningTrade;
		
	}
	
	public double getAverageLosingTrade(){
		return averageLosingTrade;
		
	}
	
	public double getAverageTrade(){
		return averageTrade;
		
	}
	
	public double getLargestWinningTrade(){
		return largestWinningTrade;
		
	}
	
	public double getLargestLosingTrade(){
		return largestLosingTrade;
	}
	
	public DrawDownData getMaxDrawDown(){
		return ddStats;
	}
	
	
	
	/*
	 * TODO : remaining stats 
	 * 
	 */
	
	
	public int getMaxConsecutiveWinners(){
		return 0;
		
	}
	
	public int getMaxConsecutiveLosers(){
		return 0;
		
	}
	
	public double getOpenPositionsPnL(){
		return 0;
		
	}
	
	
}
