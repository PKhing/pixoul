package controller;

import java.util.ArrayList;

import exception.InvalidFloorException;
import javafx.scene.media.MediaPlayer;
import scene.GameScene;
import utils.GameAudioUtils;

public class GameController {
	private static ArrayList<GameScene> floorList = new ArrayList<>();
	private static MediaPlayer bgmMedia = GameAudioUtils.getGameSceneAudioLoop();
	private static int level;

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
	}
	
	public static void gameover() {
		return;
	}
}
