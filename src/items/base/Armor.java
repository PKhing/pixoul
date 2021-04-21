package items.base;

import exception.UnknownItemTypeException;
import items.armor.GoldenArmor;
import items.armor.IronArmor;
import items.armor.WoodenArmor;

public abstract class Armor extends Item {
	private int defense;

	public Armor(String name, String description, int defense, int spriteIndex) {
		super(name, description, spriteIndex);
		setDefense(defense);
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

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
