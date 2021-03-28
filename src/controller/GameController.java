package controller;

import java.util.ArrayList;
import logic.GameMap;

public class GameController {
	private ArrayList<GameMap> floorList;
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
	
	public GameMap getFloor(int floor) {
		return this.getFloorList().get(floor - 1);
	}
	
	public void addFloor(GameMap newFloor) {
		this.getFloorList().add(newFloor);
	}

}
