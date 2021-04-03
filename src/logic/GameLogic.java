package logic;

import java.util.ArrayList;

import controller.GameController;
import entity.Player;
import entity.base.DispatchAction;
import entity.base.Entity;
import entity.base.Monster;
import exception.InvalidFloorException;
import items.base.IConsecutiveEffect;
import items.base.Item;
import items.base.Potion;
import scene.GameScene;

public class GameLogic {
	private static ArrayList<Item> itemList = new ArrayList<Item>();

	public static ArrayList<Item> getItemList() {
		return itemList;
	}

	public static void setItemList(ArrayList<Item> itemList) {
		GameLogic.itemList = itemList;
	}
	
	public static int calculateAttackValue(Entity from, Entity target) {
		double fromAttack = from.getAttack();
		double targetDefense = target.getDefense();
		
		int atkCal = (int) Math.max(1, (fromAttack / (fromAttack + targetDefense)) * fromAttack);
		
		return atkCal;
	}
	
	public static void gameUpdate(DispatchAction action) {
		boolean moveSuccess = true;
		Player player = GameController.getPlayer();
		switch (action) {
		case MOVE_UP:
			moveSuccess = player.move(Direction.UP);
			break;
		case MOVE_DOWN:
			moveSuccess = player.move(Direction.DOWN);
			break;
		case MOVE_LEFT:
			moveSuccess = player.move(Direction.LEFT);
			break;
		case MOVE_RIGHT:
			moveSuccess = player.move(Direction.RIGHT);
			break;
		case STAY_STILL:
			break;
		default:
			break;
		}
		if (moveSuccess) {
			postGameUpdate();
		}
	}

	public static void gameUpdate(DispatchAction action, Entity entity) {
		Player player = GameController.getPlayer();
		int diffX = Math.abs(player.getPosX() - entity.getPosX());
		int diffY = Math.abs(player.getPosY() - entity.getPosY());

		if (diffX <= 1 && diffY <= 1) {
			System.out.println("Action attack");
			player.attack(entity);
			postGameUpdate();
		}
	}

	public static void gameUpdate(DispatchAction action, Item item) {
		Player player = GameController.getPlayer();
		switch (action) {
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
		Player player = GameController.getPlayer();
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
		for (Monster each : GameController.getGameMap().getMonsterList()) {
			each.update();
		}
	}

	private static void postGameUpdate() {
		Player player = GameController.getPlayer();
		monsterUpdate();
		potionUpdate();
		try {
			GameScene gs = GameController.getFloor(GameController.getLevel());
			gs.setHPText(player.getHealth(), player.getMaxHealth());
			gs.setAttackText(player.getAttack());
			gs.setDefenseText(player.getDefense());
		} catch (InvalidFloorException e) {
			System.out.println("Invalid floor");
		}
	}
}
