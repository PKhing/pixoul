package items.armor;

import entity.Player;
import items.base.Armor;

public class NormalArmor extends Armor {

	public NormalArmor(String name, String description, int defense) {
		super(name, description, defense);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onEquip(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDeequip(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getSymbol() {
		// TODO Auto-generated method stub
		return 0;
	}

}
