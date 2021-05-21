package items.potion;

import effects.Vision;
import effects.base.EntityEffect;
import entity.Player;
import items.base.Potion;
import logic.Sprites;

/**
 * The StrengthPotion is {@link Potion} that temporary increase player
 * {@link Player#lineOfSight vision} when used
 * 
 * @see Vision
 */
public class VisionPotion extends Potion {
	/**
	 * The constructor of class
	 * 
	 * @param name        the name of the potion
	 * @param description the description of potion
	 * @param vision      the value for {@link Vision} effect
	 * @param duration    the duration of the effect
	 * @param isPermanant the effect property that permanent or not
	 * @param spriteIndex the index in sprite map of potion
	 */
	public VisionPotion(String name, String description, int vision, int duration, boolean isPermanant,
			int spriteIndex) {
		super(name, description, vision, duration, isPermanant, spriteIndex);
	}

	@Override
	public VisionPotion clone() {
		return new VisionPotion(getName(), getDescription(), getEffectValue(), getDuration(), isPermanent(),
				getSpriteIndex());
	}

	/**
	 * Add {@link Vision} effect to {@link Player}
	 */
	@Override
	public void onEquip(Player player) {
		EntityEffect effect = new Vision(getName(), getEffectValue(), getDuration(), isPermanent());
		effect.onAdd(player);
	}

	@Override
	public int getSymbol() {
		return Sprites.VISION_POTION;
	}
}
