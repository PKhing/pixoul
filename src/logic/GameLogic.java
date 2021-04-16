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
import javafx.application.Platform;
import scene.GameScene;
import utils.AnimationUtil;
import utils.GameConfig;
import utils.MessageTextUtil;
import utils.RandomUtil;

public class GameLogic {
	private static Runnable nextAction = null;

	public static void doNextAction() {
		if (InterruptController.isInterruptPlayerInput()) {
			return;
		}
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
		if ((InterruptController.isImmobilize()) && (action != DispatchAction.STAY_STILL)) {
			MessageTextUtil.textWhenImmobilized();
			return;
		}
		boolean moveSuccess = false;
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
			moveSuccess = true;
			MessageTextUtil.textWhenStayStill(player);
			break;
		default:
			break;
		}
		if (moveSuccess) {
			InterruptController.setStillAnimation(true);
			new Thread() {
				public void run() {
					try {
						AnimationUtil.playAnimation(2).join();
					} catch (InterruptedException e) {
						System.out.println("animation interrupted");
					}
					Platform.runLater(() -> {
						if (action == DispatchAction.STAY_STILL) {
							GameLogic.postMoveUpdate(false);
						} else {
							GameLogic.postMoveUpdate(true);
						}
						postGameUpdate();
					});
				}
			}.start();

		}
	}

	public static void postMoveUpdate(boolean isMove) {
		GameMap thisGameMap = GameController.getGameMap();
		Player player = GameController.getPlayer();
		Cell currentCell = thisGameMap.get(player.getPosY(), player.getPosX());
		Item cellItem = currentCell.getItem();

		if ((cellItem != null) && (player.getItemList().size() != GameConfig.MAX_ITEM)) {
			player.getItemList().add(cellItem);
			currentCell.setItem(null);
			MessageTextUtil.textWhenPickUpItem(cellItem);
		} else if ((currentCell.getType() == Cell.LADDER_UP) && isMove) {
			boolean isAscending = GameController.ascending();
			int level = GameController.getLevel();
			if (!isAscending) {
				level = 0;
			}
			MessageTextUtil.textWhenAscending(level);
		} else if ((currentCell.getType() == Cell.LADDER_DOWN) && isMove) {
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
			monster.setAttacked(true);
			InterruptController.setStillAnimation(true);
			new Thread() {
				public void run() {
					try {
						AnimationUtil.playAnimation(2).join();
					} catch (InterruptedException e) {
						System.out.println("animation interrupted");
						e.printStackTrace();
					}
					Platform.runLater(() -> {
						postGameUpdate();
					});
				}
			}.start();
		}

	}

	public static void gameUpdate(DispatchAction action, Item item) {
		switch (action) {
		case USE_ITEM:
			useItemHandler(item);
			break;
		case SWITCH_EQUIP:
			switchEquipmentHandler(item);
			break;
		case UNEQUIP:
			unEquipItemHandler(item);
			break;
		case DELETE_ITEM:
			deleteItemHandler(item);
			break;
		default:
			return;
		}
		InterruptController.setStillAnimation(true);
		postGameUpdate();
	}

	public static void potionUpdate() {
		Player player = GameController.getPlayer();
		for (Monster each : GameController.getGameMap().getMonsterList()) {
			updateEntityEffect(each);
		}
		updateEntityEffect(player);
	}

	public static void monsterUpdate() {
		for (Monster each : GameController.getGameMap().getMonsterList()) {
			each.update();
		}
	}

	public static void postGameUpdate() {
		Player player = GameController.getPlayer();
		potionUpdate();
		monsterUpdate();
		GameScene.getInventoryPane().update();
		GameScene.getEffectPane().update();
		GameScene.getStatusPane().setAllValue(player);
		new Thread() {
			public void run() {
				try {
					AnimationUtil.playAnimation(0).join();
				} catch (InterruptedException e) {
					System.out.println("post game animation interrupted");
					e.printStackTrace();
				}
				Platform.runLater(() -> {
					GameController.getGameMap().drawMap();
					InterruptController.setStillAnimation(false);
					doNextAction();
				});
			}
		}.start();

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

	private static void deleteItemHandler(Item item) {
		if (item == GameController.getPlayer().getEquippedArmor()) {
			GameController.getPlayer().setEquippedArmor(null);
		} else if (item == GameController.getPlayer().getEquippedWeapon()) {
			GameController.getPlayer().setEquippedWeapon(null);
		}
		GameController.getPlayer().getItemList().remove(item);
		MessageTextUtil.textWhenDropItem(item);
	}

	private static void unEquipItemHandler(Item item) {
		Player player = GameController.getPlayer();
		player.unEquipItem(item);
		if (item instanceof Armor) {
			MessageTextUtil.textWhenUnequipArmor((Armor) item);
		} else {
			MessageTextUtil.textWhenUnequipWeapon((Weapon) item);
		}
	}

	private static void switchEquipmentHandler(Item item) {
		Player player = GameController.getPlayer();
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
	}

	private static void useItemHandler(Item item) {
		Player player = GameController.getPlayer();
		player.equipItem(item);
		if (item instanceof Armor) {
			MessageTextUtil.textWhenEquipArmor((Armor) item);
		} else if (item instanceof Weapon) {
			MessageTextUtil.textWhenEquipWeapon((Weapon) item);
		} else {
			MessageTextUtil.textWhenUsedPotion((Potion) item);
		}
	}
}
