package controller;

import java.util.ArrayList;

import entity.Player;
import exception.InvalidFloorException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Pair;
import logic.GameMap;
import scene.GameScene;
import scene.LandingScene;
import utils.GameAudioUtils;

public class GameController {
	private static ArrayList<GameScene> floorList = new ArrayList<>();
	private static MediaPlayer bgmMedia = GameAudioUtils.GameSceneBGM;
	private static int level;
	private static GameMap gameMap;
	private static final Player player = new Player();

	private static GameScene getFloor(int floor) throws InvalidFloorException {
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
		if (level == 1) {
			return false;
		}

		level -= 1;

		try {
			SceneController.setSceneToStage(getFloor(level).getScene());
		} catch (InvalidFloorException e) {
			return false;
		}
		return true;
	}

	public static void reset() {
		floorList.clear();
		level = 1;
	}

	public static void start() {
		reset();
		GameScene newFloor = addNewFloor();
		SceneController.setSceneToStage(newFloor.getScene());
		bgmMedia.play();
	}

	public static void exit() {
		bgmMedia.stop();
		SceneController.setSceneToStage(LandingScene.getScene());
	}

	public static void gameover() {
		return;
	}

	public static GameMap getGameMap() {
		// TODO null map exception
		return gameMap;
	}

	public static void setGameMap(GameMap gameMap) {
		GameController.gameMap = gameMap;
	}

	public static ArrayList<Pair<Integer, Integer>> getRoomList() {
		return getGameMap().getRoomList();
	}

	public static Player getPlayer() {
		return player;
	}
}
