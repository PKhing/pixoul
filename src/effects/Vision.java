package effects;

import effects.base.EffectName;
import effects.base.EntityEffect;
import entity.Player;
import entity.base.Entity;

/**
 * The Vision class is the {@link EntityEffect} that will increase the line of
 * sight of {@link Player}
 * 
 * <p>Note that this effect will be applied to {@link Player} only</p>
 */
public class Vision extends EntityEffect {

	/**
	 * The constructor of the class
	 * 
	 * @param name        The name of the effect
	 * @param value       The value of the effect
	 * @param duration    The duration of the effect
	 * @param isPermanent The effect property that permanent or not
	 */
	public Vision(String name, int value, int duration, boolean isPermanent) {
		super(name, value, duration, isPermanent);
	}

	/**
	 * Increase line of sight of specific {@link Player} and add effect to
	 * {@link Player}
	 */
	@Override
	public void onAdd(Entity entity) {
		if (entity instanceof Player) {
			addEffect(entity);
			int newLineOfSight = ((Player) entity).getLineOfSight() + getValue();
			((Player) entity).setLineOfSight(newLineOfSight);

			if (isPermanent()) {
				removeEffect(entity);
			}
		}
	}

	/**
	 * Decrease line of sight of {@link Player} back to normal and remove effect
	 * from {@link Player}
	 */
	@Override
	public void onWearOff(Entity entity) {
		if (entity instanceof Player) {
			int newLineOfSight = ((Player) entity).getLineOfSight() - getValue();
			((Player) entity).setLineOfSight(newLineOfSight);
		}
		removeEffect(entity);
	}

	@Override
	public String getEffectName() {
		return EffectName.VISION;
	}

}
