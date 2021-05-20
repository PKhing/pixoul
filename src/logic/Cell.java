package logic;

import entity.base.Entity;
import items.base.Item;
import utils.RandomUtil;

/**
 * The Cell class is used to represent individual cells in {@link GameMap}. It
 * stores data of type, symbol, {@link Entity} and {@link Item} on this cell.
 *
 */
public class Cell {
	
	/**
	 * Represents path.
	 */
	public static final int PATH = 0;
	
	/**
	 * Represents wall.
	 */
	public static final int WALL = 1;
	
	/**
	 * Represents void.
	 */
	public static final int VOID = 2;
	
	/**
	 * Represents path with ladder to the upper floor.
	 */
	public static final int LADDER_UP = 3;
	
	/**
	 * Represents path with ladder to the lower floor.
	 */
	public static final int LADDER_DOWN = 4;
	
	/**
	 * A constant holding the maximum type of path sprite.
	 */
	private static final int TILE_SPRITE_TYPE = 6;
	
	/**
	 * The type of this cell.
	 */
	private int type;
	
	/**
	 * The symbol of this cell. (for rendering)
	 */
	private int symbol;

	/**
	 * The entity on this cell.
	 */
	private Entity entity;

	/**
	 * The item on this cell.
	 */
	private Item item;

	/**
	 * Creates a new cell with void type.
	 */
	public Cell() {
		this(VOID);
	}

	/**
	 * Creates a new cell with a specified type.
	 * 
	 * @param type Type of the new cell
	 */
	public Cell(int type) {
		setType(type);
		setEntity(null);
		setItem(null);
	}

	/**
	 * Getter for type.
	 * 
	 * @return This cell's type
	 */
	public int getType() {
		return type;
	}

	/**
	 * Set cell type. If the type is {@code PATH}, randomly set the symbol of this
	 * cell.
	 * 
	 * @param type The type to be set.
	 */
	public void setType(int type) {
		this.type = type;
		if (type == PATH) {
			setSymbol(RandomUtil.random(0, TILE_SPRITE_TYPE - 1));
		}
		if (type == WALL) {
			setSymbol(TILE_SPRITE_TYPE);
		}
	}

	/**
	 * Getter for entity.
	 * 
	 * @return This cell's entity
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * Setter for entity.
	 * 
	 * @param entity The entity to be set.
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	/**
	 * Getter for symbol.
	 * 
	 * @return This cell's symbol
	 */
	public int getSymbol() {
		return symbol;
	}

	/**
	 * Setter for symbol.
	 * 
	 * @param symbol The symbol to be set.
	 */
	public void setSymbol(int symbol) {
		this.symbol = symbol;
	}

	/**
	 * Getter for item.
	 * 
	 * @return This cell's item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * Setter for item.
	 * 
	 * @param item The item to be set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

}
