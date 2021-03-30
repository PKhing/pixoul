package entity;

import controller.GameController;
import entity.base.Attackable;
import entity.base.Entity;
import entity.base.Monster;
import entity.base.Moveable;
import javafx.util.Pair;
import logic.Cell;
import logic.Direction;

public class Skeleton extends Monster implements Moveable, Attackable {

	public Skeleton(int health, int attack, int maxHealth, int defense, int posY, int posX, int direction,
			double critRate, double critPercent, int moveSpeed) {
		super(health, attack, maxHealth, defense, posY, posX, direction, critRate, critPercent, moveSpeed);
		GameController.getGameMap().get(posY, posX).setEntity(this);
		// TODO Auto-generated constructor stub
	}

	public void update() {
		if (Math.abs(GameController.getPlayer().getPosX() - getPosX()) == 1
				|| Math.abs(GameController.getPlayer().getPosY() - getPosY()) == 1) {
			attack();
		}
		else {
			Pair<Integer,Integer> newPos = this.getNextPos();
			int newY = newPos.getKey();
			int newX = newPos.getValue();
			if(newY-this.getPosY()==1) {
				this.move(Direction.RIGHT);
			}
			if(newY-this.getPosY()==-1) {
				this.move(Direction.LEFT);
			}
			if(newX-this.getPosX()==-1) {
				this.move(Direction.UP);
			}
			if(newY-this.getPosY()==-1) {
				this.move(Direction.DOWN);
			}
		}
	}

	@Override
	public boolean attack() {
		System.out.println("Attack!");
		return false;
	}

	@Override
	public void move(int direction) {
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
		}
	}

	@Override
	public void remove() {
		GameController.getGameMap().get(this.getPosY(), this.getPosX()).setEntity(null);
		
	}

}
