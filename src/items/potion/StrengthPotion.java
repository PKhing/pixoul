package items.potion;

import effects.Strength;
import effects.base.EntityEffect;
import entity.Player;
import items.base.Potion;
import logic.Sprites;

public class StrengthPotion extends Potion {
	public StrengthPotion(String name, String description, int attack, int duration, boolean isPermanant, int spriteIndex) {
		super(name, description, attack, duration, isPermanant, spriteIndex);
	}

	public StrengthPotion clone() {
		return new StrengthPotion(getName(), getDescription(), getEffectValue(), getDuration(), isPermanent(), getSpriteIndex());
	}
	
	@Override
	public void onEquip(Player player) {
		EntityEffect effect = new Strength(getName(), getEffectValue(), getDuration(), isPermanent());
		effect.onAdd(player);
	}

	@Override
	public int getSymbol() {
		return Sprites.STRENGTH_POTION;
	}
}
