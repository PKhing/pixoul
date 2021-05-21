package exception;

import items.base.Armor;
import items.base.Potion;
import items.base.Weapon;

/**
 * The UnknownItemTypeException is {@link Exception} that will be thrown when
 * Item CSV contains invalid item type
 * 
 * @see Armor#parseArmor(String, String, String, int) parseArmor
 * @see Weapon#parseWeapon(String, String, String, int, int) parseWeapon
 * @see Potion#parsePotion(String, String, String, int, int, boolean, int) parsePotion
 */
public class UnknownItemTypeException extends Exception {

	private static final long serialVersionUID = 7006014797669787555L;

	/**
	 * The constructor of class. Initialize superclass with message
	 * 
	 * @param message The message of exception
	 */
	public UnknownItemTypeException(String message) {
		super(message);
	}
}
