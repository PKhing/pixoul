package controller;

import java.util.ArrayList;
import java.util.List;

import entity.Player;
import exception.InvalidFloorException;
import exception.NullMapException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Pair;
import logic.GameLogic;
import logic.GameMap;
import logic.MapGenerator;
import scene.GameScene;
import utils.GameAudioUtils;

public class GameController {
	private static ArrayList<GameMap> floorList = new ArrayList<>();
	private static GameMap gameMap;
	private static MediaPlayer bgmMedia = GameAudioUtils.GameSceneBGM;
	private static int level;
	private static Player player;
	
	static {
		setPlayer(new Player());
	}

	public static GameMap getFloor(int floor) throws InvalidFloorException {
		if (floorList.size() < floor || floor <= 0) {
			throw new InvalidFloorException();
		}
		return floorList.get(floor - 1);
	}

	private static GameMap addNewFloor() {
		GameMap newFloor = MapGenerator.generateMap();
		floorList.add(newFloor);
		return newFloor;
	}

	public static boolean descending() {
		level += 1;
		gameMap.get(player.getPosY(), player.getPosX()).setEntity(null);
		try {
			setGameMap(getFloor(level));	
		} catch (InvalidFloorException e) {
			GameMap newFloor = addNewFloor();
			setGameMap(newFloor);
		}
		int posX = gameMap.getRoomList().get(0).getValue();
		int posY = gameMap.getRoomList().get(0).getKey();
		
		player.setInitialPos(posY, posX);
		gameMap.get(posY, posX).setEntity(player);
		return true;
	}

	public static boolean ascending() {
		try {
			GameMap newMap = getFloor(level - 1);
			
			gameMap.get(player.getPosY(), player.getPosX()).setEntity(null);
			
			setGameMap(newMap);
		} catch (InvalidFloorException e) {
			return false;
		}

		level -= 1;

		List<Pair<Integer, Integer>> roomList = gameMap.getRoomList();
		
		int posX = roomList.get(roomList.size() - 1).getValue();
		int posY = roomList.get(roomList.size() - 1).getKey();
		
		player.setInitialPos(posY, posX);
		gameMap.get(posY, posX).setEntity(player);
		
		return true;
	}

	public static void reset() {
		floorList.clear();
		player = new Player();
		level = 1;
	}

	public static void start() {
		reset();
		GameMap newFloor = addNewFloor();
		setGameMap(newFloor);
		GameScene.getMessagePane().resetMessage();
		GameScene.setPlayerPositionOnNewMap();
		GameScene.getStatusPane().setAllValue(GameController.getPlayer());
		getGameMap().drawMap();
		SceneController.setSceneToStage(GameScene.getScene());
		GameLogic.postGameUpdate();
		bgmMedia.play();
	}

	public static void exitToMainMenu() {
		bgmMedia.stop();
		SceneController.backToMainMenu();
	}

	public static void gameover() {
		return;
	}

	public static GameMap getGameMap() {
		try {
			if (gameMap == null) {
				throw new NullMapException();
			}
			return gameMap;
		} catch (NullMapException err) {
			err.printStackTrace();
			return null;
		}
	}

	public static void setGameMap(GameMap gameMap) {
		GameController.gameMap = gameMap;
	}

	public static List<Pair<Integer, Integer>> getRoomList() {
		return getGameMap().getRoomList();
	}

	public static Player getPlayer() {
		return player;
	}

	public static void setPlayer(Player newPlayer) {
		player = newPlayer;
	}
	
	public static int getLevel() {
		return level;
	}
}
