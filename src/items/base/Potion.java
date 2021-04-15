package items.base;

import entity.Player;
import exception.UnknownItemTypeException;
import items.potion.InstantHealPotion;
import items.potion.RegenerationPotion;
import items.potion.ShieldPotion;
import items.potion.StrengthPotion;
import items.potion.VisionPotion;

public abstract class Potion extends Item {
	private int duration;
	private boolean isPermanant;

	public Potion(String name, String description, int duration, boolean isPermanant) {
		super(name, description);
		setDuration(duration);
		setPermanant(isPermanant);
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isPermanant() {
		return isPermanant;
	}

	public void setPermanant(boolean isPermanant) {
		this.isPermanant = isPermanant;
	}

	@Override
	public void onUnequip(Player player) {
	}

	public static Potion parsePotion(String type, String name, String description, int value, int duration,
			boolean isPermanant) throws UnknownItemTypeException {
		if (type.equals("InstantHealPotion")) {
			return new InstantHealPotion(name, description, value, duration, isPermanant);
		}

		if (type.equals("RegenerationPotion")) {
			return new RegenerationPotion(name, description, value, duration, isPermanant);
		}

		if (type.equals("ShieldPotion")) {
			return new ShieldPotion(name, description, value, duration, isPermanant);
		}

		if (type.equals("StrengthPotion")) {
			return new StrengthPotion(name, description, value, duration, isPermanant);
		}

		if (type.equals("VisionPotion")) {
			return new VisionPotion(name, description, value, duration, isPermanant);
		}

		throw new UnknownItemTypeException("%s potion type is unknown".formatted(type));
	}
	
}
