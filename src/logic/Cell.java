package logic;

public class Cell {
	public static final int PATH = 0;
	public static final int WALL = 1;
	public static final int VOID = 2;
	private int type;
	private int entity; // change to entity type later
	public Cell(int type) {
		this.type = type;
		this.entity = 0;
	}
	public Cell() {
		this(VOID);
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getEntity() {
		return entity;
	}
	public void setEntity(int entity) {
		this.entity = entity;
	}
	
}
