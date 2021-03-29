package logic;

import entity.base.Entity;
import utils.Util;

public class Cell {
	public static final int PATH = 0;
	public static final int WALL = 1;
	public static final int VOID = 2;
	private static final int TILE_SPRITE_TYPE = 6;
	private int type;
	private Entity entity; 
	private int symbol;
	
	public Cell() {
		this(VOID);
	}

	public Cell(int type) {
		setType(type);
		setEntity(null);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
		if(type == PATH)setSymbol(Util.random(0, TILE_SPRITE_TYPE - 1));
		if(type == WALL)setSymbol(6);
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

}
