package entity.base;

import logic.GameMap;

public interface Moveable {
	public abstract void move(GameMap gameMap,int direction);
}
