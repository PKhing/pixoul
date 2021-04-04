package scene;

import components.EffectPane;
import components.EquipmentPane;
import components.InventoryPane;
import components.MessagePane;
import components.StatusPane;
import components.PausePane;
import controller.GameController;
import controller.InterruptController;
import entity.Skeleton;
import entity.base.DispatchAction;
import items.potion.HealingPotion;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import utils.GameConfig;
import javafx.util.Pair;
import logic.Direction;
import logic.GameLogic;
import utils.DrawUtil;

public class GameScene {
	private static Scene scene = null;
	private static StatusPane statusPane;
	private static MessagePane messagePane;
	private static EffectPane effectPane;
	private static InventoryPane inventoryPane;
	private static PausePane pausePane;
	private static AnchorPane buttonPane;
	private static EquipmentPane equipmentPane;
	private static GraphicsContext gc;

	private static void initScene() {
		Pair<Integer, Integer> firstRoomPos = GameController.getRoomList().get(0);
		GameController.getPlayer().setInitialPos(firstRoomPos.getKey(), firstRoomPos.getValue());

		Skeleton skeleton = new Skeleton(1, 10, 1, firstRoomPos.getKey(), firstRoomPos.getValue() + 1, Direction.DOWN,
				0, 0, 1);
		skeleton.setHealth(8);
		GameController.getGameMap().getMonsterList().add(skeleton);
		GameLogic.getItemList().add(new HealingPotion("Salty Potion", "With 100 years salt effect", 10, 100));
		GameController.getPlayer().equipItem(new HealingPotion("Salty Potion", "With 100 years salt effect", 10, 100));

		StackPane root = new StackPane();
		scene = new Scene(root, GameConfig.getScreenWidth(), GameConfig.getScreenHeight());

		Canvas canvas = new Canvas(GameConfig.getScreenWidth(), GameConfig.getScreenHeight());
		gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);

		buttonPane = new AnchorPane();
		root.getChildren().add(buttonPane);
		addEventListener(scene, gc, buttonPane);

		// Overlay
		AnchorPane overlay = new AnchorPane();
		statusPane = new StatusPane();
		overlay.getChildren().add(statusPane);
		messagePane = new MessagePane();
		overlay.getChildren().add(messagePane);
		effectPane = new EffectPane();
		overlay.getChildren().add(effectPane);
		overlay.setPickOnBounds(false);
		root.getChildren().add(overlay);

		// Inventory Button
		Button inventoryBtn = new Button();

		inventoryBtn.setStyle("-fx-margin:0;-fx-padding:0");
		inventoryBtn.setPrefHeight(30.0 * GameConfig.getScale());
		inventoryBtn.setPrefWidth(30.0 * GameConfig.getScale());
		Canvas backpack = new Canvas(32 * GameConfig.getScale(), 32 * GameConfig.getScale());
		DrawUtil.drawBackpack(backpack.getGraphicsContext2D());
		inventoryBtn.setGraphic(backpack);
		inventoryBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				root.getChildren().add(inventoryPane);
				overlay.getChildren().add(equipmentPane);
				inventoryPane.requestFocus();
				InterruptController.setInventoryOpen(true);
			}
		});
		AnchorPane.setBottomAnchor(inventoryBtn, 5.0 * GameConfig.getScale());
		AnchorPane.setRightAnchor(inventoryBtn, 5.0 * GameConfig.getScale());

		overlay.getChildren().add(inventoryBtn);

		// Pause Button
		Button pauseBtn = new Button();
		pauseBtn.setStyle("-fx-margin:0;-fx-padding:0");
		AnchorPane.setTopAnchor(pauseBtn, 5.0 * GameConfig.getScale());
		AnchorPane.setRightAnchor(pauseBtn, 5.0 * GameConfig.getScale());
		pauseBtn.setPrefHeight(15.0 * GameConfig.getScale());
		pauseBtn.setPrefWidth(15.0 * GameConfig.getScale());

		pauseBtn.setOnMouseClicked((event) -> {
			root.getChildren().add(pausePane);
			pausePane.requestFocus();
			InterruptController.setPauseOpen(true);
		});

		Canvas pause = new Canvas(16 * GameConfig.getScale(), 16 * GameConfig.getScale());
		DrawUtil.drawPause(pause.getGraphicsContext2D());
		pauseBtn.setGraphic(pause);
		overlay.getChildren().add(pauseBtn);

		inventoryPane = new InventoryPane();
		equipmentPane = new EquipmentPane();
		pausePane = new PausePane();

		StackPane.setAlignment(new Group(inventoryPane), Pos.CENTER);
		StackPane.setAlignment(new Group(pausePane), Pos.CENTER);

		GameController.getGameMap().drawMap();
	}

	private static void addEventListener(Scene s, GraphicsContext gc, AnchorPane buttonPane) {
		s.setOnKeyPressed((event) -> {
			if (InterruptController.isInterruptPlayerInput()) {
				return;
			}
			KeyCode keycode = event.getCode();

			boolean isDraw = true;
			switch (keycode) {
			case A:
				GameLogic.gameUpdate(DispatchAction.MOVE_LEFT);
				break;
			case D:
				GameLogic.gameUpdate(DispatchAction.MOVE_RIGHT);
				break;
			case W:
				GameLogic.gameUpdate(DispatchAction.MOVE_UP);
				break;
			case S:
				GameLogic.gameUpdate(DispatchAction.MOVE_DOWN);
				break;
			case Q:
				GameLogic.gameUpdate(DispatchAction.STAY_STILL);
				break;
			case ESCAPE:
				if (InterruptController.isOpenFromInside()) {
					InterruptController.setOpenFromInside(false);
				} else {
					GameController.exitToMainMenu();
					isDraw = false;
				}
				break;
			default:
				System.out.println("Invalid key");
				isDraw = false;
				break;
			}
			if (isDraw) {
				GameController.getGameMap().drawMap();
			}
		});
	}

	public static Scene getScene() {
		if (scene == null)
			initScene();
		return scene;

	}

	public static StatusPane getStatusPane() {
		if (statusPane == null)
			initScene();
		return statusPane;
	}

	public static MessagePane getMessagePane() {
		if (messagePane == null)
			initScene();
		return messagePane;
	}

	public static EffectPane getEffectPane() {
		if (effectPane == null)
			initScene();
		return effectPane;
	}

	public static InventoryPane getInventoryPane() {
		if (inventoryPane == null)
			initScene();
		return inventoryPane;
	}

	public static PausePane getPausePane() {
		if (pausePane == null)
			initScene();
		return pausePane;
	}

	public static AnchorPane getButtonPane() {
		if (buttonPane == null)
			initScene();
		return buttonPane;
	}

	public static GraphicsContext getGraphicsContext() {
		if (gc == null)
			initScene();
		return gc;
	}

	public static EquipmentPane getEquipmentPane() {
		return equipmentPane;
	}
}
