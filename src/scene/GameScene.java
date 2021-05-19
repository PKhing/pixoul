package scene;

import components.EffectPane;
import components.InventoryPane;
import components.MessagePane;
import components.PausePane;
import components.StatusPane;
import controller.InterruptController;
import entity.base.DispatchAction;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import logic.GameLogic;
import logic.MapRenderer;
import utils.DrawUtil;
import utils.GameConfig;

/**
 * The GameScene class provides a method to initialize the game scene and store
 * the components in the game scene.
 *
 */
public class GameScene {
	/**
	 * The game scene.
	 */
	private static Scene scene = null;
	/**
	 * The {@link StatusPane status pane} on the game scene.
	 */
	private static StatusPane statusPane;
	/**
	 * The {@link MessagePane message pane} on the game scene.
	 */
	private static MessagePane messagePane;
	/**
	 * The {@link EffectPane effect pane} on the game scene.
	 */
	private static EffectPane effectPane;
	/**
	 * The {@link InventoryPane inventory pane} that will display when the player
	 * click inventory button.
	 */
	private static InventoryPane inventoryPane;
	/**
	 * The {@link PausePane pause pane} that will display when the player click
	 * pause button.
	 */
	private static PausePane pausePane;
	/**
	 * The pane for entity button.
	 */
	private static AnchorPane buttonPane;
	/**
	 * {@link GraphicsContext graphic context} of the map canvas.
	 */
	private static GraphicsContext gc;
	/**
	 * Backpack sprite for the inventory button.
	 */
	private static WritableImage backpackSprite = DrawUtil.getWritableImage("sprites/backpack.png");
	/**
	 * Pause sprite for the pause button.
	 */
	private static WritableImage pauseSprite = DrawUtil.getWritableImage("sprites/pause.png");
	/**
	 * The pane that contains all of the component in game scene.
	 */
	private static StackPane gamePane;
	/**
	 * The root pane.
	 */
	private static StackPane root;

	/**
	 * Initialize game scene.
	 */
	public static void initScene() {
		root = new StackPane();
		root.setPadding(new Insets(0));
		root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		root.setMinSize(GameConfig.getScreenWidth(), GameConfig.getScreenHeight());
		root.setMaxSize(GameConfig.getScreenWidth(), GameConfig.getScreenHeight());
		scene = new Scene(root, GameConfig.getScreenWidth(), GameConfig.getScreenHeight());

		setupGamePane();
		setupGameUI();

		StackPane.setAlignment(new Group(inventoryPane), Pos.CENTER);
		StackPane.setAlignment(new Group(pausePane), Pos.CENTER);

		MapRenderer.render();
	}

	/**
	 * Initialize game pane.
	 */
	private static void setupGamePane() {
		gamePane = new StackPane();
		root.getChildren().add(gamePane);

		Canvas canvas = new Canvas(GameConfig.getScreenWidth(), GameConfig.getScreenHeight());
		gc = canvas.getGraphicsContext2D();
		gamePane.getChildren().add(canvas);

		buttonPane = new AnchorPane();
		gamePane.getChildren().add(buttonPane);
		addEventListener();
	}

	/**
	 * Initialize user interface.
	 */
	private static void setupGameUI() {
		AnchorPane ui = new AnchorPane();
		ui.setPickOnBounds(false);
		gamePane.getChildren().add(ui);

		addInventoryButton(ui);
		addPauseButton(ui);

		statusPane = new StatusPane();
		messagePane = new MessagePane();
		effectPane = new EffectPane();
		inventoryPane = new InventoryPane();
		pausePane = new PausePane();

		ui.getChildren().addAll(statusPane, messagePane, effectPane);
	}

	/**
	 * Add inventory button to the user interface pane.
	 * 
	 * @param ui The user interface pane
	 */
	private static void addInventoryButton(AnchorPane ui) {
		Canvas inventoryBtn = new Canvas(32 * GameConfig.getScale(), 32 * GameConfig.getScale());
		ui.getChildren().add(inventoryBtn);

		AnchorPane.setBottomAnchor(inventoryBtn, 5.0 * GameConfig.getScale());
		AnchorPane.setRightAnchor(inventoryBtn, 5.0 * GameConfig.getScale());

		inventoryBtn.getGraphicsContext2D().drawImage(DrawUtil.scaleUp(backpackSprite, GameConfig.getScale()), 0, 0);

		inventoryBtn.setOnMouseClicked((event) -> {
			if (InterruptController.isPauseOpen() || InterruptController.isTransition()) {
				return;
			}
			gamePane.getChildren().add(inventoryPane);
			inventoryPane.requestFocus();
			InterruptController.setInventoryOpen(true);
		});
	}

	/**
	 * Add pause button to the user interface pane.
	 * 
	 * @param ui The user interface pane.
	 */
	private static void addPauseButton(AnchorPane ui) {
		Canvas pauseBtn = new Canvas(16 * GameConfig.getScale(), 16 * GameConfig.getScale());
		ui.getChildren().add(pauseBtn);

		AnchorPane.setTopAnchor(pauseBtn, 5.0 * GameConfig.getScale());
		AnchorPane.setRightAnchor(pauseBtn, 5.0 * GameConfig.getScale());

		pauseBtn.getGraphicsContext2D().drawImage(DrawUtil.scaleUp(pauseSprite, GameConfig.getScale()), 0, 0);

		pauseBtn.setOnMouseClicked((event) -> {
			if (InterruptController.isInventoryOpen() || InterruptController.isTransition()) {
				return;
			}
			if (InterruptController.isPauseOpen()) {
				pausePane.remove();
				return;
			}
			gamePane.getChildren().add(pausePane);
			pausePane.requestFocus();
			InterruptController.setPauseOpen(true);
		});
	}

	/**
	 * Add keyboard listener.
	 */
	private static void addEventListener() {
		scene.setOnKeyPressed((event) -> {
			if (InterruptController.isInterruptPlayerMovingInput() && !InterruptController.isStillAnimation()) {
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
			default:
				System.out.println("Invalid key");
				break;
			}
		});
	}

	/**
	 * Getter for game scene.
	 * 
	 * @return Game scene
	 */
	public static Scene getScene() {
		if (scene == null) {
			initScene();
		}
		return scene;

	}

	/**
	 * Getter for status pane.
	 * 
	 * @return Status pane
	 */
	public static StatusPane getStatusPane() {
		if (statusPane == null) {
			initScene();
		}
		return statusPane;
	}

	/**
	 * Getter for message pane.
	 * 
	 * @return Message pane
	 */
	public static MessagePane getMessagePane() {
		if (messagePane == null) {
			initScene();
		}
		return messagePane;
	}

	/**
	 * Getter for effect pane.
	 * 
	 * @return Effect pane
	 */
	public static EffectPane getEffectPane() {
		if (effectPane == null) {
			initScene();
		}
		return effectPane;
	}

	/**
	 * Getter for inventory pane.
	 * 
	 * @return Inventory pane
	 */
	public static InventoryPane getInventoryPane() {
		if (inventoryPane == null) {
			initScene();
		}
		return inventoryPane;
	}

	/**
	 * Getter for pause pane.
	 * 
	 * @return Pause pane
	 */
	public static PausePane getPausePane() {
		if (pausePane == null) {
			initScene();
		}
		return pausePane;
	}

	/**
	 * Getter for button pane.
	 * 
	 * @return Button pane
	 */
	public static AnchorPane getButtonPane() {
		if (buttonPane == null) {
			initScene();
		}
		return buttonPane;
	}

	/**
	 * Getter for graphic context of the map canvas.
	 * 
	 * @return Graphic context of the map canvas
	 */
	public static GraphicsContext getGraphicsContext() {
		if (gc == null) {
			initScene();
		}
		return gc;
	}

	/**
	 * Getter for game pane.
	 * 
	 * @return Game pane
	 */
	public static StackPane getGamePane() {
		if (gamePane == null) {
			initScene();
		}
		return gamePane;
	}

}
