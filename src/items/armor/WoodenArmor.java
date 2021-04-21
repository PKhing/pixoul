package items.armor;

import entity.Player;
import items.base.Armor;
import logic.Sprites;

public class WoodenArmor extends Armor {

	public WoodenArmor(String name, String description, int defense, int spriteIndex) {
		super(name, description, defense, spriteIndex);
		// TODO Auto-generated constructor stub
	}

	public WoodenArmor clone() {
		return new WoodenArmor(getName(), getDescription(), getDefense(), getSpriteIndex());
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
		return Sprites.ARMOR;
	}

}
