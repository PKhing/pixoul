package entity;

import controller.GameController;
import entity.base.Attackable;
import entity.base.Entity;
import entity.base.Monster;
import entity.base.Moveable;
import logic.GameLogic;
import logic.Sprites;
import utils.MessageTextUtil;

public class PumpkinHead extends Monster implements Moveable, Attackable {

	public PumpkinHead(int attack, int maxHealth, int defense, int posY, int posX, int direction, double critRate,
			double critPercent, int moveSpeed) {
		super("Pumpkin Head", attack, maxHealth, defense, posY, posX, direction, critRate, critPercent, moveSpeed);
		GameController.getGameMap().get(posY, posX).setEntity(this);
	}

	@Override
	public int getSymbol() {
		return Sprites.PUMPKIN_HEAD;
	}

	@Override
	public void update() {
		if (getHealth() <= 0) {
			remove();
			return;
		}
		Player gamePlayer = GameController.getPlayer();
		if (isAttackable(gamePlayer)) {
			attack(gamePlayer);
		} else {
			int nextDirection = this.getNextDirection();
			if (nextDirection != -1) {
				this.move(nextDirection);
				this.setMoving(true);
			}
		}
	}

	@Override
	public boolean attack(Entity target) {
		int atkValue = GameLogic.calculateAttackValue(this, target);
		MessageTextUtil.textWhenAttack(this, target, atkValue);
		target.setHealth(target.getHealth() - atkValue);
		target.setAttacked(true);
		return true;
	}

}
