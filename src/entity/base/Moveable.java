package entity.base;

import logic.Cell;
import logic.Direction;

/**
 * This interface defines methods for {@link Entity} that can move to another
 * {@link Cell}.
 */
public interface Moveable {
	/**
	 * This methods is called when {@link Entity} is moving to specific
	 * {@link Direction}.
	 * 
	 * @param direction the direction which entity will be moving to
	 * @return true if entity can move to that direction otherwise false
	 */
	public abstract boolean move(int direction);
}
