package effects;

import entity.base.Entity;
import utils.GameConfig;

public class Vision extends EntityEffect {

	public Vision(String name, int value, int duration, boolean isPermanant) {
		super(name, value, duration, isPermanant);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAdd(Entity entity) {
		
	}
	
	@Override
	public void onWearOff(Entity entity) {
		
	}

	@Override
	public String getEffectName() {
		return EffectName.VISION;
	}

}
