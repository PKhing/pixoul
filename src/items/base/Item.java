package items.base;

import entity.Player;

public abstract class Item {
	private String name;
	private String description;
	private int spriteIndex;
	
	public Item(String name, String description, int spriteIndex) {
		setName(name);
		setSpriteIndex(spriteIndex);
		setDescription(description);
	}

	public abstract int getSymbol();

	public abstract Item clone();
	
	public abstract void onEquip(Player player);

	public abstract void onUnequip(Player player);

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSpriteIndex() {
		return spriteIndex;
	}

	public void setSpriteIndex(int spriteIndex) {
		this.spriteIndex = spriteIndex;
	}
}
