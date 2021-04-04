package items.armor;

import entity.Player;
import items.base.Armor;
import logic.Sprites;

public class IronArmor extends Armor {

	public IronArmor(String name, String description, int defense) {
		super(name, description, defense);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onEquip(Player player) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUnequip(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSymbol() {
		return Sprites.IRON_ARMOR;
	}
	
	
}
