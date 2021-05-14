package effects;

import effects.base.EffectName;
import effects.base.EntityEffect;
import entity.Player;
import entity.base.Entity;

public class Vision extends EntityEffect {

	public Vision(String name, int value, int duration, boolean isPermanant) {
		super(name, value, duration, isPermanant);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAdd(Entity entity) {
		if (entity instanceof Player) {
			entity.getEffectList().add(this);
			int newLineOfSight = ((Player) entity).getLineOfSight() + getValue();
			((Player) entity).setLineOfSight(newLineOfSight);
		}
	}

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
