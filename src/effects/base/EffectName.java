package effects.base;

import components.EffectPane;
import effects.Blindness;
import effects.Immobilize;
import effects.InstantHealth;
import effects.Poison;
import effects.Protection;
import effects.Regeneration;
import effects.Strength;
import effects.Vision;

/**
 * The EffectName is the class that contain the name of each effect class that
 * will be used to display in {@link EffectPane}
 * 
 * @see EntityEffect#toString()
 */
public class EffectName {

	/**
	 * Represent the name for {@link InstantHealth}
	 */
	public static final String INSTANT_HEALTH = "InstantHealth";

	/**
	 * Represent the name for {@link Regeneration}
	 */
	public static final String REGENERATION = "Regeneration";

	/**
	 * Represent the name for {@link Strength}
	 */
	public static final String STRENGTH = "Strength";

	/**
	 * Represent the name for {@link Protection}
	 */
	public static final String PROTECTION = "Protection";

	/**
	 * Represent the name for {@link Poison}
	 */
	public static final String POISON = "Poison";

	/**
	 * Represent the name for {@link Vision}
	 */
	public static final String VISION = "Vision";

	/**
	 * Represent the name for {@link Blindness}
	 */
	public static final String BLINDNESS = "Blindness";

	/**
	 * Represent the name for {@link Immobilize}
	 */
	public static final String IMMOBILIZED = "Immobilize";
}
