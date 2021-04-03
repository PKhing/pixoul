package scene;

import components.EffectPane;
import components.InventoryPane;
import components.MessagePane;
import components.StatusPane;
import components.PausePane;
import controller.GameController;
import controller.InterruptController;
import entity.Skeleton;
import entity.base.Monster;
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
import javafx.scene.paint.Color;
import javafx.util.Pair;
import logic.Direction;
import logic.GameLogic;
import logic.GameMap;
import utils.DrawUtil;

public class GameScene {
	private Scene scene;
	private StatusPane statusPane;
	private MessagePane messagePane;
	private EffectPane effectPane;
	private InventoryPane inventoryPane;
	private PausePane pausePane;

	public GameScene() {

		GameController.setGameMap(new GameMap());
		Pair<Integer, Integer> firstRoomPos = GameController.getRoomList().get(0);
		GameController.getPlayer().setInitialPos(firstRoomPos.getKey(), firstRoomPos.getValue());

		Skeleton skeleton = new Skeleton(1, 1, 1, firstRoomPos.getKey(), firstRoomPos.getValue() + 1, Direction.DOWN, 0, 0, 1);

		GameController.getGameMap().getMonsterList().add(skeleton);

		GameController.getPlayer().equipItem(new HealingPotion("Salty Potion", "With 100 years salt effect", 10, 100));

		StackPane root = new StackPane();
		scene = new Scene(root, GameConfig.getScreenWidth(), GameConfig.getScreenHeight());

		Canvas canvas = new Canvas(GameConfig.getScreenWidth(), GameConfig.getScreenHeight());
		GraphicsContext gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);

		AnchorPane buttonPane = new AnchorPane();
		drawMap(gc, buttonPane);
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
		pausePane = new PausePane();

		StackPane.setAlignment(new Group(inventoryPane), Pos.CENTER);
		StackPane.setAlignment(new Group(pausePane), Pos.CENTER);
	}

	private void drawMap(GraphicsContext gc, AnchorPane buttonPane) {
		buttonPane.getChildren().clear();
		gc.setFill(Color.rgb(0, 0, 0));
		gc.fillRect(0, 0, GameConfig.getScreenWidth(), GameConfig.getScreenHeight());

		int newSpriteSize = GameConfig.SPRITE_SIZE * GameConfig.getScale();

		int centerY = GameController.getPlayer().getPosY() * newSpriteSize
				+ GameConfig.SPRITE_SIZE * GameConfig.getScale() / 2;
		int centerX = GameController.getPlayer().getPosX() * newSpriteSize
				+ GameConfig.SPRITE_SIZE * GameConfig.getScale() / 2;

		int startY = centerY - GameConfig.getScreenHeight() / 2;
		int startX = centerX - GameConfig.getScreenWidth() / 2;

		int maxCellY = GameConfig.getScreenHeight() / (newSpriteSize);
		int maxCellX = GameConfig.getScreenWidth() / (newSpriteSize);

		int startIdxY = Math.max(0, GameController.getPlayer().getPosY() - maxCellY / 2 - 1);
		int endIdxY = Math.min(GameConfig.MAP_SIZE, GameController.getPlayer().getPosY() + maxCellY / 2 + 1);

		int startIdxX = Math.max(0, GameController.getPlayer().getPosX() - maxCellX / 2 - 1);
		int endIdxX = Math.min(GameConfig.MAP_SIZE, GameController.getPlayer().getPosX() + maxCellX / 2 + 1);

//		Uncomment when game is ready 
//		ArrayList<Pair<Integer, Integer>> allVisibleField = new ArrayList<Pair<Integer, Integer>>();
//
//		getAllVisibleField(allVisibleField, 3, player.getPosY(), player.getPosX());
//
//		allVisibleField.sort(Comparator.comparing(Pair<Integer, Integer>::getKey).thenComparingInt(Pair::getValue));
//
//		for (Pair<Integer, Integer> pos : allVisibleField) {
//			int posX = pos.getValue();
//			int posY = pos.getKey();
//
//			DrawUtil.drawCell(gc, newSpriteSize * posY - startY,
//					newSpriteSize * posX - startX, gameMap.get(posY, posX));
//			if (gameMap.get(posY, posX).getEntity() instanceof Player)
//				DrawUtil.drawCharacter(gc, newSpriteSize * posY - startY,
//						newSpriteSize * posX - startX, player.getDirection());
//		}

		for (int i = startIdxY; i <= endIdxY; i++) {
			for (int j = startIdxX; j <= endIdxX; j++) {
				DrawUtil.drawCell(gc, newSpriteSize * i - startY, newSpriteSize * j - startX,
						GameController.getGameMap().get(i, j));
				if (GameController.getGameMap().get(i, j).getEntity() != null)
					DrawUtil.drawEntity(gc, newSpriteSize * i - startY, newSpriteSize * j - startX,
							GameController.getGameMap().get(i, j).getEntity());
				if (GameController.getGameMap().get(i, j).getEntity() instanceof Monster)
					DrawUtil.addEntityButton(buttonPane, newSpriteSize * i - startY, newSpriteSize * j - startX,
							GameController.getGameMap().get(i, j).getEntity());
			}
		}

	}

	private void addEventListener(Scene s, GraphicsContext gc, AnchorPane buttonPane) {
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
				drawMap(gc, buttonPane);
			}
		});
	}

	public Scene getScene() {
		return scene;
	}

	public void setHPText(int hp, int maxHP) {
		this.statusPane.setHP(hp, maxHP);
	}
	
	public void setAttackText(int atk) {
		this.statusPane.setAttack(atk);
	}
	
	public void setDefenseText(int def) {
		this.statusPane.setDefense(def);
	}
}
