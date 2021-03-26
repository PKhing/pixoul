package entity;

import entity.base.Entity;
import entity.base.Moveable;
import logic.Direction;
import logic.GameMap;

public class Player extends Entity implements Moveable{

	public Player(int posY, int posX, GameMap gameMap) {
		super(1, 1, 1, posX, posY, 1, 1, 1);
		gameMap.get(posY, posX).setEntity(this);
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(GameMap gameMap,int direction) {
		gameMap.get(getPosY(),getPosX()).setEntity(null);
		if(direction==Direction.Up)setPosY(getPosY()-getMoveSpeed());
		if(direction==Direction.Down)setPosY(getPosY()+getMoveSpeed());
		if(direction==Direction.Left)setPosX(getPosX()-getMoveSpeed());
		if(direction==Direction.Right)setPosX(getPosX()+getMoveSpeed());
		gameMap.get(getPosY(),getPosX()).setEntity(this);
	}
	
}
