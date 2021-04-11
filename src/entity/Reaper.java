package entity;

import controller.GameController;
import controller.InterruptController;
import entity.base.Attackable;
import entity.base.Entity;
import entity.base.Monster;
import entity.base.Moveable;
import logic.Cell;
import logic.Direction;
import logic.GameLogic;
import logic.Sprites;
import utils.MessageTextUtil;

public class Reaper extends Monster implements Attackable, Moveable {

	public Reaper(int attack, int maxHealth, int defense, int posY, int posX, int direction, double critRate,
			double critPercent, int moveSpeed) {
		super("Reaper", attack, maxHealth, defense, posY, posX, direction, critRate, critPercent, moveSpeed);
		GameController.getGameMap().get(posY, posX).setEntity(this);
		// TODO Auto-generated constructor stub
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
		if (Math.abs(gamePlayer.getPosX() - getPosX()) <= 1 && Math.abs(gamePlayer.getPosY() - getPosY()) <= 1) {
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

		if (!InterruptController.isImmobilize()) {
			// TODO add message
			InterruptController.setImmobilize(true);
		}
		int atkValue = GameLogic.calculateAttackValue(this, target);
		MessageTextUtil.textWhenAttack(this, target, atkValue);
		target.setHealth(target.getHealth() - atkValue);
		target.setAttacked(true);
		return true;
	}

	@Override
	public boolean move(int direction) {
		int newPosY = getPosY();
		int newPosX = getPosX();
		if (direction == Direction.UP)
			newPosY -= getMoveSpeed();
		if (direction == Direction.DOWN)
			newPosY += getMoveSpeed();
		if (direction == Direction.LEFT)
			newPosX -= getMoveSpeed();
		if (direction == Direction.RIGHT)
			newPosX += getMoveSpeed();

		setDirection(direction);

		Cell newPosCell = GameController.getGameMap().get(newPosY, newPosX);

		if (newPosCell.getType() != Cell.WALL && !(newPosCell.getEntity() instanceof Entity)) {
			GameController.getGameMap().get(getPosY(), getPosX()).setEntity(null);
			GameController.getGameMap().get(newPosY, newPosX).setEntity(this);
			setPosY(newPosY);
			setPosX(newPosX);
			return true;
		}

		return false;
	}

}
