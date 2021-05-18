package items.potion;

import effects.InstantHealth;
import effects.base.EntityEffect;
import entity.Player;
import items.base.Potion;
import logic.Sprites;

public class InstantHealPotion extends Potion {
	public InstantHealPotion(String name, String description, int value, int duration, boolean isPermanant, int spriteIndex) {
		super(name, description, value, 0, isPermanant, spriteIndex);
	}
	
	public InstantHealPotion clone() {
		return new InstantHealPotion(getName(), getDescription(), getEffectValue(), getDuration(), isPermanent(), getSpriteIndex());
	}

	@Override
	public void onEquip(Player player) {
		if(getName().contains("Max Healing Potion")) {
			setEffectValue(player.getMaxHealth() - player.getHealth());
		}
		EntityEffect effect = new InstantHealth(getName(), getEffectValue(), getDuration(), isPermanent());
		effect.onAdd(player);
	}

	@Override
	public int getSymbol() {
		return Sprites.INSTANT_HEALTH_POTION;
	}

}
