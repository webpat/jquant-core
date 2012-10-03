package org.jquant.instrument;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.jquant.model.InstrumentId;
import org.jquant.model.StitchingMethod;
import org.jquant.serie.Candle;
import org.jquant.serie.Candle.CandleData;
import org.jquant.serie.CandleSerie;
import org.jquant.serie.DoubleSerie;
import org.jquant.serie.TimeValue;
import org.jquant.time.calendar.Periods;

/**
 * Warning : stitching, only works with Daily Candles 
 * 
 * @author patrick.merheb
 *
 */
public class GenericFuture extends Derivative {

	private CandleSerie serie;
	
	private final NavigableMap<Future,CandleSerie> strips = new TreeMap<Future, CandleSerie>(new Comparator<Future>() {

		@Override
		public int compare(Future f1, Future f2) {
			return f1.getLastDeliveryDate().compareTo(f2.getLastDeliveryDate());
		}
	}) ;
	
	public GenericFuture(InstrumentId symbol) {
		super(symbol);
	}

	public CandleSerie getSerie() {
		return serie;
	}

	public void add(Future fut,CandleSerie cs){
		strips.put(fut,cs);
	}

	public void stitch(StitchingMethod method) {

		
		if (strips.size()>0){

			serie = new CandleSerie(this.getId());
			constantReturns(strips);
		}
		
	}

	private void constantReturns(SortedMap<Future, CandleSerie> strips2) {
		
		/*
		 * Flatten the strips
		 */
//		DoubleSerie flatOpen = new DoubleSerie();
//		DoubleSerie flatHigh = new DoubleSerie();
//		DoubleSerie flatLow = new DoubleSerie();
		DoubleSerie flatClose = new DoubleSerie();
		for (CandleSerie cs :strips.descendingMap().values()){
			DoubleSerie closeReturns = cs.getDoubleSerie(CandleData.CLOSE).getReturns();
//			DoubleSerie openReturns = cs.getDoubleSerie(CandleData.OPEN).getReturns();
//			DoubleSerie highReturns = cs.getDoubleSerie(CandleData.HIGH).getReturns();
//			DoubleSerie lowReturns = cs.getDoubleSerie(CandleData.LOW).getReturns();
				
//			flatOpen.addAll(openReturns);
//			flatHigh.addAll(highReturns);
//			flatLow.addAll(lowReturns);
			flatClose.addAll(closeReturns);
			
		}

		
		
		/*
		 * Backward propagation 
		 */
		Candle last = strips.lastEntry().getValue().getLast();
		serie.addValue(last);
		Iterator<TimeValue> it = flatClose.reverseIterator();
		while (it.hasNext()){
			TimeValue f = it.next();
			DateTime dt = f.getDate();
			double previousReturn = flatClose.getDouble(dt);
			Candle c = new Candle(
						dt,
						Periods.ONE_DAY,
						last.getOpen()/(1.0+previousReturn),
						last.getHigh()/(1.0+previousReturn),
						last.getLow()/(1.0+previousReturn),
						last.getClose()/(1.0+previousReturn),
						0
						);
			serie.addValue(c);
			last = c;
		}
		
		
	}

}
