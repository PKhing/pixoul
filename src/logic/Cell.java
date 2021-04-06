package logic;

import entity.base.Entity;
import items.base.Item;
import utils.RandomUtil;

public class Cell {
	public static final int PATH = 0;
	public static final int WALL = 1;
	public static final int VOID = 2;
	public static final int LADDER_UP = 3;
	public static final int LADDER_DOWN = 4;
	private static final int TILE_SPRITE_TYPE = 6;
	private int type;
	private int symbol;
	
	private Entity entity;
	private Item item;

	public Cell() {
		this(VOID);
	}

	public Cell(int type) {
		setType(type);
		setEntity(null);
		setItem(null);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
		if (type == PATH)
			setSymbol(RandomUtil.random(0, TILE_SPRITE_TYPE - 1));
		if (type == WALL)
			setSymbol(TILE_SPRITE_TYPE);
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public int getSymbol() {
		return symbol;
	}

	public void setSymbol(int symbol) {
		this.symbol = symbol;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
