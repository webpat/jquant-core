package org.jquant.instrument;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.jquant.core.BaseInstrument;
import org.jquant.core.Candle;
import org.jquant.core.MICMarketPlace;
import org.jquant.core.Quote;
import org.jquant.core.Symbol;
import org.jquant.exception.NotEnoughDataException;
import org.jquant.instrument.rate.InterestCalculation;
import org.jquant.instrument.rate.InterestType;
import org.jquant.serie.QuoteSerie;
import org.jquant.time.TimeFrame;
import org.jquant.time.calendar.CalendarUtils;
import org.jquant.time.daycounter.DayCounter;


/**
 * 
 * @author patrick.merheb
 *
 */
public class TimeDeposit extends BaseInstrument {

	private final DateTime startDate = null;
	private final DateTime expirationDate = null;
	private DayCounter dayCount;
	private InterestCalculation interestCalculation;
	private Double interestRate;
	private Double nominal;
	private InterestType interestType;
	private RateIndex underlying;
	private Double spread;
	private TimeFrame underlyingPeriod;

	public TimeDeposit(Symbol symbol, DateTime expirationDate, 
			DayCounter dayCount, InterestCalculation interestCalculation) {
		super(symbol, MICMarketPlace.NO_MIC);
		// TODO Auto-generated constructor stub
	}
	
	public TimeDeposit(Symbol symbol) {
		super(symbol, MICMarketPlace.NO_MIC);
	}
	
//	public boolean loadInstrument() {
//		TickerTimeDepositDAO dao = JQuantApplicationContext.getTickerTimeDepositDAO();
//		TickerTimeDepositTO tickerTimeDeposit = dao.selectForInternalCode(symbol.toString(),this.getMarketDataProvider());
//		if (tickerTimeDeposit==null) return false;
//		this.interestCalculation = tickerTimeDeposit.getPaymentType();
//		this.dayCount = tickerTimeDeposit.getDayCountBasis();
//		this.expirationDate = tickerTimeDeposit.getRedemptionDate();
//		this.startDate = tickerTimeDeposit.getInceptionDate();
//		this.interestRate = tickerTimeDeposit.getInterestRate();
//		if (this.interestRate==null) this.interestRate = 0.;
//		else this.interestRate /= 100.;
//		if (this.interestCalculation==InterestCalculation.PRE_JUDGEMENT)
//			this.nominal = tickerTimeDeposit.getFinalPrice();
//		else if (this.interestCalculation==InterestCalculation.POST_JUDGEMENT)
//			this.nominal = tickerTimeDeposit.getInitialPrice();
//		else 
//			return false;
//		this.interestType = tickerTimeDeposit.getType();
//		this.spread = tickerTimeDeposit.getSpread();
//		if (this.spread == null) this.spread = 0.;
//		else this.spread /= 100.;
//		// si indexe sur un taux
//		if (this.interestType==InterestType.REVISABLE||this.interestType==InterestType.VARIABLE) {
//			MarketDataProvider underProvider = MarketDataProvider.BLOOMBERG;
//			InstrumentFactory factory=JQuantApplicationContext.getInstrumentFactory();
//			Symbol underlyingSymbol = new Symbol(tickerTimeDeposit.getUnderlyingInternalCode());
//			if (tickerTimeDeposit.getUnderlyingMaturity()==null) return false;
//			int unerlyingMaturity = tickerTimeDeposit.getUnderlyingMaturity().intValue();
//			TimeUnit timeUnit = TimeUnit.fromCode(tickerTimeDeposit.getUnderlyingTimeUnit().charAt(0));
//			TimeFrame timeFrame = new TimeFrame(unerlyingMaturity, timeUnit);
//			this.underlying = factory.getRateIndex(underlyingSymbol,underProvider,timeFrame);
//		}
//		return true;
//	}
	
	public double getMarkToMarket(DateTime evaluationDate) throws NotEnoughDataException {
		double markToMarket = 0.0;
		switch (getInterestType()) {
		case FIXED:
			markToMarket = getMTMforFixedInterest(evaluationDate);
			break;
		case VARIABLE:
			markToMarket = getMTMforVariableInterest(evaluationDate);
			break;
		case REVISABLE:
			markToMarket = getMTMforRevisableInterest(evaluationDate);
			break;
		}
		
		return markToMarket;
	}
	
	private double getMTMforFixedInterest(DateTime evaluationDate) {
		double markToMarket = 0.0;
		switch (getInterestCalculation()) {
		case PRE_JUDGEMENT:
			markToMarket = nominal / (1+interestRate*
					dayCount.calculateYearFraction(evaluationDate, expirationDate));
			break;
		case POST_JUDGEMENT:
			markToMarket = nominal * (1+interestRate*
					dayCount.calculateYearFraction(startDate, evaluationDate));
			break;
		}

		return markToMarket;
	}
	
	private double getMTMforVariableInterest(DateTime evaluationDate) throws NotEnoughDataException {
		double markToMarket = nominal;
		DateTime t = startDate;
		double rate = 0.0;
		while (t.compareTo(evaluationDate)<0) {
			t = t.plusDays(1);
			// recuperation de n'eonia en t
			Candle candle = underlying.getCandles().getValue(t);
			// no rate for this date
			
			//if no rate for the evaluation date : the rate is not in louxor
			if (candle == null&&t.compareTo(evaluationDate)==0) {
				throw new NotEnoughDataException("The rate  has no price for the "+t.toString());
			}
			
			if (candle == null) {
				// les interets sont simples
				markToMarket += (rate+spread)/360.;
			}
			else {
				// les interets sont capitalis�s
				rate = candle.getValue()/100.;
				markToMarket = markToMarket*(1+(rate+spread)/360.);
			}
		}
		
		return markToMarket;
	}
	
	private double getMTMforRevisableInterest(DateTime evaluationDate) {
		double markToMarket = nominal;
		
		DateTime t = startDate;
		DateTime nextDate = CalendarUtils.getLastWorkDay(t, underlyingPeriod, MICMarketPlace.NO_MIC);
		double rate = 0.0;
		while (nextDate.compareTo(evaluationDate)<=0) {
			Candle candle = underlying.getCandles().getValue(t);
			if (candle == null) {
				
			}
			else {
				rate = candle.getValue();
			}
			int nbDaysToNextDate = Days.daysBetween(t, nextDate).getDays()-1;
			markToMarket *= 1+(rate+spread)*nbDaysToNextDate/360.;
			t = nextDate;
			nextDate = CalendarUtils.getLastWorkDay(t, underlyingPeriod, MICMarketPlace.NO_MIC);
		}
		if (t.compareTo(evaluationDate)!=0) {
			int nbDaysToNextDate = Days.daysBetween(t, evaluationDate).getDays()-1;
			markToMarket *= 1+(rate+spread)*nbDaysToNextDate/360.;
		}
		return markToMarket;	
	}
	
	//TODO Renvoyer une exception si interet n'est pas fix�
	public QuoteSerie getAllMarkToMarket() throws NotEnoughDataException {
		QuoteSerie prices = new QuoteSerie();
		DateTime date = startDate;
		while (date.compareTo(expirationDate)<=0) {
			Quote quote = new Quote(date, getMarkToMarket(date));
			
			prices.addValue(date, quote);
			date = date.plusDays(1);
		}
		return prices;
	}

	public DayCounter getDayCount() {
		return dayCount;
	}

	public DateTime getExpirationDate() {
		return expirationDate;
	}

	public InterestCalculation getInterestCalculation() {
		return interestCalculation;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public double getNominal() {
		return nominal;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public InterestType getInterestType() {
		return interestType;
	}
}
