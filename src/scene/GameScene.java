package scene;

import components.EffectPane;
import components.InventoryPane;
import components.MessagePane;
import components.StatusPane;
import components.PausePane;
import controller.GameController;
import controller.InterruptController;
import entity.Skeleton;
import entity.base.DispatchAction;
import items.armor.GoldenArmor;
import items.armor.IronArmor;
import items.base.Potion;
import items.potion.InstantHealPotion;
import items.potion.RegenerationPotion;
import items.weapon.Spear;
import items.weapon.Sword;
import items.potion.ShieldPotion;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
	private static GraphicsContext gc;
	private static StackPane gamePane;

	public static void initScene() {
		StackPane root = new StackPane();
		
		root.setPadding(new Insets(0));
		root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

		gamePane = new StackPane();

		gamePane.setMinSize(GameConfig.getScreenWidth(), GameConfig.getScreenHeight());
		gamePane.setMaxSize(GameConfig.getScreenWidth(), GameConfig.getScreenHeight());
		
		root.getChildren().add(gamePane);
		
		scene = new Scene(root, GameConfig.getScreenWidth(), GameConfig.getScreenHeight());

		Canvas canvas = new Canvas(GameConfig.getScreenWidth(), GameConfig.getScreenHeight());
		gc = canvas.getGraphicsContext2D();
		gamePane.getChildren().add(canvas);

		buttonPane = new AnchorPane();
		gamePane.getChildren().add(buttonPane);
		addEventListener(scene, gc, buttonPane);

		// Overlay
		AnchorPane overlay = new AnchorPane();
		statusPane = new StatusPane();
		messagePane = new MessagePane();
		effectPane = new EffectPane();

		overlay.setPickOnBounds(false);
		gamePane.getChildren().add(overlay);

		// Inventory Button
		Button inventoryBtn = new Button();

		inventoryBtn.setStyle("-fx-margin:0;-fx-padding:0");
		inventoryBtn.setPrefHeight(30.0 * GameConfig.getScale());
		inventoryBtn.setPrefWidth(30.0 * GameConfig.getScale());
		Canvas backpack = new Canvas(32 * GameConfig.getScale(), 32 * GameConfig.getScale());

		DrawUtil.addCursorHover(backpack, false);
		DrawUtil.drawBackpack(backpack.getGraphicsContext2D());

		inventoryBtn.setGraphic(backpack);
		inventoryBtn.setOnMouseClicked((event) -> {
			if (InterruptController.isPauseOpen())
				return;
			gamePane.getChildren().add(inventoryPane);
			inventoryPane.requestFocus();
			InterruptController.setInventoryOpen(true);
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
			if (InterruptController.isPauseOpen())
				return;
			gamePane.getChildren().add(pausePane);
			pausePane.requestFocus();
			InterruptController.setPauseOpen(true);
		});

		Canvas pause = new Canvas(16 * GameConfig.getScale(), 16 * GameConfig.getScale());
		DrawUtil.drawPause(pause.getGraphicsContext2D());
		pauseBtn.setGraphic(pause);

		overlay.getChildren().addAll(statusPane, messagePane, effectPane, pauseBtn);

		inventoryPane = new InventoryPane();
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
				}
				break;
			default:
				System.out.println("Invalid key");
				break;
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

	public static StackPane getGamePane() {
		if (gamePane == null)
			initScene();
		return gamePane;
	}

	public static void setPlayerPositionOnNewMap() {

		Pair<Integer, Integer> firstRoomPos = GameController.getRoomList().get(0);
		GameController.getPlayer().setInitialPos(firstRoomPos.getKey(), firstRoomPos.getValue());
		GameController.getPlayer().getItemList().add(new Sword("Salty Sword", "With 100 years salt effect", 10, 1));
		GameController.getPlayer().getItemList()
				.add(new Spear("More Salty Sword", "With 100 years salt effect", 10, 1));
		GameController.getPlayer().getItemList()
				.add(new IronArmor("More Salty Sword", "With 100 years salt effect", 5));
		GameController.getPlayer().getItemList()
				.add(new GoldenArmor("More Salty Sword", "With 100 years salt effect", 5));

		Skeleton skeleton = new Skeleton(5, 10, 1, firstRoomPos.getKey() - 3, firstRoomPos.getValue() + 1,
				Direction.DOWN, 1.25, 0, 1);

		Potion maxHealthPotion = new InstantHealPotion("Bitset Potion", "Extends for 1 bit shift",
				GameController.getPlayer().getHealth(), true);
		Potion currentPotion = new RegenerationPotion("Salty Potion", "With 100 years salt effect", 10, 100);
		GameController.getPlayer().getItemList().add(currentPotion);
		GameController.getPlayer().getItemList().add(maxHealthPotion);

		Potion newPotion = new ShieldPotion("Shield Potion", "For extra armor", 5, 0, true);

		GameController.getGameMap().get(firstRoomPos.getKey() + 3, firstRoomPos.getValue()).setItem(newPotion);
		skeleton.setHealth(8);
		GameController.getGameMap().getMonsterList().add(skeleton);
	}

}
