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

public class GameScene {
	private static Scene scene = null;
	private static StatusPane statusPane;
	private static MessagePane messagePane;
	private static EffectPane effectPane;
	private static InventoryPane inventoryPane;
	private static PausePane pausePane;
	private static AnchorPane buttonPane;
	private static GraphicsContext gc;
	private static WritableImage backpackSprite = DrawUtil.getWritableImage("sprites/backpack.png");
	private static WritableImage pauseSprite = DrawUtil.getWritableImage("sprites/pause.png");
	private static StackPane gamePane;
	private static StackPane root;

	public static void initScene() {
		root = new StackPane();
		root.setPadding(new Insets(0));
		root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		root.setMinSize(GameConfig.getScreenWidth(), GameConfig.getScreenHeight());
		root.setMaxSize(GameConfig.getScreenWidth(), GameConfig.getScreenHeight());
		scene = new Scene(root, GameConfig.getScreenWidth(), GameConfig.getScreenHeight());

		setupGamePane();
		setupGameOverlay();

		StackPane.setAlignment(new Group(inventoryPane), Pos.CENTER);
		StackPane.setAlignment(new Group(pausePane), Pos.CENTER);

		MapRenderer.render();
	}

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

	private static void setupGameOverlay() {
		AnchorPane overlay = new AnchorPane();
		overlay.setPickOnBounds(false);
		gamePane.getChildren().add(overlay);

		addInventoryButton(overlay);
		addPauseButton(overlay);

		statusPane = new StatusPane();
		messagePane = new MessagePane();
		effectPane = new EffectPane();
		inventoryPane = new InventoryPane();
		pausePane = new PausePane();

		overlay.getChildren().addAll(statusPane, messagePane, effectPane);
	}

	private static void addInventoryButton(AnchorPane overlay) {
		Canvas inventoryBtn = new Canvas(32 * GameConfig.getScale(), 32 * GameConfig.getScale());
		overlay.getChildren().add(inventoryBtn);

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

	private static void addPauseButton(AnchorPane overlay) {
		Canvas pauseBtn = new Canvas(16 * GameConfig.getScale(), 16 * GameConfig.getScale());
		overlay.getChildren().add(pauseBtn);

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

	public static Scene getScene() {
		if (scene == null) {
			initScene();
		}
		return scene;

	}

	public static StatusPane getStatusPane() {
		if (statusPane == null) {
			initScene();
		}
		return statusPane;
	}

	public static MessagePane getMessagePane() {
		if (messagePane == null) {
			initScene();
		}
		return messagePane;
	}

	public static EffectPane getEffectPane() {
		if (effectPane == null) {
			initScene();
		}
		return effectPane;
	}

	public static InventoryPane getInventoryPane() {
		if (inventoryPane == null) {
			initScene();
		}
		return inventoryPane;
	}

	public static PausePane getPausePane() {
		if (pausePane == null) {
			initScene();
		}
		return pausePane;
	}

	public static AnchorPane getButtonPane() {
		if (buttonPane == null) {
			initScene();
		}
		return buttonPane;
	}

	public static GraphicsContext getGraphicsContext() {
		if (gc == null) {
			initScene();
		}
		return gc;
	}

	public static StackPane getGamePane() {
		if (gamePane == null) {
			initScene();
		}
		return gamePane;
	}

}
