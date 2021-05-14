package effects;

import effects.base.EffectName;
import effects.base.EntityEffect;
import entity.base.Entity;

public class Strength extends EntityEffect {

	public Strength(String name, int value, int duration, boolean isPermanant) {
		super(name, value, duration, isPermanant);
	}

	@Override
	public void onAdd(Entity entity) {
		if (isDuplicate(entity)) {
			return;
		}

		addEffect(entity);

		int newAttack = entity.getAttack() + getValue();
		entity.setAttack(newAttack);

		if (isPermanant()) {
			removeEffect(entity);
		}
	}

	@Override
	public void onWearOff(Entity entity) {
		int newAttack = entity.getAttack() - getValue();
		entity.setAttack(newAttack);
		removeEffect(entity);
	}

	@Override
	public String getEffectName() {
		return EffectName.STRENGTH;
	}

}
