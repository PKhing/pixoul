package entity;

import entity.base.Entity;
import entity.base.Moveable;
import logic.Cell;
import logic.Direction;
import logic.GameMap;

public class Player extends Entity implements Moveable {

	public Player(int posY, int posX, GameMap gameMap) {
		super(1, 1, 1, posX, posY, Direction.DOWN, 1, 1, 1);
		gameMap.get(posY, posX).setEntity(this);
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void move(GameMap gameMap, int direction) {
		int newPosY = getPosY();
		int newPosX = getPosX();
		if (direction == Direction.UP)
			newPosY = getPosY() - getMoveSpeed();
		if (direction == Direction.DOWN)
			newPosY = getPosY() + getMoveSpeed();
		if (direction == Direction.LEFT)
			newPosX = getPosX() - getMoveSpeed();
		if (direction == Direction.RIGHT)
			newPosX = getPosX() + getMoveSpeed();
		setDirection(direction);
		if(gameMap.get(newPosY, newPosX).getType()!=Cell.WALL) {
			gameMap.get(getPosY(), getPosX()).setEntity(null);
			gameMap.get(newPosY, newPosX).setEntity(this);
			setPosY(newPosY);
			setPosX(newPosX);
		}
	}

}
