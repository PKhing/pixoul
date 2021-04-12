package effects;

import entity.base.Entity;

public class Protection extends EntityEffect {

	public Protection(String name, int value, int duration, boolean isPermanant) {
		super(name, value, duration, isPermanant);
	}

	@Override
	public void onAdd(Entity entity) {
		if (isDuplicate(entity)) {
			return;
		}

		addEffect(entity);

		int newDefense = entity.getDefense() + getValue();
		entity.setDefense(newDefense);

		if (isPermanant()) {
			removeEffect(entity);
		}
	}

	@Override
	public void onWearOff(Entity entity) {
		int newDefense = entity.getDefense() - getValue();
		entity.setDefense(newDefense);
		removeEffect(entity);
	}

	@Override
	public String getEffectName() {
		return EffectName.PROTECTION;
	}

}
