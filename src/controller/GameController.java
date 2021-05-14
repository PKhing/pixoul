package controller;

import java.util.ArrayList;
import java.util.List;

import entity.Player;
import exception.InvalidFloorException;
import exception.NullMapException;
import items.armor.WoodenArmor;
import items.base.Potion;
import items.potion.InstantHealPotion;
import items.weapon.Knife;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Pair;
import logic.GameMap;
import logic.MapGenerator;
import logic.MapRenderer;
import scene.GameOverScene;
import scene.GameScene;
import utils.GameAudioUtils;
import utils.TransitionUtil;

/**
 * The GameController class is the class that control about the floor 
 * which currently render and the floor changing inside the game
 */
public class GameController {
	
	/**
	 * The {@link ArrayList} represents the floor of each level
	 */
	private static ArrayList<GameMap> floorList = new ArrayList<>();
	
	/**
	 * Represent the {@link GameMap} of current floor
	 */
	private static GameMap gameMap;

	/**
	 * The {@link MediaPlayer} represent the background music of GameScene 
	 */
	private static MediaPlayer bgm = GameAudioUtils.GameSceneBGM;

	/**
	 * Represent the level of current floor 
	 */
	private static int level;
	
	/**
	 * Represent the current {@link Player} instance 
	 */
	private static Player player;

	/**
	 * Getter {@link GameMap} by floor level number method
	 * 
	 * @param floor the number which want to get map.
	 * @return {@link GameMap} map of current floor
	 * @throws InvalidFloorException throw if floor is invalid
	 */
	public static GameMap getFloor(int floor) throws InvalidFloorException {
		if ((floorList.size() < floor) || (floor <= 0)) {
			throw new InvalidFloorException();
		}
		return floorList.get(floor - 1);
	}

	/**
	 * Create new {@link GameMap} and add to floorList
	 * 
	 * @return {@link GameMap} new floor which added to floorList
	 */
	private static GameMap addNewFloor() {
		GameMap newFloor = MapGenerator.generateMap();
		floorList.add(newFloor);
		return newFloor;
	}

	/**
	 * Change {@link #gameMap} to lower level and making fade transition if able to do
	 * 
	 * @return return true if {@link #player} can go to lower level otherwise false
	 */
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


	/**
	 * Change {@link #gameMap} to upper level and making fade transition if able to do
	 * 
	 * @return return true if {@link #player} can go to upper level otherwise false
	 */
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
	
	/**
	 * Initialize new game
	 */
	public static void start() {
		floorList.clear();
		level = 1;

		GameMap newFloor = addNewFloor();
		setGameMap(newFloor);

		player = makeNewPlayer();

		sceneSetup();
		initialTransition();
	}

	/**
	 * Stop the game background music then 
	 * making fade transition to {@link LandingScene}
	 */
	public static void exitToMainMenu() {
		FadeTransition fadeOut = TransitionUtil.makeFadingNode(GameScene.getGamePane(), 1.0, 0.0);

		bgm.stop();

		fadeOut.setOnFinished((event) -> SceneController.backToMainMenu());

		fadeOut.play();
	}

	/**
	 * Checking condition that {@link #player} is currently 
	 * Game over or not by checking {@link #player} health
	 * 
	 * @return true if {@link #player} health is less than or equals 0 otherwise false
	 */
	public static boolean isGameOver() {
		if (player.getHealth() <= 0) {
			bgm.stop();
			FadeTransition fadeOut = TransitionUtil.makeFadingNode(GameScene.getGamePane(), 1.0, 0.0);

			InterruptController.setTransition(true);
			fadeOut.setOnFinished((event) -> {
				SceneController.setSceneToStage(GameOverScene.getScene());
			});

			fadeOut.play();
			return true;
		}
		return false;
	}

	/**
	 * Getter for {@link #gameMap}
	 * 
	 * @return {@link #gameMap}
	 */
	public static GameMap getGameMap() {
		try {
			if (gameMap == null) {
				throw new NullMapException();
			}
			return gameMap;
		} catch (NullMapException err) {
			return null;
		}
	}

	/**
	 * Setter for {@link #gameMap}
	 * 
	 * @param {@link GameMap}
	 */
	public static void setGameMap(GameMap gameMap) {
		GameController.gameMap = gameMap;
	}

	/**
	 * Get roomList from {@link #gameMap}
	 * 
	 * @return {@link #floorList}
	 */
	public static List<Pair<Integer, Integer>> getRoomList() {
		return getGameMap().getRoomList();
	}

	/**
	 * Getter for {@link #player}
	 * 
	 * @return {@link #player}
	 */
	public static Player getPlayer() {
		return player;
	}
	
	/**
	 * Setter for {@link #player}
	 * 
	 * @param newPlayer
	 */
	public static void setPlayer(Player newPlayer) {
		player = newPlayer;
	}

	/**
	 * Getter for {@link #level}
	 * 
	 * @return {@link #level}
	 */
	public static int getLevel() {
		return level;
	}

	/**
	 * Utility method that creating FadingAnimation for switching floor
	 * 
	 * @param node the target node that we want to make a fade
	 * @param from starting opacity 
	 * @param to ending opacity
	 * @param newMap the map that we want to render
	 * @param isAscending true if the type of switching floor is ascending otherwise false
	 * @return {@link FadeTransition} instance which used for making transition between floor
	 */
	private static FadeTransition makeFadingScene(Node node, double from, double to, GameMap newMap,
			boolean isAscending) {
		// Fade in, Fade out setup
		FadeTransition fadeFirst = TransitionUtil.makeFadingNode(GameScene.getGamePane(), from, to);
		FadeTransition fadeSecond = TransitionUtil.makeFadingNode(GameScene.getGamePane(), to, from);

		// Fade out when finished
		fadeSecond.setOnFinished((event) -> InterruptController.setTransition(false));

		// Fade in when finished
		fadeFirst.setOnFinished((event) -> {
			gameMap.get(player.getPosY(), player.getPosX()).setEntity(null);

			setGameMap(newMap);
			List<Pair<Integer, Integer>> roomList = newMap.getRoomList();

			int idxPos = 0;

			if (isAscending) {
				idxPos = roomList.size() - 1;
			}

			int posX = roomList.get(idxPos).getValue();
			int posY = roomList.get(idxPos).getKey();

			player.setPositionOnMap(posY, posX);
			newMap.get(posY, posX).setEntity(player);
			MapRenderer.render();
			fadeSecond.play();
		});

		return fadeFirst;
	}

	/**
	 * Setup {@link GameScene} when start or restart game
	 */
	private static void sceneSetup() {
		InterruptController.resetInterruptState();
		GameScene.getMessagePane().resetMessage();
		GameScene.getEffectPane().update();
		GameScene.getStatusPane().update();
		GameScene.getInventoryPane().update();
		SceneController.setSceneToStage(GameScene.getScene());
	}

	/**
	 * Create new {@link Player} instance and register to the {@link GameMap}
	 * 
	 * @return {@link Player} new player instance
	 */
	private static Player makeNewPlayer() {
		Player newPlayer = new Player();
		Pair<Integer, Integer> firstRoomPos = GameController.getRoomList().get(0);

		newPlayer.setPositionOnMap(firstRoomPos.getKey(), firstRoomPos.getValue());
		new Knife("Rusty Knife", "A rusty knife which dungeon guard has given [2]", 2, 1).onEquip(newPlayer);
		new WoodenArmor("Wooden Armor", "With 100 years salt effect [5]", 5, 0).onEquip(newPlayer);

		Potion maxHealthPotion = new InstantHealPotion("Max Healing Potion",
				"This potion will heal you to max health (have only one per game) [?]", 0, 0, false, 0);
		
		newPlayer.getItemList().add(maxHealthPotion);

		return newPlayer;
	}

	/**
	 * Create new {@link FadeTransition} then play transition along with background music
	 */
	private static void initialTransition() {
		GameScene.getGamePane().setOpacity(0.0);

		InterruptController.setTransition(true);
		FadeTransition fadeIn = TransitionUtil.makeFadingNode(GameScene.getGamePane(), 0.0, 1.0);

		MapRenderer.render();

		fadeIn.play();
		fadeIn.setOnFinished((event) -> InterruptController.setTransition(false));

		bgm.play();
	}
}
