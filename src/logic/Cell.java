package logic;

import entity.base.Entity;

public class Cell {
	public static final int PATH = 0;
	public static final int WALL = 1;
	public static final int VOID = 2;
	private int type;
	private Entity entity; 

	public Cell() {
		this(VOID);
	}

	public Cell(int type) {
		this.type = type;
		this.entity = null;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

}
