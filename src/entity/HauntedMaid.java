package entity;

import controller.GameController;
import entity.base.Attackable;
import entity.base.Entity;
import entity.base.Monster;
import entity.base.Moveable;
import logic.Cell;
import logic.Direction;
import logic.GameLogic;
import logic.Sprites;
import utils.MessageTextUtil;

public class HauntedMaid extends Monster implements Moveable, Attackable {

	public HauntedMaid(int attack, int maxHealth, int defense, int posY, int posX, int direction, double critRate,
			double critPercent, int moveSpeed) {
		super("Haunted Maid", attack, maxHealth, defense, posY, posX, direction, critRate, critPercent, moveSpeed);
		GameController.getGameMap().get(posY, posX).setEntity(this);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getSymbol() {
		return Sprites.HAUNTED_MAID;
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

	@Override
	public boolean move(int direction) {
		int newPosY = getPosY()+Direction.getMoveY(direction, getMoveSpeed());
		int newPosX = getPosX()+Direction.getMoveX(direction, getMoveSpeed());

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
