package entity.base;

/**
 * This interface defines methods for {@link Entity} that can attack another
 * entity
 */
public interface Attackable {
	/**
	 * This method is called when this entity attack target
	 * 
	 * @param the target which this entity attack to
	 * @return true if attack success otherwise false
	 */
	public abstract boolean attack(Entity target);
}
