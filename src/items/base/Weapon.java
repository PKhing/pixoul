package items.base;

import entity.Player;
import exception.UnknownItemTypeException;
import logic.Sprites;

/**
 * The weapon class is {@link Item} that player can equip to have more attack
 */
public class Weapon extends Item {

	/**
	 * Represent the attack value of weapon
	 */
	private int attack;

	private int spriteSymbol;

	/**
	 * The constructor of class
	 * 
	 * @param name        the name of armor
	 * @param description the description of armor
	 * @param defense     the defense value of armor
	 * @param spriteIndex the index in sprite map of armor
	 */
	public Weapon(String name, String description, int attack, int spriteIndex, int spriteSymbol) {
		super(name, description, spriteIndex);
		this.spriteSymbol = spriteSymbol;
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

	@Override
	public Weapon clone() {
		return new Weapon(getName(), getDescription(), getAttack(), getSpriteIndex(), getSymbol());
	}

	/**
	 * Handler method when player equips weapon, increase player attack value by
	 * {@link Weapon#attack attack}
	 */
	@Override
	public void onEquip(Player player) {
		player.setAttack(player.getAttack() + getAttack());
		player.setEquippedWeapon(this);
	}

	/**
	 * Handler method when player unequips weapon, decrease player attack value by
	 * {@link Weapon#attack attack}
	 */
	@Override
	public void onUnequip(Player player) {
		player.setAttack(player.getAttack() - getAttack());
		player.setEquippedWeapon(null);
	}

	@Override
	public int getSymbol() {
		return spriteSymbol;
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
		int symbol = -1;

		if (type.equals("Knife")) {
			symbol = Sprites.KNIFE;
		}

		if (type.equals("Spear")) {
			symbol = Sprites.SPEAR;
		}

		if (type.equals("Sword")) {
			symbol = Sprites.SWORD;
		}

		if (symbol != -1) {
			return new Weapon(name, description, attack, spriteIndex, symbol);
		}

		throw new UnknownItemTypeException("%s weapon type is unknown".formatted(type));
	}

}
