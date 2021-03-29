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
		if(floorList.size() < floor) {
			throw new InvalidFloorException();
		}
		return floorList.get(floor - 1);
	}
	
	private static void addNewFloor() {
		floorList.add(new GameScene());
	}

	public static void reset() {
		floorList.clear();
		level = 1;
	}
	
	public static void exit() {
		bgmMedia.stop();
	}
	
	public static void start() {
		reset();
		try {
			addNewFloor();
			SceneController.setSceneToStage(getFloor(level).getScene());
		} catch (InvalidFloorException e) {
			e.printStackTrace();
			return;
		}
		bgmMedia.play();
	}
}
