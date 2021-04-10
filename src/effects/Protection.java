package effects;

import entity.base.Entity;

public class Protection extends EntityEffect {

	public Protection(String name, int value, int duration, boolean isPermanant) {
		super(name, value, duration, isPermanant);
	}

	@Override
	public void onAdd(Entity entity) {
		if(isDuplicate(entity)) {
			return;
		}
		
		entity.getEffectList().add(this);
		
		int newDefense = entity.getDefense() + getValue();
		entity.setDefense(newDefense);

		if (isPermanant()) {
			remove(entity);
		}
	}

	@Override
	public void onWearOff(Entity entity) {
		int newDefense = entity.getDefense() - getValue();
		entity.setDefense(newDefense);
		remove(entity);
	}

	@Override
	public String getEffectName() {
		return EffectName.PROTECTION;
	}

}
