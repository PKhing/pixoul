package items.potion;

import entity.Player;
import items.base.Potion;

public class HealingPotion extends Potion {

	public HealingPotion(String name, String description, int duration) {
		super(name, description, duration);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void used(Player player) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getSymbol() {
		// TODO Auto-generated method stub
		return 0;
	}
}
