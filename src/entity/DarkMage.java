package entity;

import controller.GameController;
import entity.base.Monster;
import logic.Sprites;

public class DarkMage extends Monster{

	public DarkMage(int maxHealth, int defense, int posY, int posX, int direction) {
		super("Dark Mage", 0, maxHealth, defense, posY, posX, direction, 0, 0, 0);
		GameController.getGameMap().get(posY, posX).setEntity(this);
		// TODO Auto-generated constructor stub
	}
	@Override
	public int getSymbol(){
		return Sprites.DARK_MAGE;
	}
	@Override
	public void update() {
		if(getHealth() <= 0) {
			remove();
			return;
		}
		Player gamePlayer = GameController.getPlayer();
		if (isAttackable(gamePlayer)) {
			//TODO Use skill
		} 
	}




}
