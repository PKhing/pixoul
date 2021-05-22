package items.potion;

import effects.Strength;
import effects.base.EntityEffect;
import entity.Player;
import items.base.Potion;
import logic.Sprites;

/**
 * The StrengthPotion is {@link Potion} that temporary or permanent increase
 * current player attack when used.
 * 
 * @see Strength
 */
public class StrengthPotion extends Potion {

	/**
	 * The constructor of class.
	 * 
	 * @param name        the name of the potion
	 * @param description the description of potion
	 * @param attack      the value for {@link Strength} effect
	 * @param duration    the duration of the effect
	 * @param isPermanant the effect property that permanent or not
	 * @param spriteIndex the index in sprite map of potion
	 */
	public StrengthPotion(String name, String description, int attack, int duration, boolean isPermanant,
			int spriteIndex) {
		super(name, description, attack, duration, isPermanant, spriteIndex);
	}

	@Override
	public StrengthPotion clone() {
		return new StrengthPotion(getName(), getDescription(), getEffectValue(), getDuration(), isPermanent(),
				getSpriteIndex());
	}

	/**
	 * Add {@link Strength} effect to {@link Player}.
	 */
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
