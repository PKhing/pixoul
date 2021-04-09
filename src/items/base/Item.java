package items.base;

import entity.Player;
import logic.Renderable;

public abstract class Item{
	private String name;
	private String description;

	public Item(String name, String description) {
		setName(name);
		setDescription(description);
	}

	public abstract int getSymbol();
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
}
