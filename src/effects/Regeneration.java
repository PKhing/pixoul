package effects;

import effects.base.EffectName;
import effects.base.EntityEffect;
import effects.base.IConsecutiveEffect;
import entity.base.Entity;

public class Regeneration extends EntityEffect implements IConsecutiveEffect {

	public Regeneration(String name, int value, int duration, boolean isPermanant) {
		super(name, value, duration, false);
	}

	@Override
	public void onAdd(Entity entity) {
		if (isDuplicate(entity)) {
			return;
		}
		addEffect(entity);
	}

	@Override
	public void effect(Entity entity) {
		int newHealth = Math.min(entity.getMaxHealth(), entity.getHealth() + getValue());
		entity.setHealth(newHealth);
	}

	@Override
	public void onWearOff(Entity entity) {
		entity.getEffectList().remove(this);
	}

	@Override
	public String getEffectName() {
		return EffectName.REGENERATION;
	}
}
