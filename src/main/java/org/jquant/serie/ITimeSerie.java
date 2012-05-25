package org.jquant.serie;

import org.joda.time.DateTime;
import org.jquant.model.InstrumentId;

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
	 */
	public abstract T get(int index);

	/**
	 * 
	 * @return Le {@link InstrumentId} de l'instrument associé à la timeSerie
	 */
	public abstract InstrumentId getSymbol();

}