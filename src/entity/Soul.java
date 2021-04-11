package entity;

import controller.GameController;
import entity.base.Attackable;
import entity.base.Entity;
import entity.base.Monster;
import logic.Sprites;
import utils.MessageTextUtil;
import utils.RandomUtil;

public class Soul extends Monster implements Attackable {
	private final int attackPercent = 20;
	private boolean isFriendly;

	public Soul(int posY, int posX, int direction, int moveSpeed) {
		super("Soul", 0, 1, 0, posY, posX, direction, 0, 0, moveSpeed);
		GameController.getGameMap().get(posY, posX).setEntity(this);
	}
	@Override
	public int getSymbol(){
		if(isFriendly) 
			return Sprites.FRIENDLY_SOUL;
		return Sprites.SOUL;
	}
	@Override
	public void update() {
		if (RandomUtil.random(0, 100) < attackPercent) {
			setFriendly(false);
		} else
			setFriendly(true);
		if (getHealth() <= 0) {
			remove();
			return;
		}
		Player gamePlayer = GameController.getPlayer();
		if (Math.abs(gamePlayer.getPosX() - getPosX()) <= 1 && Math.abs(gamePlayer.getPosY() - getPosY()) <= 1) {
			attack(gamePlayer);
		}
	}

	@Override
	public boolean attack(Entity target) {
		if (isFriendly())
			return true;
		MessageTextUtil.textWhenAttack(this, target, target.getHealth() - 1);
		target.setHealth(1);
		return true;
	}

	public boolean isFriendly() {
		return isFriendly;
	}

	public void setFriendly(boolean isFriendly) {
		this.isFriendly = isFriendly;
	}

}
