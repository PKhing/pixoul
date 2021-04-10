package effects;

import entity.base.Entity;

public class Strength extends EntityEffect {

	public Strength(String name, int value, int duration, boolean isPermanant) {
		super(name, value, duration, isPermanant);
	}

	@Override
	public void onAdd(Entity entity) {
		if(isDuplicate(entity)) {
			return;
		}
		
		entity.getEffectList().add(this);
		
		int newAttack = entity.getAttack() + getValue();
		entity.setAttack(newAttack);
		
		if(isPermanant()) {
			remove(entity);
		}
	}

	@Override
	public void onWearOff(Entity entity) {
		int newAttack = entity.getAttack() - getValue();
		entity.setAttack(newAttack);
		remove(entity);
	}

	@Override
	public String getEffectName() {
		return EffectName.STRENGTH;
	}
	
}
