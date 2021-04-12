package logic;

import controller.GameController;
import controller.InterruptController;
import effects.EntityEffect;
import effects.IConsecutiveEffect;
import entity.Player;
import entity.base.DispatchAction;
import entity.base.Entity;
import entity.base.Monster;
import items.base.Armor;
import items.base.Item;
import items.base.Potion;
import items.base.Weapon;
import scene.GameScene;
import utils.AnimationUtil;
import utils.GameConfig;
import utils.MessageTextUtil;
import utils.RandomUtil;

public class GameLogic {
	private static Runnable nextAction = null;

	public static void doNextAction() {
		if (InterruptController.isInterruptPlayerInput())
			return;
		if (nextAction != null) {
			nextAction.run();
		}
		nextAction = null;
	}

	public static int calculateAttackValue(Entity from, Entity target) {
		int lowerBound = (int) (from.getAttack() / from.getCritRate());
		int upperBound = (int) (from.getAttack() * from.getCritRate());

		double fromAttack = RandomUtil.random(lowerBound, upperBound);
		double targetDefense = target.getDefense();

		int atkCal = (int) Math.max(1, (fromAttack / (fromAttack + targetDefense)) * fromAttack);

		return atkCal;
	}

	public static void gameUpdate(DispatchAction action) {
		Player player = GameController.getPlayer();
		if (InterruptController.isStillAnimation()) {
			nextAction = new Runnable() {
				@Override
				public void run() {
					gameUpdate(action);
				}
			};
			return;
		}
		if (InterruptController.isImmobilize() && action != DispatchAction.STAY_STILL) {
			MessageTextUtil.textWhenImmobilized();
			return;
		}
		switch (action) {
		case MOVE_UP:
			player.move(Direction.UP);
			break;
		case MOVE_DOWN:
			player.move(Direction.DOWN);
			break;
		case MOVE_LEFT:
			player.move(Direction.LEFT);
			break;
		case MOVE_RIGHT:
			player.move(Direction.RIGHT);
			break;
		case STAY_STILL:
			postGameUpdate();
			postMoveUpdate();
			break;
		default:
			break;
		}
	}

	public static void postMoveUpdate() {
		GameMap thisGameMap = GameController.getGameMap();
		Player player = GameController.getPlayer();
		Cell currentCell = thisGameMap.get(player.getPosY(), player.getPosX());
		Item cellItem = currentCell.getItem();

		if (cellItem != null && player.getItemList().size() != GameConfig.MAX_ITEM) {
			player.getItemList().add(cellItem);
			currentCell.setItem(null);
			MessageTextUtil.textWhenPickUpItem(cellItem);
		} else if (currentCell.getType() == Cell.LADDER_UP) {
			boolean isAscending = GameController.ascending();
			int level = GameController.getLevel();
			if (!isAscending) {
				level = 0;
			}
			MessageTextUtil.textWhenAscending(level);
		} else if (currentCell.getType() == Cell.LADDER_DOWN) {
			GameController.descending();
			MessageTextUtil.textWhenDescending(GameController.getLevel());
		}
	}

	public static void gameUpdate(DispatchAction action, Monster monster) {
		if (InterruptController.isStillAnimation()) {
			nextAction = new Runnable() {
				@Override
				public void run() {
					gameUpdate(action, monster);
				}
			};
			return;
		}
		Player player = GameController.getPlayer();
		int diffX = Math.abs(player.getPosX() - monster.getPosX());
		int diffY = Math.abs(player.getPosY() - monster.getPosY());

		if (diffX <= 1 && diffY <= 1) {
			InterruptController.setStillAnimation(true);
			player.attack(monster);
			AnimationUtil.monsterAttacked(monster);
		}
	}

	public static void gameUpdate(DispatchAction action, Item item) {
		Player player = GameController.getPlayer();
		switch (action) {
		case USE_ITEM:
			player.equipItem(item);
			if (item instanceof Armor) {
				MessageTextUtil.textWhenEquipArmor((Armor) item);
			} else if (item instanceof Weapon) {
				MessageTextUtil.textWhenEquipWeapon((Weapon) item);
			} else {
				MessageTextUtil.textWhenUsedPotion((Potion) item);
			}
			break;
		case SWITCH_EQUIP:
			Item beforeSwitch = null;
			if (item instanceof Armor) {
				beforeSwitch = player.getEquippedArmor();
				player.unEquipItem(beforeSwitch);
				MessageTextUtil.textWhenSwitchArmor((Armor) item);
			} else {
				beforeSwitch = player.getEquippedWeapon();
				player.unEquipItem(beforeSwitch);
				MessageTextUtil.textWhenSwitchWeapon((Weapon) item);
			}
			player.equipItem(item);

			break;
		case UNEQUIP:
			player.unEquipItem(item);
			if (item instanceof Armor) {
				MessageTextUtil.textWhenUnequipArmor((Armor) item);
			} else {
				MessageTextUtil.textWhenUnequipWeapon((Weapon) item);
			}
			break;
		case DELETE_ITEM:
			if (item == GameController.getPlayer().getEquippedArmor()) {
				GameController.getPlayer().setEquippedArmor(null);
			} else if (item == GameController.getPlayer().getEquippedWeapon()) {
				GameController.getPlayer().setEquippedWeapon(null);
			}
			GameController.getPlayer().getItemList().remove(item);
			MessageTextUtil.textWhenDropItem(item);
			break;
		default:
			return;
		}
		postGameUpdate();
	}

	public static void potionUpdate() {
		Player player = GameController.getPlayer();
		for (Monster each : GameController.getGameMap().getMonsterList()) {
			updateEntityEffect(each);
		}
		updateEntityEffect(player);
		GameScene.getEffectPane().update();
	}

	public static void monsterUpdate() {
		for (Monster each : GameController.getGameMap().getMonsterList()) {
			each.update();
		}
	}

	public static void postGameUpdate() {
		InterruptController.setStillAnimation(true);
		Player player = GameController.getPlayer();
		monsterUpdate();
		potionUpdate();
		GameScene.getInventoryPane().update();
		GameScene.getStatusPane().setHP(player.getHealth(), player.getMaxHealth());
		GameScene.getStatusPane().setAttack(player.getAttack());
		GameScene.getStatusPane().setDefense(player.getDefense());
		AnimationUtil.postGame();
		if (!InterruptController.isTransition()) {
			// GameController.getGameMap().drawMap();
		}
	}

	private static void updateEntityEffect(Entity entity) {
		for (EntityEffect each : entity.getEffectList()) {
			if (each instanceof IConsecutiveEffect) {
				((IConsecutiveEffect) each).effect(entity);
			}

			if (!each.onUpdate(entity)) {
				each.onWearOff(entity);
				entity.getEffectList().remove(each);
			}
		}
	}
}
