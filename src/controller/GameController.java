package controller;

import java.util.ArrayList;
import logic.GameMap;

public class GameController {
	private ArrayList<GameMap> floorList;
	private GameMap nowMap;
	private int level;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public ArrayList<GameMap> getFloorList() {
		return floorList;
	}

	public void setFloorList(ArrayList<GameMap> floorList) {
		this.floorList = floorList;
	}
	
	public GameMap moveToFloor(int floor) {
		return this.getFloor(floor);
	}
	
	private GameMap getFloor(int floor) {
		if(this.getFloorList().size() <= floor) {
			this.addNewFloor();
		}
		return this.getFloorList().get(floor - 1);
	}
	
	private void addNewFloor() {
		this.getFloorList().add(new GameMap());
	}

	public void reset() {
		this.floorList.clear();
	}
}
