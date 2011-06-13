/****

    activequant - activestocks.eu

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.

	
	contact  : contact@activestocks.eu
    homepage : http://www.activestocks.eu

****/
package org.jquant.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jquant.exception.NotEnoughDataException;
import org.jquant.serie.CandleSerie;



/**
 * @TODO desc<br>
 * <br>
 * <b>History:</b><br>
 *  - [03.05.2007] Created (Ulrich Staudinger)<br>
 *  - [06.05.2007] Added min, max, priceRange, MEMA, vola (Erik Nijkamp)<br>
 *  - [10.05.2007] added wma, sma, rsi, roc, normalizeArray, scale from ccapi2. (Ulrich Staudinger)<br>
 *  - [14.11.2007] Added priceSlope (Ulrich Staudinger)<br>
 *  - [23.11.2007] Adding PivotPoints based on Dan O'Rourke's contribution (Ulrich Staudinger)<br>
 * <br>
 *
 *  @author Ulrich Staudinger
 *  @author Erik Nijkamp
 */
public class FinancialLibrary {
	
    protected final static Logger log = Logger.getLogger(FinancialLibrary.class);
	
	public static double max(double[] values) {
		double max = Double.MIN_VALUE;
		for(double value: values) {
			if(value > max)
				max = value;
		}
		return max;
	}
	
	public static double min(double[] values) {
		double min = Double.MAX_VALUE;
		for(double value: values) {
			if(value < min)
				min = value;
		}
		return min;
	}
	
    public static double max(double[] values, int start, int end) {
    	double[] sublist = new double[end-start+1];
    	System.arraycopy(values, start, sublist, 0, sublist.length);
        return max(sublist);
    }
    
    public static double min(double[] values, int start, int end) {
    	double[] sublist = new double[end-start+1];
    	System.arraycopy(values, start, sublist, 0, sublist.length);
        return min(sublist);
    }

	public static double[] bollinger(int n, int deviations, double[] vals,
			int skipdays) {
		double[] value = new double[3];

		double centerband = SMA(n, vals, skipdays);

		double t2 = deviation(n, vals, skipdays);

		double upper = centerband + (deviations * t2);
		double lower = centerband - (deviations * t2);

		value[2] = upper;
		value[1] = centerband;
		value[0] = lower;

		return value;
	}

	public static double deviation(int n, double[] vals, int skipdays) {
		double centerband = SMA(n, vals, skipdays);

		double t1 = 0.0;

		for (int i = 0; i < n; i++) {
			t1 += ((vals[i + skipdays] - centerband) * (vals[i + skipdays] - centerband));
		}

		double t2 = Math.sqrt(t1 / n);

		return t2;
	}

	
	
	
    
    /**
     * returns the minimum value of two doubles.
     * 
     * @param v1
     * @param v2
     * @return minimum of a value of two doubles.
     */
    public static double minOf(double v1, double v2) {
        if (v1 <= v2)
            return v1;
        if (v2 <= v1)
            return v2;
        return v1;
    }

    /**
     * returns the minimum value of three doubles
     * 
     * @param v1
     * @param v2
     * @param v3
     * @return the minimum value of three doubles
     */
    public static double minOf(double v1, double v2, double v3) {
        if (v1 <= v2 && v1 <= v3)
            return v1;
        if (v2 <= v1 && v2 <= v3)
            return v2;
        if (v3 <= v1 && v3 <= v2)
            return v3;
        return v1;
    }
    
    
    /**
     * returns the max value out of three.
     */
    public static double maxOf(double v1, double v2, double v3) {
        if (v1 >= v2 && v1 >= v3)
            return v1;
        if (v2 >= v1 && v2 >= v3)
            return v2;
        if (v3 >= v1 && v3 >= v2)
            return v3;
        return v1;
    }
    
    public static double maxOf(double v1, double v2) {
        if (v1 >= v2 )
            return v1;
        else return v2;
    }
    
	public static double SMA(int period, double[] vals, int skipdays) {

		double value = 0.0;
		// debugPrint("SMA("+period+") for "+candles.size()+ " skipd:
		// "+skipdays);

		for (int i = skipdays; i < (period + skipdays); i++) {
			value += vals[i];
		}

		value /= period;

		return value;
	}

	/**
	 * returns the linearly weighted moving average.
	 * 
	 * @param period
	 * @param candles
	 * @param skipdays
	 * @return the wma
	 */
	public static double WMA(int period, double[] vals, int skipdays) {

		double numerator = 0.0;

		int weight = period;
		for (int i = skipdays; i < (period + skipdays); i++) {
			numerator += vals[i] * weight;
			weight--;
		}

		int denominator = period * (period + 1) / 2;
		double value = numerator / denominator;

		return value;
	}

	
    /**
     * returns the slope between two timepoints
     * 
     * @param n
     * @param candles
     * @param skipdays
     * @return the slope
     */
    public static double slope(int n, double[] values, int skipdays) {
        double value = 0.0;
        value = (values[skipdays] - values[n+skipdays]) / n;
        return value;
    }
	
    
    /**
     * calculates the slope, relative to the price and scales it by 100.
     * @param n
     * @param values
     * @param skipdays
     * @return
     */
    public static double priceSlope(int n, double[] values, int skipdays) {
        double value = 0.0;
        value = (values[skipdays] - values[n+skipdays]) / values[skipdays] * 100;
        return value;
    }
	
    
    
    /**
     * returns a SMA smoothed slope
     * 
     * @param n
     * @param smoothingfactor
     * @param candles
     * @param skipdays
     * @return smoother slope
     */
    public static double smoothedSlope(int n, int smoothingunits, double[] vals, int skipdays) {
        double value = 0.0;
        double[] values = new double[smoothingunits];
        for (int i = 0; i < (smoothingunits); i++) {
            values[i] = slope(n, vals, skipdays + i);
        }
        value = SMA(smoothingunits, values, 0);
        return value;
    }
    
	/**
	 * returns the exponential moving average <br/> see
	 * http://www.quotelinks.com/technical/ema.html
	 * 
	 * @param n
	 * @param candles
	 * @param skipdays
	 * @return the exponential moving average
	 */
	public static double EMA(int n, double[] vals, int skipdays) {
		double value = 0;

		double exponent = 2 / (double) (n + 1);

		value = vals[vals.length - 1] * exponent;

		for (int i = vals.length - 1; i > skipdays - 1; i--) {

			value = (vals[i] * exponent) + (value * (1 - exponent));

		}

		return value;
	}
	
    public static double volatilityIndex(int p1, int p2, double[][] ohlc, int skipdays) {
		return volatilityIndex(p1, p2, ohlc[0], ohlc[1], ohlc[2], ohlc[3],
				skipdays);
	}
	
    /**
     * calculates the volatility Index, returns the trend following system
     * working with SAR points. indicator requires at least 100 candles !
     * 
     * @param p1
     *            the factor
     * @param p2
     *            the periods to work on.
     * @param candles
     *            this are the input candles.
     * @param skipdays
     *            this parameter specifies how many days to skip.
     * @return the volatility Index
     */
    public static double volatilityIndex(int p1, int p2, double[] opens,
			double[] highs, double[] lows, double[] closes, int skipdays) {

        boolean first_run_vlx = true;
        boolean position_long = true;
        double sip = 0, sar = 0, next_sar = 0, smoothed_range = 0;

        int max = closes.length - skipdays - 2;
        if (max > 200)
            max = 200;

        for (int i = max; i > skipdays - 1; i--) {

            double value = closes[i];
            smoothed_range = MEMA(p2, priceRange(opens, highs, lows, closes, i), 0);
            double atr = smoothed_range * p1;

            if (first_run_vlx && smoothed_range != 0) {
                first_run_vlx = false;
                sip = max(highs, i, i + p2);
                next_sar = sip - atr;
                sar = next_sar;
            } else {
                sar = next_sar;
                if (position_long) {
                    if (value< sar) {
                        position_long = false;
                        sip = value;
                        next_sar = sip + (smoothed_range * p1);
                    } else {
                        position_long = true;
                        sip = (value > sip) ? value : sip;
                        next_sar = sip - (smoothed_range * p1);
                    }
                } else {
                    if (value > sar) {
                        position_long = true;
                        sip = value;
                        next_sar = sip - (smoothed_range * p1);
                    } else {
                        position_long = false;
                        sip = (value < sip) ? value : sip;
                        next_sar = sip + (smoothed_range * p1);
                    }
                }
            }
        }
        return sar;
    }
    
    /**
     * calculates the MEMA
     * 
     * @param period
     * @param candles
     * @param skipdays
     * @return the MEMA
     */
    public static double MEMA(int period, double[] values, int skipdays) {
        double mema = 0.0;
        double smoothing = 1;

        if (period != 0) {
            smoothing = 1 / (double) period;
        }

        int max = values.length;
        if (max > 600 + skipdays + 2 + period) {
            max = 500 + skipdays + 2 + period;
        } else {
            max = values.length - skipdays - 1 - period;
        }
        for (int i = max; i >= skipdays; i--) {
            double value = values[i];
            if (i == max) {
                // ok, beginning of calculation
                mema = SMA(period, values, i);
            } else {
                mema = (smoothing * value) + ((1 - smoothing) * mema);
            }
        }
        return mema;
    }
    
    /**
     * returns the price range R as an array of doubles.
     * 
     * @return the price range
     */
    public static double[] priceRange(double[] opens, double[] highs, double[] lows,
			double[] closes, int skipdays) {
    	List<Double> results = new ArrayList<Double>();
        boolean first_run_range = true;
        int max = opens.length - 1;
        if (max > 200)
            max = 200;
        for (int i = max; i > skipdays - 1; i--) {
            double result = 0.0;
            if (first_run_range) {
                first_run_range = false;
                result = highs[i] - lows[i];
            } else {
                double v1 = highs[i] - lows[i];
                double v2 = highs[i] - closes[i+1];
                double v3 = closes[i+1] - lows[i];

                if (v1 >= v2 && v1 >= v3) {
                	result = v1;
                } else if (v2 >= v1 && v2 >= v3) {
                	result = v2;
                } else if (v3 >= v1 && v3 >= v2) {
                	result = v3;
                }
            }
            results.add(0, result);
        }
        return unboxDoubles(results);
    }
    
    private static double[] unboxDoubles(List<Double> doubles) {
    	double[] array = new double[doubles.size()];
    	for(int i = 0; i < array.length; i++) {
    		array[i] = doubles.get(i);
    	}
    	return array;
    }
    
    /**
     * returns a normalized copy of the input array
     */
    public static double[] normalizeArray(double[] in) {
        double min = min(in);
        double max = max(in);
        double[] ret = new double[in.length];

        for (int i = 0; i < in.length; i++) {
            ret[i] = (in[i] - min) / (max - min);
        }
        return ret;
    }

    /**
     * calculates the plain mean of an array of doubles
     * 
     * @param vals
     * @return plain mean of an array of doubles
     */
    public static double mean(double[] vals) {
        double v = 0;
        for (int i = 0; i < vals.length; i++) {
            v += vals[i];
        }
        v /= vals.length;
        return v;
    }
    

    /**
     * returns the rate of change
     * 
     * @param n
     * @param candles
     * @param skipdays
     * @return the rate of change
     */
    public static double ROC(int n, double[] vals, int skipdays) {
        double value = 0.0;
        double v0 = vals[skipdays];
        double v1 = vals[skipdays+n];

        value = (v0-v1) / v0 * 100;

        return value;
    }


    /**
     * returns the RSI
     * 
     * @param n
     * @param values
     * @param skipdays
     * @return the RSI
     */
    public static double RSI(int n, double[] vals, int skipdays) {
        double U = 0.0;
        double D = 0.0;

        for (int i = 0; i < n; i++) {
            double v0 = vals[skipdays + i];
            double v1 = vals[skipdays + i+1];
            
            double change = v0 - v1;

            if (change > 0) {
                U += change;
            } else {
                D += Math.abs(change);
            }
        }

        // catch division by zero
        if(D == 0 || (1 + (U / D)) == 0) {
        	log.warn("Division by zero");
        	return 0.0;
        }

        return 100 - (100 / (1 + (U / D)));
    }

    /**
     * this function does scale the input parameters into the values -1...1. Can
     * be useful for various aspects.
     * 
     * @param in
     *            the input values
     * @return the input values in the range -1 .. 1
     */
    public static double[] scale(double[] in) {
        double[] ret = new double[in.length];

        for (int i = 0; i < in.length; i++) {
            ret[i] = -1 + 2 * in[i];
        }
        return ret;
    }
    
    /**
     * returns the logarithmic change value for double[0] and double[1]. 
     * double[0] must be the more recent value.
     *  
     * @param in
     * @return
     * @throws NotEnoughDataException
     */
    public static double logChange(double[] in) throws NotEnoughDataException {
        if(in.length<2)throw new NotEnoughDataException("Too few inputs.");
        double logReturn = Math.log(in[0]/in[1]);
        return logReturn; 
    }
 
    /**
     * checks if something is a hammer or not.
     * @param series
     * @param filterPeriod
     * @param multiplier
     * @param position
     * @return
     * @throws NotEnoughDataException 
     */
    public static boolean isHammer(CandleSerie series, int filterPeriod, double multiplier, int position) throws NotEnoughDataException{
		//
    	
    	// 
    	double xaverage = EMA(filterPeriod, series.getCloses(), position);
    
    	
		if(series.get(position).getClose() < xaverage){
    		
			
			
			double open = series.get(position).getOpen();
			double high = series.get(position).getHigh();
			double low = series.get(position).getLow();
			double close = series.get(position).getClose();
			
			double bodyMin = minOf(close, open);
			double bodyMax = maxOf(close, open);
			double candleBody = bodyMax - bodyMin; 
			double rangeMedian = (high + low) * 0.5;
			double upShadow = high - bodyMax; 
			double downShadow = bodyMin - low; 
			
			boolean isHammer = (open!=close) && (bodyMin > rangeMedian) && 
				(downShadow > (candleBody * multiplier)) && (upShadow < candleBody); 
			
				return isHammer;
			
		}
		else {
			return false;
		}
		
	}

   
    

    /**
     * Calculates Pivot Points, contributed by Dan O'Rourke. 
     * @param open
     * @param high
     * @param low
     * @param close
     * @param positionInTime
     * @return
     */
    public double[] getPivotPoints(double[] open, double[] high, double[] low, double[] close, int pos) {
	    double[] ret = new double[7];
	    
	    ret[3] = (high[pos] + low[pos] + close[pos]) / 3;
	    // r1 
	    ret[2] = (ret[2] * 2) - low[pos];
	    // r2
	    ret[1] = (ret[2] + high[pos] - low[pos]);
	    // r3
	    ret[0] = (ret[1] + high[pos] - low[pos]);
	    
	    // s1
	    ret[4] = (ret[3] * 2) - high[pos];
	    // s2
	    ret[5] = (ret[4] - high[pos] + low[pos]);
	    // s3
	    ret[6] = (ret[5] - high[pos] + low[pos]);
	    
	    return ret;
    }

    /**
     * return realized Variance for a serie of close with a RMS (root mean squared) measure
     * @param close
     * @return realized Variance
     */
    public static double getRealizedVariance(double[] close) {
    	double realizedVariance = 0.0;
    	double varOfLogReturns = 0.0;
    	int nbPrices = close.length;
    	for (int i=1;i<nbPrices;i++) {
    		double logReturn = Math.log(close[i]/close[i-1]);
    		varOfLogReturns += logReturn*logReturn;
    	}
    	realizedVariance = 252/(nbPrices-1)*varOfLogReturns;
    	return realizedVariance;
    }
    
    /**
     * return realized Volatility for a serie of close with a RMS (root mean squared) measure
     * @param close
     * @return realized volatility
     */
    public static double getRealizedVolatility(double[] close) {
    	return Math.sqrt(getRealizedVariance(close));
    }
       
}
