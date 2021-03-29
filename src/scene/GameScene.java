package scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import components.InventoryPane;
import controller.SceneController;
import entity.Player;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utils.GameConfig;
import utils.Util;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;
import logic.Cell;
import logic.Direction;
import logic.GameMap;
import utils.DrawUtil;

public class GameScene {
	private GameMap gameMap;
	private Scene scene;
	private Player player;
	private StackPane statPane;
	private VBox messagePane;
	private VBox effectPane;
	private InventoryPane inventoryPane;
	
	public GameScene() {
		gameMap = new GameMap();
		gameMap.printMap();

		StackPane root = new StackPane();
		scene = new Scene(root, GameConfig.getScreenWidth(), GameConfig.getScreenHeight());

		Canvas canvas = new Canvas(GameConfig.getScreenWidth(), GameConfig.getScreenHeight());
		GraphicsContext gc = canvas.getGraphicsContext2D();

		ArrayList<Pair<Integer, Integer>> roomList = gameMap.getRoomList();

		Collections.shuffle(roomList);
		player = new Player(roomList.get(0).getKey(), roomList.get(0).getValue(), gameMap);

		root.getChildren().add(canvas);
		drawMap(gc);

		addEventListener(scene, gc);

		// Overlay
		AnchorPane overlay = new AnchorPane();
		addStatPane(overlay);
		addMessagePane(overlay);
		addEffectPane(overlay);
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

		Canvas pause = new Canvas(16 * GameConfig.getScale(), 16 * GameConfig.getScale());
		DrawUtil.drawPause(pause.getGraphicsContext2D());
		pauseBtn.setGraphic(pause);
		overlay.getChildren().add(pauseBtn);
		
		inventoryPane = new InventoryPane();
		StackPane.setAlignment(new Group(inventoryPane), Pos.CENTER);
		
		
	}

	private void addStatPane(AnchorPane overlay) {
		statPane = new StackPane();

		Canvas background = new Canvas(75 * GameConfig.getScale(), 48 * GameConfig.getScale());
		DrawUtil.drawStatPane(background.getGraphicsContext2D());
		statPane.getChildren().add(background);

		AnchorPane.setTopAnchor(statPane, 0.0);
		AnchorPane.setLeftAnchor(statPane, 0.0);
		overlay.getChildren().add(statPane);

		VBox statBox = new VBox();
		statPane.getChildren().add(statBox);
		statBox.setPadding(new Insets(10*GameConfig.getScale(),10*GameConfig.getScale(),10*GameConfig.getScale(),15*GameConfig.getScale()));


		Text hp = new Text("HP : 0 / 10");
		hp.setFont(Util.getFont());
		statBox.getChildren().add(hp);

		Text attack = new Text("Attack : 0");
		attack.setFont(Util.getFont());
		statBox.getChildren().add(attack);

		Text defense = new Text("Defense : 0");
		defense.setFont(Util.getFont());
		statBox.getChildren().add(defense);

	}

	private void addMessagePane(AnchorPane overlay) {

		messagePane = new VBox();
		AnchorPane.setBottomAnchor(messagePane, 0.0);
		AnchorPane.setLeftAnchor(messagePane, 0.0);
		overlay.getChildren().add(messagePane);
		messagePane.setPrefHeight(40.0 * GameConfig.getScale());
		messagePane.setPrefWidth(100.0 * GameConfig.getScale());
		messagePane.setStyle("-fx-background-color: rgba(0,0,0, 0.5);-fx-padding:7");

		Text message = new Text("PKhing got salt from gacha");
		message.setFont(Util.getFont());
		message.setFill(Color.WHITE);
		messagePane.getChildren().add(message);

	}

	private void addEffectPane(AnchorPane overlay) {

		effectPane = new VBox();
		AnchorPane.setTopAnchor(effectPane, 25.0 * GameConfig.getScale());
		AnchorPane.setRightAnchor(effectPane, 0.0);
		overlay.getChildren().add(effectPane);
		effectPane.setStyle("-fx-background-color: rgba(0,0,0, 0.5);-fx-padding:7");

		Text message = new Text("Salt effect : inf");
		message.setFont(Util.getFont());
		message.setFill(Color.WHITE);
		effectPane.getChildren().add(message);

	}

	private void drawMap(GraphicsContext gc) {
		gc.setFill(Color.rgb(0, 0, 0));
		gc.fillRect(0, 0, GameConfig.getScreenWidth(), GameConfig.getScreenHeight());

		int centerY = player.getPosY() * GameConfig.SPRITE_SIZE * GameConfig.getScale()
				+ GameConfig.SPRITE_SIZE * GameConfig.getScale() / 2;
		int centerX = player.getPosX() * GameConfig.SPRITE_SIZE * GameConfig.getScale()
				+ GameConfig.SPRITE_SIZE * GameConfig.getScale() / 2;

		int startY = centerY - GameConfig.getScreenHeight() / 2;
		int startX = centerX - GameConfig.getScreenWidth() / 2;

		int maxCellY = GameConfig.getScreenHeight() / (GameConfig.SPRITE_SIZE * GameConfig.getScale());
		int maxCellX = GameConfig.getScreenWidth() / (GameConfig.SPRITE_SIZE * GameConfig.getScale());

		int startIdxY = Math.max(0, player.getPosY() - maxCellY / 2 - 1);
		int endIdxY = Math.min(GameConfig.MAP_SIZE, player.getPosY() + maxCellY / 2 + 1);

		int startIdxX = Math.max(0, player.getPosX() - maxCellX / 2 - 1);
		int endIdxX = Math.min(GameConfig.MAP_SIZE, player.getPosX() + maxCellX / 2 + 1);

		/*
		 * Uncomment when game is ready 
		 * ArrayList<Pair<Integer, Integer>>
		 * allVisibleField = new ArrayList<Pair<Integer, Integer>>();
		 * 
		 * getAllVisibleField(allVisibleField, 3, player.getPosY(), player.getPosX());
		 * 
		 * allVisibleField.sort(Comparator.comparing(Pair<Integer,
		 * Integer>::getKey).thenComparingInt(Pair::getValue));
		 * 
		 * for (Pair<Integer, Integer> pos : allVisibleField) { int posX =
		 * pos.getValue(); int posY = pos.getKey();
		 * 
		 * DrawUtil.drawSprite(gc, GameConfig.SPRITE_SIZE * posY - startY,
		 * GameConfig.SPRITE_SIZE * posX - startX, gameMap.get(posY, posX).getType());
		 * if (gameMap.get(posY, posX).getEntity() instanceof Player)
		 * DrawUtil.drawCharacter(gc, GameConfig.SPRITE_SIZE * posY - startY,
		 * GameConfig.SPRITE_SIZE * posX - startX, player.getDirection()); }
		 */

		for (int i = startIdxY; i <= endIdxY; i++) {
			for (int j = startIdxX; j <= endIdxX; j++) {
				DrawUtil.drawCell(gc, GameConfig.SPRITE_SIZE * GameConfig.getScale() * i - startY,
						GameConfig.SPRITE_SIZE * GameConfig.getScale() * j - startX, gameMap.get(i, j));
				if (gameMap.get(i, j).getEntity() instanceof Player)
					DrawUtil.drawCharacter(gc, GameConfig.SPRITE_SIZE * GameConfig.getScale() * i - startY,
							GameConfig.SPRITE_SIZE * GameConfig.getScale() * j - startX, player.getDirection());
			}
		}

	}

	@SuppressWarnings("unused")
	private void getAllVisibleField(ArrayList<Pair<Integer, Integer>> allPos, int lineOfSight, int posY, int posX) {
		Queue<Pair<Integer, Pair<Integer, Integer>>> queue = new LinkedList<>();

		final int directionArr[][] = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 }, { -1, -1 }, { 1, 1 }, { 1, -1 },
				{ -1, 1 } };
		final int directionSz = 8;

		queue.add(new Pair<>(0, new Pair<>(posY, posX)));

		while (queue.size() != 0) {
			int level = queue.peek().getKey();
			int nowY = queue.peek().getValue().getKey();
			int nowX = queue.peek().getValue().getValue();

			queue.remove();

			if (level > lineOfSight) {
				continue;
			}

			boolean found = false;

			for (Pair<Integer, Integer> each : allPos) {
				if (each.getKey() == nowY && each.getValue() == nowX) {
					found = true;
					break;
				}
			}

			if (found) {
				continue;
			}

			allPos.add(new Pair<>(nowY, nowX));

			if (gameMap.get(nowY, nowX).getType() == Cell.WALL) {
				continue;
			}

			for (int i = 0; i < directionSz; i++) {
				int newX = directionArr[i][0] + nowX;
				int newY = directionArr[i][1] + nowY;
				if (directionArr[i][0] == 0 || directionArr[i][1] == 0) {
					queue.add(new Pair<>(level + 1, new Pair<>(newY, newX)));
				} else {
					int cellTypeY = gameMap.get(newY, nowX).getType();
					int cellTypeX = gameMap.get(nowY, newX).getType();

					if (cellTypeY == Cell.WALL && cellTypeX == Cell.WALL) {
						continue;
					} else {
						queue.add(new Pair<>(level + 1, new Pair<>(newY, newX)));
					}
				}
			}
		}
	}

	private void addEventListener(Scene s, GraphicsContext gc) {
		s.setOnKeyPressed((event) -> {
			KeyCode keycode = event.getCode();
			boolean isDraw = true;
			switch (keycode) {
			case A:
				player.move(gameMap, Direction.LEFT);
				break;
			case D:
				player.move(gameMap, Direction.RIGHT);
				break;
			case W:
				player.move(gameMap, Direction.UP);
				break;
			case S:
				player.move(gameMap, Direction.DOWN);
				break;
			case SPACE:
				break;
			case ESCAPE:
				SceneController.setSceneToStage(LandingScene.getScene());
				isDraw = false;
				break;
			default:
				System.out.println("Invalid key");
				isDraw = false;
				break;
			}
			if (isDraw) {
				drawMap(gc);
			}
		});
	}

	public GameMap getGameMap() {
		return gameMap;
	}

	public void setGameMap(GameMap gameMap) {
		this.gameMap = gameMap;
	}

	public Scene getScene() {
		return scene;
	}
	
}
