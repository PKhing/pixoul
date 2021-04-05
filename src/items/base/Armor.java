package items.base;

public abstract class Armor extends Item {
	private int defense;

	public Armor(String name, String description, int defense) {
		super(name, description);
		setDefense(defense);
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

}
