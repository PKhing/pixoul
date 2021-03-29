package items.potion;

import entity.Player;
import items.base.Potion;

public class ShieldPotion extends Potion {

	public ShieldPotion(String name, String description, int duration) {
		super(name, description, duration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onUsed(Player player) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onWearOff(Player player) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getSymbol() {
		// TODO Auto-generated method stub
		return 0;
	}
}
