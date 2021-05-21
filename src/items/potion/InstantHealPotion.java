package items.potion;

import effects.InstantHealth;
import effects.base.EntityEffect;
import entity.Player;
import items.base.Potion;
import logic.Sprites;

/**
 * The InstantHealPotion is {@link Potion} that temporary or permanent increase
 * current player health when used
 * 
 * @see {@link InstantHealth}
 */
public class InstantHealPotion extends Potion {

	/**
	 * The constructor of class
	 * 
	 * @param name        the name of the potion
	 * @param description the description of potion
	 * @param healValue   the value for {@link InstantHealth} effect
	 * @param duration    the duration of the effect
	 * @param isPermanant the effect property that permanent or not
	 * @param spriteIndex the index in sprite map of potion
	 */
	public InstantHealPotion(String name, String description, int healValue, int duration, boolean isPermanant,
			int spriteIndex) {
		super(name, description, healValue, 0, isPermanant, spriteIndex);
	}

	@Override
	public InstantHealPotion clone() {
		return new InstantHealPotion(getName(), getDescription(), getEffectValue(), getDuration(), isPermanent(),
				getSpriteIndex());
	}

	/**
	 * Add {@link InstantHealth} effect to {@link Player}
	 */
	@Override
	public void onEquip(Player player) {
		// This is for special potion that set current player health to max health
		if (getName().contains("Max Healing Potion")) {
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
