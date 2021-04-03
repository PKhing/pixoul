package entity;

import controller.GameController;
import entity.base.Attackable;
import entity.base.Entity;
import entity.base.Monster;
import entity.base.Moveable;
import javafx.util.Pair;
import logic.Cell;
import logic.Direction;
import logic.GameLogic;

public class Skeleton extends Monster implements Moveable, Attackable {

	public Skeleton(int attack, int maxHealth, int defense, int posY, int posX, int direction, double critRate,
			double critPercent, int moveSpeed) {
		super(attack, maxHealth, defense, posY, posX, direction, critRate, critPercent, moveSpeed);
		GameController.getGameMap().get(posY, posX).setEntity(this);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		if(getHealth() <= 0) {
			remove();
			return;
		}
		Player gamePlayer = GameController.getPlayer();
		if (Math.abs(gamePlayer.getPosX() - getPosX()) <= 1
				&& Math.abs(gamePlayer.getPosY() - getPosY()) <= 1) {
			attack(gamePlayer);
		} else {
			Pair<Integer, Integer> newPos = this.getNextPos();
			if (newPos == null) {
				return;
			}
			int newY = newPos.getKey();
			int newX = newPos.getValue();
			if (newY - this.getPosY() == 1) {
				this.move(Direction.DOWN);
			}
			if (newY - this.getPosY() == -1) {
				this.move(Direction.UP);
			}
			if (newX - this.getPosX() == -1) {
				this.move(Direction.LEFT);
			}
			if (newX - this.getPosX() == 1) {
				this.move(Direction.RIGHT);
			}
		}
	}

	@Override
	public boolean attack(Entity target) {
		int atkValue = GameLogic.calculateAttackValue(this, target);
		target.setHealth(target.getHealth() - atkValue);
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
