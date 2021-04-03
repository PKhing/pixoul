package controller;

import java.util.ArrayList;

import entity.Player;
import entity.base.DispatchAction;
import entity.base.Entity;
import entity.base.Monster;
import exception.InvalidFloorException;
import exception.NullMapException;
import items.base.IConsecutiveEffect;
import items.base.Item;
import items.base.Potion;
import javafx.scene.media.MediaPlayer;
import javafx.util.Pair;
import logic.Direction;
import logic.GameMap;
import scene.GameScene;
import utils.GameAudioUtils;

public class GameController {
	private static ArrayList<GameScene> floorList = new ArrayList<>();
	private static MediaPlayer bgmMedia = GameAudioUtils.GameSceneBGM;
	private static int level;
	private static GameMap gameMap;
	private static Player player = new Player();

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

	public static ArrayList<Pair<Integer, Integer>> getRoomList() {
		return getGameMap().getRoomList();
	}

	public static Player getPlayer() {
		return player;
	}

	public static void setPlayer(Player newPlayer) {
		player = newPlayer;
	}

	/*
	 * Order of action
	 * 
	 * Update Potion -> Use Potion / Equip weapon, armor (If have) -> Player Move / Attack (If have) -> Update Monster
	 * Health / Death -> Monster Turn -> Update Player Health / Death
	 */
	public static void gameUpdate(DispatchAction action) {
		boolean moveSuccess = true;
		switch(action) {
		case MOVE_UP:
			moveSuccess = getPlayer().move(Direction.UP);
			break;
		case MOVE_DOWN:
			moveSuccess = getPlayer().move(Direction.DOWN);
			break;
		case MOVE_LEFT:
			moveSuccess = getPlayer().move(Direction.LEFT);
			break;
		case MOVE_RIGHT:
			moveSuccess = getPlayer().move(Direction.RIGHT);
			break;
		case STAY_STILL:
			break;
		case ATTACK:
			break;
		default:
			break;
		}
		if(moveSuccess) {
			postGameUpdate();
		}
	}
	
	public static void gameUpdate(DispatchAction action, Entity entity) {
		int diffX = Math.abs(player.getPosX() - entity.getPosX());
		int diffY = Math.abs(player.getPosY() - entity.getPosY());
		
		if(diffX <= 1 && diffY <= 1) {
			System.out.println("Action attack");
			player.attack(entity);
			postGameUpdate();
		}
	}
	
	public static void gameUpdate(DispatchAction action, Item item) {
		switch(action) {
		case USEITEM:
			player.equipItem(item);
			break;
		case DEEQUIP:
			player.deEquipItem(item);
			break;
		default:
			break;
		}
		postGameUpdate();
	}
	
	public static void potionUpdate() {
		for (Potion each : player.getPotionList()) {
			if (each instanceof IConsecutiveEffect) {
				((IConsecutiveEffect) each).effect(player);
			}

			if (!each.update()) {
				each.onDeequip(player);
				player.getPotionList().remove(each);
			}
		}
	}
	
	public static void monsterUpdate() {
		for(Monster each: gameMap.getMonsterList()) {
			each.update();
		}
	}
	
	private static void postGameUpdate() {
		monsterUpdate();
		potionUpdate();
	}
}
