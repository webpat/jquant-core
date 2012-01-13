package org.jquant.serie;

import org.joda.time.DateTime;
import org.jquant.exception.TimeSerieException;
import org.jquant.model.Symbol;

public interface ITimeSerie<T extends AbstractTimeValue> {

	/**
	 * @param timestamp un {@link DateTime}
	 * @return La valeur contenue à l'instant précisé 
	 */
	public abstract T getValue(DateTime timestamp);

	/**
	 * Return the element @ index position
	 * @param index
	 * @return the Time output content at index 
	 * @throws TimeSerieException 
	 */
	public abstract T get(int index) throws TimeSerieException;

	/**
	 * 
	 * @return Le {@link Symbol} de l'instrument associé à la timeSerie
	 */
	public abstract Symbol getSymbol();

}