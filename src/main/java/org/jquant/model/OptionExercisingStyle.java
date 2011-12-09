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
public enum OptionExercisingStyle {
	European,
	American,
	Bermudan,
	Unknown;

}
