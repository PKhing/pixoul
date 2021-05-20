package items.potion;

import effects.Regeneration;
import effects.base.EntityEffect;
import entity.Player;
import items.base.Potion;
import logic.Sprites;

/**
 * The RegenerationPotion is {@link Potion} that consecutively increasing player
 * health for specific duration
 * 
 * @see {@link Regeneration}
 */
public class RegenerationPotion extends Potion {

	/**
	 * The constructor of class
	 * 
	 * @param name        the name of the potion
	 * @param description the description of potion
	 * @param healValue   the value for {@link Regeneration} effect
	 * @param duration    the duration of the effect
	 * @param isPermanant the effect property that permanent or not
	 * @param spriteIndex the index in sprite map of potion
	 */
	public RegenerationPotion(String name, String description, int healValue, int duration, boolean isPermanent,
			int spriteIndex) {
		// isPermanent is ignored for this potion type
		super(name, description, healValue, duration, false, spriteIndex);
	}

	@Override
	public RegenerationPotion clone() {
		return new RegenerationPotion(getName(), getDescription(), getEffectValue(), getDuration(), isPermanent(),
				getSpriteIndex());
	}

	@Override
	public int getSymbol() {
		return Sprites.REGENERATION_POTION;
	}

	/**
	 * Add {@link Regeneration} effect to {@link Player}
	 */
	@Override
	public void onEquip(Player player) {
		EntityEffect effect = new Regeneration(getName(), getEffectValue(), getDuration(), isPermanent());
		effect.onAdd(player);
	}

}
