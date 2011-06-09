package org.jquant.model;

/**
 * Mode d'exercice de l'option
 * <ul>
 * <li>Européenne</li>
 * <li>Américaine (Vanille)</li>
 * <li>Bermudan</li>
 * </ul>
 * @author patrick.merheb
 *
 */
public enum ExerciseType {
	European,
	American,
	Bermudan,
	Unknown;

    public static ExerciseType fromLouxorCode(String louxorCode){
		
		if ("European".compareTo(louxorCode)==0)
			return European;
		else if ("American".compareTo(louxorCode)==0)
			return American;
		else
			return Unknown;
    }
}
