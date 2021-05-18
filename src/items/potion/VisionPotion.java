package items.potion;

import effects.Vision;
import effects.base.EntityEffect;
import entity.Player;
import items.base.Potion;
import logic.Sprites;

public class VisionPotion extends Potion {
	public VisionPotion(String name, String description, int vision, int duration, boolean isPermanant, int spriteIndex) {
		super(name, description, vision, duration, isPermanant, spriteIndex);
	}
	
	public VisionPotion clone() {
		return new VisionPotion(getName(), getDescription(), getEffectValue(), getDuration(), isPermanant(), getSpriteIndex());
	}

	@Override
	public void onEquip(Player player) {
		EntityEffect effect = new Vision(getName(), getEffectValue(), getDuration(), isPermanant());
		effect.onAdd(player);
	}

	@Override
	public int getSymbol() {
		return Sprites.VISION_POTION;
	}
}
