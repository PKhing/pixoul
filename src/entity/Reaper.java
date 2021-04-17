package entity;

import controller.GameController;
import controller.InterruptController;
import effects.Immobilize;
import entity.base.Attackable;
import entity.base.Entity;
import entity.base.Monster;
import entity.base.Moveable;
import logic.GameLogic;
import logic.Sprites;
import utils.MessageTextUtil;

public class Reaper extends Monster implements Attackable, Moveable {

	public Reaper(int maxHealth, int attack, int defense, int posY, int posX, int direction, double critRate,
			double critPercent, int moveSpeed) {
		super("Reaper", maxHealth, attack, defense, posY, posX, direction, critRate, critPercent, moveSpeed);
		setPositionOnMap(posY, posX);
	}

	@Override
	public int getSymbol() {
		return Sprites.REAPER;
	}

	@Override
	public void update() {
		if (getHealth() <= 0) {
			remove();
			InterruptController.setImmobilize(false);
			return;
		}
		Player gamePlayer = GameController.getPlayer();
		if ((Math.abs(gamePlayer.getPosX() - getPosX()) <= 1) && (Math.abs(gamePlayer.getPosY() - getPosY()) <= 1)) {
			attack(gamePlayer);
		} else {
			int nextDirection = this.getNextDirection();
			if (nextDirection != -1) {
				this.move(nextDirection);
			}
		}
	}

	@Override
	public boolean attack(Entity target) {
		new Immobilize(getName(), 1, 1, false).onAdd(target);
		int atkValue = GameLogic.calculateAttackValue(this, target);
		MessageTextUtil.textWhenAttack(this, target, atkValue);
		target.setHealth(target.getHealth() - atkValue);
		target.setAttacked(true);
		return true;
	}

}
