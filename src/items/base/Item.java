package items.base;

import entity.Player;

/**
 * The abstract base class for all items
 */
public abstract class Item {

	/**
	 * Represent name of item
	 */
	private String name;

	/**
	 * Represent description of item
	 */
	private String description;

	/**
	 * Represent index in sprite map of item
	 */
	private int spriteIndex;

	/**
	 * The constructor of this class
	 * 
	 * @param name        the name of this item
	 * @param description the description of this item
	 * @param spriteIndex the index in sprite map of item
	 */
	public Item(String name, String description, int spriteIndex) {
		setName(name);
		setSpriteIndex(spriteIndex);
		setDescription(description);
	}

	/**
	 * Getter for sprite symbol of this item
	 * 
	 * @return This item's sprite symbol
	 */
	public abstract int getSymbol();

	/**
	 * Clone method for making a new {@link Item} instance with same property
	 * 
	 * @return new instance of {@link Item}
	 */
	@Override
	public abstract Item clone();

	/**
	 * Handler method when {@link Player} equip item
	 * 
	 * @param player the {@link Player} who equip item
	 */
	public abstract void onEquip(Player player);

	/**
	 * Handler method when {@link Player} unequips item
	 * 
	 * @param player the {@link Player} who unequips item
	 */
	public abstract void onUnequip(Player player);

	/**
	 * Getter for {@link #description}
	 * 
	 * @return {@link #description}
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter for {@link #description}
	 * 
	 * @param description the new {@link #description}
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter for {@link #name}
	 * 
	 * @return {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for {@link #name}
	 * 
	 * @param name the new {@link #name}
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for {@link #spriteIndex}r
	 * 
	 * @return {@link #spriteIndex}
	 */
	public int getSpriteIndex() {
		return spriteIndex;
	}

	/**
	 * Setter for {@link #spriteIndex}
	 * 
	 * @param spriteIndex the new {@link #spriteIndex}
	 */
	public void setSpriteIndex(int spriteIndex) {
		this.spriteIndex = spriteIndex;
	}
}
