package controller;

import java.util.ArrayList;

import components.GameAudio;
import javafx.scene.media.MediaPlayer;
import scene.GameScene;

public class GameController {
	private static ArrayList<GameScene> floorList = new ArrayList<>();
	private static MediaPlayer bgmMedia = GameAudio.getGameSceneAudioLoop();
	private static int level;
	
	private static GameScene getFloor(int floor) {
		if(floorList.size() <= floor) {
			addNewFloor();
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
		SceneController.setSceneToStage(getFloor(level).getScene());
		bgmMedia.play();
	}
}
