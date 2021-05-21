package items.base;

import entity.Player;
import exception.UnknownItemTypeException;
import logic.Sprites;

/**
 * The armor class is {@link Item} that player can equip to have more defense
 */
public class Armor extends Item {

	/**
	 * Represent the defense value of armor
	 */
	private int defense;

	/**
	 * The constructor of class
	 * 
	 * @param name        the name of armor
	 * @param description the description of armor
	 * @param defense     the defense value of armor
	 * @param spriteIndex the index in sprite map of armor
	 * @param symbol      the symbol index of armor
	 */
	public Armor(String name, String description, int defense, int spriteIndex, int symbol) {
		super(name, description, spriteIndex);
		setDefense(defense);
	}

	@Override
	public Armor clone() {
		return new Armor(getName(), getDescription(), getDefense(), getSpriteIndex(), getSymbol());
	}

	@Override
	public int getSymbol() {
		return Sprites.ARMOR;
	}

	/**
	 * Handler method when player equips armor, increase player defense value by
	 * {@link Armor#defense defense}
	 */
	@Override
	public void onEquip(Player player) {
		player.setDefense(player.getDefense() + getDefense());
		player.setEquippedArmor(this);
	}

	/**
	 * Handler method when player unequips armor, decrease player defense value by
	 * {@link Armor#defense defense}
	 */
	@Override
	public void onUnequip(Player player) {
		player.setDefense(player.getDefense() - getDefense());
		player.setEquippedArmor(null);
	}

	/**
	 * Getter for {@link #defense}
	 * 
	 * @return {@link #defense}
	 */
	public int getDefense() {
		return defense;
	}

	/**
	 * Setter for {@link #defense}
	 * 
	 * @param defense the new {@link #defense} value
	 */
	public void setDefense(int defense) {
		this.defense = defense;
	}

	/**
	 * The utility method for creating new {@link Armor} by using input parameter
	 * 
	 * @param type        the type of armor
	 * @param name        the name of armor
	 * @param description the description of armor
	 * @param defense     the defense value of armor
	 * @return new {@link Armor} instance that match type with input
	 * @throws UnknownItemTypeException throws when {@link Armor} type is not
	 *                                  recognized
	 */
	public static Armor parseArmor(String type, String name, String description, int defense)
			throws UnknownItemTypeException {
		if (type.equals("WoodenArmor")) {
			return new Armor(name, description, defense, 0, Sprites.ARMOR);
		}

		if (type.equals("IronArmor")) {
			return new Armor(name, description, defense, 1, Sprites.ARMOR);
		}

		if (type.equals("GoldenArmor")) {
			return new Armor(name, description, defense, 2, Sprites.ARMOR);
		}

		throw new UnknownItemTypeException("%s armor type is unknown".formatted(type));
	}

}
