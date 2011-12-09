package org.jquant.model;

import org.joda.time.DateTime;


/**
 * Quotation d'une option sur le marché 
 * Le strike K et le time to maturity T le type de l'option et son mode d'exercice 
 * @author patrick.merheb
 *
 */
public class OptionQuote extends Quote{

	private final OptionType 		optionType;
	private final OptionExercisingStyle 	exerciseType;
	
	/**
	 * {@link OptionQuote#getStrike()}
	 */
	private final double			strike;
	
	/**
	 * {@link OptionQuote#getTimeToMaturity()}
	 */
	private final double			timeToMaturity;
	
	
	
	public OptionQuote(DateTime date, double price, OptionType optionType, OptionExercisingStyle exerciseType, double strike, double timeToMaturity){
		super(date, price);
		this.optionType = optionType;
		this.exerciseType = exerciseType;
		this.strike = strike;
		this.timeToMaturity = timeToMaturity;
	}
	
	
	/**
	 * 
	 * @return La durée à courir de l'option en années (ex: 6 Mois = 0.5) 
	 */
	public double getTimeToMaturity() {
		return timeToMaturity;
	}

	
	/**
	 * Est ce un CALL un PUT ou une option type MID
	 * @return {@link OptionType}
	 */
	public OptionType getOptionType() {
		return optionType;
	}


	/**
	 * 
	 * @return Le strike de l'option (TODO: valeur absolue ou pourcentage ?)
	 */
	public double getStrike() {
		return strike;
	}


	public OptionExercisingStyle getExerciseType() {
		return exerciseType;
	}

}
