package items.base;

import effects.base.EntityEffect;
import entity.Player;
import exception.UnknownItemTypeException;
import items.potion.InstantHealPotion;
import items.potion.RegenerationPotion;
import items.potion.ProtectionPotion;
import items.potion.StrengthPotion;
import items.potion.VisionPotion;

/**
 *
 */
public abstract class Potion extends Item {

	/**
	 * Represent the duration of effect that used in {@link EntityEffect}
	 * 
	 * @see {@link EntityEffect#duration}
	 */
	private int duration;

	/**
	 * Represent the permanent property of effect that used in {@link EntityEffect}
	 * 
	 * @see {@link EntityEffect#isPermanant}
	 */
	private boolean isPermanant;

	/**
	 * Represent the efficient value that used in {@link EntityEffect}
	 * 
	 * @see {@link EntityEffect#value}
	 */
	private int effectValue;

	/**
	 * The constructor of class
	 * 
	 * @param name        the name of the potion
	 * @param description the description of potion
	 * @param effectValue the value of the effect
	 * @param duration    the duration of the effect
	 * @param isPermanant the effect property that permanent or not
	 * @param spriteIndex the index in sprite map of potion
	 */
	public Potion(String name, String description, int effectValue, int duration, boolean isPermanant,
			int spriteIndex) {
		super(name, description, spriteIndex);
		setEffectValue(effectValue);
		setDuration(duration);
		setPermanant(isPermanant);
	}

	/**
	 * Getter for {@link #duration}
	 * 
	 * @return {@link #duration}
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Setter for {@link #duration}
	 * 
	 * @param duration the new {@link #duration} value
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Getter for {@link #isPermanant}
	 * 
	 * @return {@link #isPermanant}
	 */
	public boolean isPermanant() {
		return isPermanant;
	}

	/**
	 * Setter for {@link #isPermanant}
	 * 
	 * @param isPermanant the new {@link #isPermanant} value
	 */
	public void setPermanant(boolean isPermanant) {
		this.isPermanant = isPermanant;
	}

	/**
	 * Getter for {@link #effectValue}
	 * 
	 * @return {@link #effectValue}
	 */
	public int getEffectValue() {
		return effectValue;
	}

	/**
	 * Setter for {@link #effectValue}
	 * 
	 * @param effectValue the new {@link #effectValue} value
	 */
	public void setEffectValue(int effectValue) {
		this.effectValue = Math.max(0, effectValue);
	}

	/**
	 * @implNote This method in {@link Potion} is doing nothing because it do not
	 *           have unequip action
	 */
	@Override
	public void onUnequip(Player player) {
	}

	/**
	 * The utility method for creating new {@link Potion} by using input parameter
	 * 
	 * @param type        the type of potion
	 * @param name        The name of the potion
	 * @param description the description of potion
	 * @param effectValue the value of the effect
	 * @param duration    the duration of the effect
	 * @param isPermanant the effect property that permanent or not
	 * @param spriteIndex the index in sprite map of potion
	 * 
	 * @return new {@link Potion} instance that match type with input
	 * @throws UnknownItemTypeException throws when {@link Potion} type is not
	 *                                  recognized
	 */
	public static Potion parsePotion(String type, String name, String description, int value, int duration,
			boolean isPermanant, int spriteIndex) throws UnknownItemTypeException {
		if (type.equals("InstantHealPotion")) {
			return new InstantHealPotion(name, description, value, duration, isPermanant, spriteIndex);
		}

		if (type.equals("RegenerationPotion")) {
			return new RegenerationPotion(name, description, value, duration, isPermanant, spriteIndex);
		}

		if (type.equals("ProtectionPotion")) {
			return new ProtectionPotion(name, description, value, duration, isPermanant, spriteIndex);
		}

		if (type.equals("StrengthPotion")) {
			return new StrengthPotion(name, description, value, duration, isPermanant, spriteIndex);
		}

		if (type.equals("VisionPotion")) {
			return new VisionPotion(name, description, value, duration, isPermanant, spriteIndex);
		}

		throw new UnknownItemTypeException("%s potion type is unknown".formatted(type));
	}

}
