package items.base;

import exception.UnknownItemTypeException;
import items.weapon.Knife;
import items.weapon.Spear;
import items.weapon.Sword;

/**
 * The weapon class is {@link Item} that player can equip to have more attack
 */
public abstract class Weapon extends Item {

	/**
	 * Represent the attack value of weapon
	 */
	private int attack;

	/**
	 * The constructor of class
	 * 
	 * @param name        the name of armor
	 * @param description the description of armor
	 * @param defense     the defense value of armor
	 * @param spriteIndex the index in sprite map of armor
	 */
	public Weapon(String name, String description, int attack, int spriteIndex) {
		super(name, description, spriteIndex);
		setAttack(attack);
	}

	/**
	 * Getter for {@link #attack}
	 * 
	 * @return {@link #attack}
	 */
	public int getAttack() {
		return attack;
	}

	/**
	 * Setter for {@link #attack}
	 * 
	 * @param attack the new {@link #attack} value
	 */
	public void setAttack(int attack) {
		this.attack = attack;
	}

	/**
	 * The utility method for creating new {@link Weapon} by using input parameter
	 * 
	 * @param type        the type of weapon
	 * @param name        the name of weapon
	 * @param description the description of weapon
	 * @param defense     the defense value of weapon
	 * @param spriteIndex the index in sprite map of weapon
	 * @return new {@link Weapon} instance that match type with input
	 * @throws UnknownItemTypeException throws when {@link Weapon} type is not
	 *                                  recognized
	 */
	public static Weapon parseWeapon(String type, String name, String description, int attack, int spriteIndex)
			throws UnknownItemTypeException {
		if (type.equals("Knife")) {
			return new Knife(name, description, attack, spriteIndex);
		}

		if (type.equals("Spear")) {
			return new Spear(name, description, attack, spriteIndex);
		}

		if (type.equals("Sword")) {
			return new Sword(name, description, attack, spriteIndex);
		}

		throw new UnknownItemTypeException("%s weapon type is unknown".formatted(type));
	}

}
