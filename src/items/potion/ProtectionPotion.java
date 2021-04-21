package items.potion;

import effects.EntityEffect;
import effects.Protection;
import entity.Player;
import items.base.Potion;
import logic.Sprites;

public class ProtectionPotion extends Potion {
	public ProtectionPotion(String name, String description, int shield, int duration, boolean isPermanant, int spriteIndex) {
		super(name, description, shield, duration, isPermanant, spriteIndex);
	}
	
	public ProtectionPotion clone() {
		return new ProtectionPotion(getName(), getDescription(), getPotionValue(), getDuration(), isPermanant(), getSpriteIndex());
	}
	
	@Override
	public void onEquip(Player player) {
		EntityEffect effect = new Protection(getName(), getPotionValue(), getDuration(), isPermanant());
		effect.onAdd(player);
	}

	@Override
	public int getSymbol() {
		return Sprites.SHIELD_POTION;
	}

}
