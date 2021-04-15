package items.armor;

import entity.Player;
import items.base.Armor;
import items.weapon.Knife;
import logic.Sprites;

public class GoldenArmor extends Armor {
	public GoldenArmor(String name, String description, int defense) {
		super(name, description, defense);
		// TODO Auto-generated constructor stub
	}

	public GoldenArmor clone() {
		return new GoldenArmor(getName(), getDescription(), getDefense());
	}
	
	@Override
	public void onEquip(Player player) {
		// TODO Auto-generated method stub
		player.setDefense(player.getDefense() + getDefense());
		player.setEquippedArmor(this);
	}

	@Override
	public void onUnequip(Player player) {
		// TODO Auto-generated method stub
		player.setDefense(player.getDefense() - getDefense());
		player.setEquippedArmor(null);
	}

	@Override
	public int getSymbol() {
		return Sprites.GOLDEN_ARMOR;
	}

}
