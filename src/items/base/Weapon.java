package items.base;

import exception.UnknownItemTypeException;
import items.weapon.Knife;
import items.weapon.Spear;
import items.weapon.Sword;

public abstract class Weapon extends Item {
	private int attack;

	public Weapon(String name, String description, int attack) {
		super(name, description);
		setAttack(attack);
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	public static Weapon parseWeapon(String type, String name, String description, int defense)
			throws UnknownItemTypeException {
		if (type.equals("Knife")) {
			return new Knife(name, description, defense);
		}

		if (type.equals("Spear")) {
			return new Spear(name, description, defense);
		}

		if (type.equals("Sword")) {
			return new Sword(name, description, defense);
		}

		throw new UnknownItemTypeException("%s weapon type is unknown".formatted(type));
	}

}
