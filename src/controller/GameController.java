package controller;

import java.util.ArrayList;
import java.util.List;

import entity.Player;
import exception.InvalidFloorException;
import exception.NullMapException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Pair;
import logic.GameMap;
import scene.GameScene;
import utils.GameAudioUtils;

public class GameController {
	private static ArrayList<GameScene> floorList = new ArrayList<>();
	private static MediaPlayer bgmMedia = GameAudioUtils.GameSceneBGM;
	private static int level;
	private static GameMap gameMap;
	private static Player player = new Player();

	public static GameScene getFloor(int floor) throws InvalidFloorException {
		if (floorList.size() < floor || floor <= 0) {
			throw new InvalidFloorException();
		}
		return floorList.get(floor - 1);
	}

	private static GameScene addNewFloor() {
		GameScene newFloor = new GameScene();
		floorList.add(newFloor);
		return newFloor;
	}

	public static boolean descending() {
		level += 1;
		try {
			SceneController.setSceneToStage(getFloor(level).getScene());
		} catch (InvalidFloorException e) {
			GameScene newFloor = addNewFloor();
			SceneController.setSceneToStage(newFloor.getScene());
		}
		return true;
	}

	public static boolean ascending() {
		try {
			SceneController.setSceneToStage(getFloor(level - 1).getScene());
		} catch (InvalidFloorException e) {
			return false;
		}

		level -= 1;
		return true;
	}

	public static void reset() {
		floorList.clear();
		player = new Player();
		level = 1;
	}

	public static void start() {
		reset();
		GameScene newFloor = addNewFloor();
		SceneController.setSceneToStage(newFloor.getScene());
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
