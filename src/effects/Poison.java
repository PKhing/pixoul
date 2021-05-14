package effects;

import effects.base.EffectName;
import effects.base.EntityEffect;
import effects.base.IConsecutiveEffect;
import entity.base.Entity;

public class Poison extends EntityEffect implements IConsecutiveEffect {

	public Poison(String name, int value, int duration, boolean isPermanant) {
		super(name, value, duration, isPermanant);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAdd(Entity entity) {
		addEffect(entity);
	}

	@Override
	public void effect(Entity entity) {
		int newHealth = entity.getHealth() - getValue();
		entity.setHealth(newHealth);
		entity.setAttacked(true);
	}

	@Override
	public void onWearOff(Entity entity) {
		removeEffect(entity);
	}

	@Override
	public String getEffectName() {
		return EffectName.POISON;
	}

}
