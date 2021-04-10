package effects;

import entity.base.Entity;

public class AttackBoost extends EntityEffect {

	public AttackBoost(String name, int value, int duration, boolean isPermanant) {
		super(name, value, duration, isPermanant);
	}

	@Override
	public void onAdd(Entity entity) {
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
	
}
