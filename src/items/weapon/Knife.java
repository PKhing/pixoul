package items.weapon;

import entity.Player;
import items.base.Weapon;

public class Knife extends Weapon {

	public Knife(String name, String description, int atk, int atkSpeed) {
		super(name, description, atk, atkSpeed);
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