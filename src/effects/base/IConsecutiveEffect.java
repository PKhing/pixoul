package effects.base;

import entity.base.Entity;

/**
 * This interface defines methods for {@link EntityEffect} that have a
 * consecutive effect (active every move) or not
 */
public interface IConsecutiveEffect {
	/**
	 * This method is called after pass each turn if effect is still active
	 * 
	 * @param entity the target entity which effect have affected
	 */
	public abstract void effect(Entity entity);
}
