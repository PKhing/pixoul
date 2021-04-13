package controller;

import java.util.ArrayList;
import java.util.List;

import components.GameOverPane;
import entity.Player;
import exception.InvalidFloorException;
import exception.NullMapException;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.util.Pair;
import logic.GameMap;
import logic.MapGenerator;
import scene.GameOverScene;
import scene.GameScene;
import utils.GameAudioUtils;
import utils.TransitionUtil;

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
		GameMap newMap = null;

		try {
			newMap = getFloor(level);
		} catch (InvalidFloorException e) {
			newMap = addNewFloor();
		}

		FadeTransition fadeOut = makeFadingScene(GameScene.getGamePane(), 1.0, 0.0, newMap, false);

		fadeOut.play();
		InterruptController.setTransition(true);

		return true;
	}

	public static boolean ascending() {
		try {
			GameMap newMap = getFloor(level - 1);

			FadeTransition fadeOut = makeFadingScene(GameScene.getGamePane(), 1.0, 0.0, newMap, true);

			fadeOut.play();
			InterruptController.setTransition(true);
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

		GameMap newFloor = addNewFloor();
		InterruptController.resetInterruptState();
		setGameMap(newFloor);

		GameScene.getMessagePane().resetMessage();
		GameScene.getEffectPane().update();
		GameScene.setPlayerPositionOnNewMap();
		GameScene.getStatusPane().setAllValue(GameController.getPlayer());
		SceneController.setSceneToStage(GameScene.getScene());
		GameScene.getInventoryPane().update();

		GameScene.getGamePane().setOpacity(0.0);

		InterruptController.setTransition(true);
		FadeTransition fadeIn = TransitionUtil.makeFadingNode(GameScene.getGamePane(), 0.0, 1.0);

		newFloor.drawMap();

		fadeIn.play();
		fadeIn.setOnFinished((event) -> InterruptController.setTransition(false));

		bgmMedia.play();
	}

	public static void exitToMainMenu() {
		FadeTransition fadeOut = TransitionUtil.makeFadingNode(GameScene.getGamePane(), 1.0, 0.0);

		bgmMedia.stop();

		fadeOut.setOnFinished((event) -> SceneController.backToMainMenu());

		fadeOut.play();
	}

	public static void isGameOver() {
		if(player.getHealth() <= 0) {
			bgmMedia.stop();
			FadeTransition fadeOut = TransitionUtil.makeFadingNode(GameScene.getGamePane(), 1.0, 0.0);
			
			InterruptController.setTransition(true);
			fadeOut.setOnFinished((event) -> {	
				SceneController.setSceneToStage(GameOverScene.getScene());
			});
			
			fadeOut.play();
		}
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

	private static FadeTransition makeFadingScene(Node node, double from, double to, GameMap newMap,
			boolean isAscending) {
		// Fade in, Fade out setup
		FadeTransition fadeFirst = TransitionUtil.makeFadingNode(GameScene.getGamePane(), from, to);
		FadeTransition fadeSecond = TransitionUtil.makeFadingNode(GameScene.getGamePane(), to, from);

		fadeSecond.setFromValue(from);
		fadeSecond.setToValue(to);

		fadeFirst.setFromValue(to);
		fadeFirst.setToValue(from);

		// Fade out when finished
		fadeSecond.setOnFinished((event) -> {
			gameMap.get(player.getPosY(), player.getPosX()).setEntity(null);

			setGameMap(newMap);
			List<Pair<Integer, Integer>> roomList = newMap.getRoomList();

			int idxPos = 0;

			if (isAscending) {
				idxPos = roomList.size() - 1;
			}

			int posX = roomList.get(idxPos).getValue();
			int posY = roomList.get(idxPos).getKey();

			player.setInitialPos(posY, posX);
			newMap.get(posY, posX).setEntity(player);
			newMap.drawMap();
			fadeFirst.play();
		});

		// Fade in when finished
		fadeFirst.setOnFinished((event) -> InterruptController.setTransition(false));

		return fadeSecond;
	}
}
