package items.potion;

import effects.Protection;
import effects.base.EntityEffect;
import entity.Player;
import items.base.Potion;
import logic.Sprites;

/**
 * The ProtectionPotion is {@link Potion} that temporary or permanent increase
 * current player defense when used.
 * 
 * @see Protection
 */
public class ProtectionPotion extends Potion {

	/**
	 * The constructor of class.
	 * 
	 * @param name        the name of the potion
	 * @param description the description of potion
	 * @param shield      the value for {@link Protection} effect
	 * @param duration    the duration of the effect
	 * @param isPermanent the effect property that permanent or not
	 * @param spriteIndex the index in sprite map of potion
	 */
	public ProtectionPotion(String name, String description, int shield, int duration, boolean isPermanent,
			int spriteIndex) {
		super(name, description, shield, duration, isPermanent, spriteIndex);
	}

	@Override
	public ProtectionPotion clone() {
		return new ProtectionPotion(getName(), getDescription(), getEffectValue(), getDuration(), isPermanent(),
				getSpriteIndex());
	}

	/**
	 * Add {@link Protection} effect to {@link Player}.
	 */
	@Override
	public void onEquip(Player player) {
		EntityEffect effect = new Protection(getName(), getEffectValue(), getDuration(), isPermanent());
		effect.onAdd(player);
	}

	@Override
	public int getSymbol() {
		return Sprites.SHIELD_POTION;
	}

}
