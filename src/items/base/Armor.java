package items.base;

import exception.UnknownItemTypeException;
import items.armor.GoldenArmor;
import items.armor.IronArmor;
import items.armor.WoodenArmor;

/**
 * The armor class is {@link Item} that player can equip to have more defense
 */
public abstract class Armor extends Item {
	
	/**
	 * Represent the defense value of armor
	 */
	private int defense;

	/**
	 * The constructor of class
	 * 
	 * @param name the name of armor
	 * @param description the description of armor
	 * @param defense the defense value of armor
	 * @param spriteIndex the index in sprite map of armor
	 */
	public Armor(String name, String description, int defense, int spriteIndex) {
		super(name, description, spriteIndex);
		setDefense(defense);
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
	 * @param type the type of armor
	 * @param name the name of armor
	 * @param description the description of armor
	 * @param defense the defense value of armor
	 * @param spriteIndex the index in sprite map of armor
	 * @return new {@link Armor} instance that match type with input
	 * @throws UnknownItemTypeException throws when {@link Armor} type is not recognized
	 */
	public static Armor parseArmor(String type, String name, String description, int defense, int spriteIndex)
			throws UnknownItemTypeException {
		if (type.equals("GoldenArmor")) {
			return new GoldenArmor(name, description, defense, spriteIndex);
		}

		if (type.equals("IronArmor")) {
			return new IronArmor(name, description, defense, spriteIndex);
		}

		if (type.equals("WoodenArmor")) {
			return new WoodenArmor(name, description, defense, spriteIndex);
		}

		throw new UnknownItemTypeException("%s armor type is unknown".formatted(type));
	}

}
