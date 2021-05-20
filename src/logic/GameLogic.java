package logic;

import controller.GameController;
import controller.InterruptController;
import effects.base.EntityEffect;
import effects.base.IConsecutiveEffect;
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

/**
 * The GameLogic class is the class that processes player action, plays
 * animations, and updates the status of the player and monsters.
 * 
 * @see AnimationUtil
 */
public class GameLogic {
	
	/**
	 * The variable that stores the next action of the player for the purpose of
	 * delay optimization.
	 */
	private static Runnable nextAction = null;

	/**
	 * Setter of next action.
	 * 
	 * @param nextAction The action to be set
	 */
	public static void setNextAction(Runnable nextAction) {
		GameLogic.nextAction = nextAction;
	}

	/**
	 * Does next action if there is no {@link InterruptController interrupt}.
	 */
	public static void doNextAction() {
		if (InterruptController.isInterruptPlayerMovingInput()) {
			return;
		}
		if (nextAction != null) {
			nextAction.run();
		}
		nextAction = null;
	}

	/**
	 * Calculates damage deals from the attacker to the target.
	 * 
	 * @param attacker The entity that perform attack action
	 * @param target   The target entity
	 * @return The damage that the attacker deals to the target
	 */
	public static int calculateAttackValue(Entity attacker, Entity target) {
		int getPercentWeight = (int) (attacker.getCritDamagePercent() * 100);
		int getRand = RandomUtil.random(1, 100);
		int atkCal = 0;

		int lowerBound = (int) (attacker.getAttack() / attacker.getCritRate());
		int upperBound = (int) (attacker.getAttack() * attacker.getCritRate());

		if (getRand <= getPercentWeight) {
			atkCal = upperBound;
		} else {
			double attackerAttack = RandomUtil.random(lowerBound, upperBound);
			double targetDefense = target.getDefense();

			atkCal = (int) Math.max(1, (attackerAttack / (attackerAttack + targetDefense)) * attackerAttack);
		}
		return atkCal;
	}

	/**
	 * Dispatches move or stay still action.
	 * 
	 * @param action The {@link DispatchAction action} to be dispatch
	 */
	public static void gameUpdate(DispatchAction action) {
		Player player = GameController.getPlayer();
		if (InterruptController.isStillAnimation()) {
			setNextAction(() -> {
				gameUpdate(action);
			});
			return;
		}
		if ((InterruptController.isImmobilize()) && (action != DispatchAction.STAY_STILL)) {
			MessageTextUtil.textWhenImmobilized();
			return;
		}
		boolean moveSuccess = false;
		// Dispatches action
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
			MessageTextUtil.textWhenStayStill();
			break;
		default:
			break;
		}

		// If move success, play move animation
		if (moveSuccess) {
			InterruptController.setStillAnimation(true);
			new Thread(() -> {
				try {
					AnimationUtil.playAnimation(2).join();
				} catch (InterruptedException e) {
					System.out.println("Move animation interrupted");
				}
				Platform.runLater(() -> {
					if (action == DispatchAction.STAY_STILL) {
						GameLogic.postMoveUpdate(false);
					} else {
						GameLogic.postMoveUpdate(true);
					}
					postGameUpdate();
				});
			}).start();

		}
	}

	/**
	 * Updates item and checks cell type after the move or stay still action. If
	 * there is an item on the same cell as the player, collect it. If the player is
	 * standing on the ladder cell, move up or down one floor.
	 * 
	 * @param isMove Tell whether the move is a success or not
	 */
	public static void postMoveUpdate(boolean isMove) {
		GameMap thisGameMap = GameController.getGameMap();
		Player player = GameController.getPlayer();
		Cell currentCell = thisGameMap.get(player.getPosY(), player.getPosX());
		Item cellItem = currentCell.getItem();

		// Checks item on the cell
		if ((cellItem != null) && (player.getItemList().size() != GameConfig.MAX_ITEM)) {
			player.getItemList().add(cellItem);
			currentCell.setItem(null);
			MessageTextUtil.textWhenPickUpItem(cellItem);

			// Checks the cell type
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

	/**
	 * Dispatch attack action.
	 * 
	 * @param action  The {@link DispatchAction action} to be dispatch
	 * @param monster The target entity
	 */
	public static void gameUpdate(DispatchAction action, Monster monster) {
		if (InterruptController.isStillAnimation()) {
			setNextAction(() -> {
				gameUpdate(action);
			});
			return;
		}
		Player player = GameController.getPlayer();

		InterruptController.setStillAnimation(true);
		// Dispatches action
		if (player.attack(monster)) {
			monster.setAttacked(true);
		} else {
			MessageTextUtil.textWhenPlayerCannotAttack();
			InterruptController.setStillAnimation(false);
			return;
		}

		// Plays attack animation
		new Thread() {
			public void run() {
				try {
					AnimationUtil.playAnimation(2).join();
				} catch (InterruptedException e) {
					System.out.println("Attack animation interrupted");
					e.printStackTrace();
				}
				Platform.runLater(() -> {
					postGameUpdate();
				});
			}
		}.start();

	}

	/**
	 * Dispatches action about item.
	 * 
	 * @param action The {@link DispatchAction action} to be dispatch
	 * @param item   The target item
	 */
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

	/**
	 * Dispatches delete item action.
	 * 
	 * @param item The item to be deleted
	 */
	private static void deleteItemHandler(Item item) {
		if (item == GameController.getPlayer().getEquippedArmor()) {
			item.onUnequip(GameController.getPlayer());
			GameController.getPlayer().setEquippedArmor(null);
		} else if (item == GameController.getPlayer().getEquippedWeapon()) {
			item.onUnequip(GameController.getPlayer());
			GameController.getPlayer().setEquippedWeapon(null);
		}
		GameController.getPlayer().getItemList().remove(item);
		MessageTextUtil.textWhenDropItem(item);
	}

	/**
	 * Dispatches unequip item action.
	 * 
	 * @param item The item to be unequipped
	 */
	private static void unEquipItemHandler(Item item) {
		Player player = GameController.getPlayer();
		player.unEquipItem(item);
		if (item instanceof Armor) {
			MessageTextUtil.textWhenUnequipArmor((Armor) item);
		} else {
			MessageTextUtil.textWhenUnequipWeapon((Weapon) item);
		}
	}

	/**
	 * Dispatches equip item action.
	 * 
	 * @param item The item to be equipped.
	 */
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

	/**
	 * Dispatches use item action.
	 * 
	 * @param item The item to be used.
	 */
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

	/**
	 * Updates monsters, potion effects, and user interface after player's turn.
	 */
	public static void postGameUpdate() {
		// Updates monsters and potions
		potionUpdate();
		monsterUpdate();

		// Updates user interface
		GameScene.getInventoryPane().update();
		GameScene.getEffectPane().update();
		GameScene.getStatusPane().update();

		// Play monster animations
		new Thread(() -> {
			try {
				AnimationUtil.playAnimation(0).join();
			} catch (InterruptedException e) {
				System.out.println("Post game animation interrupted");
				e.printStackTrace();
			}
			Platform.runLater(() -> {
				if (GameController.isGameOver()) {
					return;
				}
				InterruptController.setStillAnimation(false);
				doNextAction();
			});
		}).start();

	}

	/**
	 * Updates all potion.
	 */
	public static void potionUpdate() {
		Player player = GameController.getPlayer();
		for (Monster each : GameController.getGameMap().getMonsterList()) {
			updateEntityEffect(each);
		}
		updateEntityEffect(player);
	}

	/**
	 * Updates all monsters.
	 */
	public static void monsterUpdate() {
		for (Monster each : GameController.getGameMap().getMonsterList()) {
			each.update();
		}
	}

	/**
	 * Update specified entity's effect.
	 * 
	 * @param entity The entity to be updated
	 */
	private static void updateEntityEffect(Entity entity) {
		for (EntityEffect each : entity.getEffectList()) {
			if (each instanceof IConsecutiveEffect) {
				((IConsecutiveEffect) each).effect(entity);
			}

			if (!each.onUpdate()) {
				each.onWearOff(entity);
				entity.getEffectList().remove(each);
			}
		}
	}

}
