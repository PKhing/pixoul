package items.potion;

import effects.Vision;
import entity.Player;
import items.base.Potion;
import logic.Sprites;

public class VisionPotion extends Potion {
	private int vision;

	public VisionPotion(String name, String description, int vision, int duration, boolean isPermanant) {
		super(name, description, duration, isPermanant);
		setVision(vision);
	}

	@Override
	public void onEquip(Player player) {
		new Vision(getName(), getVision(), getDuration(), isPermanant()).onAdd(player);
	}

	@Override
	public int getSymbol() {
		return Sprites.STRENGTH_POTION;
	}

	public int getVision() {
		return vision;
	}

	public void setVision(int attack) {
		this.vision = Math.max(0, attack);
	}
}
