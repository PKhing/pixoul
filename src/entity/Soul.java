package entity;

import controller.GameController;
import entity.base.Attackable;
import entity.base.Entity;
import entity.base.Monster;
import utils.MessageTextUtil;
import utils.RandomUtil;

public class Soul extends Monster implements Attackable {
	private final int attackPercent = 20;

	public Soul(int posY, int posX, int direction, int moveSpeed) {
		super("Soul", 0, 1, 0, posY, posX, direction, 0, 0, moveSpeed);
		GameController.getGameMap().get(posY, posX).setEntity(this);
	}

	@Override
	public void update() {
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
		int randValue = RandomUtil.random(0, 100);
		if (randValue >= attackPercent) {
			MessageTextUtil.textWhenAttack(this, target, 0);
		} else {
			MessageTextUtil.textWhenAttack(this, target, target.getHealth() - 1);
			target.setHealth(1);
		}
		return true;
	}

}
