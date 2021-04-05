package items.armor;

import entity.Player;
import items.base.Armor;
import logic.Sprites;

public class WoodenArmor extends Armor {

	public WoodenArmor(String name, String description, int defense) {
		super(name, description, defense);
		// TODO Auto-generated constructor stub
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
		return Sprites.WOODEN_ARMOR;
	}

}
