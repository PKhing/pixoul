package logic;

import controller.GameController;
import entity.Player;
import entity.base.DispatchAction;
import entity.base.Entity;
import entity.base.Monster;
import items.base.Armor;
import items.base.IConsecutiveEffect;
import items.base.Item;
import items.base.Potion;
import scene.GameScene;
import utils.GameConfig;
import utils.MessageTextUtil;

public class GameLogic {
	

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
			GameMap thisGameMap = GameController.getGameMap();
			Item cellItem = thisGameMap.get(player.getPosY(), player.getPosX()).getItem();
			if(cellItem != null && player.getItemList().size() != GameConfig.MAX_ITEM) {
				player.getItemList().add(cellItem);
				thisGameMap.get(player.getPosY(), player.getPosX()).setItem(null);
				MessageTextUtil.textWhenPickUpItem(cellItem);
			}
			postGameUpdate();
		}
	}

	public static void gameUpdate(DispatchAction action, Entity entity) {
		Player player = GameController.getPlayer();
		int diffX = Math.abs(player.getPosX() - entity.getPosX());
		int diffY = Math.abs(player.getPosY() - entity.getPosY());

		if (diffX <= 1 && diffY <= 1) {
			player.attack(entity);
			postGameUpdate();
		}
	}

	public static void gameUpdate(DispatchAction action, Item item) {
		Player player = GameController.getPlayer();
		switch (action) {
		case USE_ITEM:
			player.equipItem(item);
			break;
		case SWITCH_EQUIP:
			if(item instanceof Armor) {
				player.getEquippedArmor().onUnequip(player);
			} else {
				player.getEquippedWeapon().onUnequip(player);
			}
			item.onEquip(player);
			break;
		case UNEQUIP:
			player.unEquipItem(item);
			break;
		default:
			return;
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
				each.onUnequip(player);
				player.getItemList().remove(each);
				player.getPotionList().remove(each);
			}
		}
		GameScene.getEffectPane().update();
	}

	public static void monsterUpdate() {
		for (Monster each : GameController.getGameMap().getMonsterList()) {
			each.update();
		}
	}

	public static void postGameUpdate() {
		Player player = GameController.getPlayer();
		monsterUpdate();
		potionUpdate();
		GameScene.getInventoryPane().update();
		GameScene.getStatusPane().setHP(player.getHealth(), player.getMaxHealth());
		GameScene.getStatusPane().setAttack(player.getAttack());
		GameScene.getStatusPane().setDefense(player.getDefense());
		GameController.getGameMap().drawMap();
	}
}
