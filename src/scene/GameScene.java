package scene;

import java.util.ArrayList;
import java.util.Collections;

import controller.SceneController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import utils.GameConfig;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import logic.Cell;
import logic.Direction;
import logic.GameMap;
import utils.DrawUtil;

public class GameScene {
	private int playerPositionX = 30;
	private int playerPositionY = 30;
	private static GameMap gameMap;
	private Scene scene;
	private int direction = Direction.Down;

	public GameScene() {
		gameMap = new GameMap();
		gameMap.printMap();
		gameMap.get(30, 30).setEntity(1);

		StackPane root = new StackPane();
		scene = new Scene(root, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);

		Canvas canvas = new Canvas(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		ArrayList<Pair<Integer, Integer>> roomList = gameMap.getRoomList();

		Collections.shuffle(roomList);
		setPlayerPositionX(roomList.get(0).getKey());
		setPlayerPositionY(roomList.get(0).getValue());

		root.getChildren().add(canvas);
		drawMap(gc);

		addEventListener(scene, gc);
	}

	private void drawMap(GraphicsContext gc) {
		gc.setFill(Color.rgb(255, 255, 255));
		gc.fillRect(0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);

		int centerX = playerPositionX * GameConfig.SPRITE_SIZE + GameConfig.SPRITE_SIZE / 2;
		int centerY = playerPositionY * GameConfig.SPRITE_SIZE + GameConfig.SPRITE_SIZE / 2;
		int startX = centerX - GameConfig.SCREEN_HEIGHT / 2;
		int startY = centerY - GameConfig.SCREEN_WIDTH / 2;
		int maxCellX = GameConfig.SCREEN_HEIGHT/GameConfig.SPRITE_SIZE;
		int maxCellY = GameConfig.SCREEN_WIDTH/GameConfig.SPRITE_SIZE;
		for (int i = Math.max(0, playerPositionX-maxCellX/2-1); i <= Math.min(GameConfig.MAP_SIZE, playerPositionX+maxCellX/2+1); i++) {
			for (int j = Math.max(0, playerPositionY-maxCellY/2-1); j <= Math.min(GameConfig.MAP_SIZE, playerPositionY+maxCellY/2+1); j++) {
					DrawUtil.drawSprite(gc, GameConfig.SPRITE_SIZE * i - startX, GameConfig.SPRITE_SIZE * j - startY, gameMap.get(i, j).getType());
				if (i == playerPositionX && j == playerPositionY)
					DrawUtil.drawCharacter(gc, GameConfig.SPRITE_SIZE * i - startX, GameConfig.SPRITE_SIZE * j - startY, direction);
			}
		}

	}

	private void addEventListener(Scene s, GraphicsContext gc) {
		s.setOnKeyPressed((event) -> {
			KeyCode keycode = event.getCode();
			boolean isDraw = true;
			switch (keycode) {
			case A:
				gameMap.get(playerPositionX, playerPositionY).setEntity(0);
				playerPositionY--;
				direction = Direction.Left;
				gameMap.get(playerPositionX, playerPositionY).setEntity(1);
				break;
			case D:
				gameMap.get(playerPositionX, playerPositionY).setEntity(0);
				playerPositionY++;
				direction = Direction.Right;
				gameMap.get(playerPositionX, playerPositionY).setEntity(1);
				break;
			case W:
				gameMap.get(playerPositionX, playerPositionY).setEntity(0);
				playerPositionX--;
				direction = Direction.Up;
				gameMap.get(playerPositionX, playerPositionY).setEntity(1);
				break;
			case S:
				gameMap.get(playerPositionX, playerPositionY).setEntity(0);
				playerPositionX++;
				direction = Direction.Down;
				gameMap.get(playerPositionX, playerPositionY).setEntity(1);
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

	public Scene getScene() {
		return scene;
	}

	public int getPlayerPositionX() {
		return playerPositionX;
	}

	public void setPlayerPositionX(int playerPositionX) {
		this.playerPositionX = playerPositionX;
	}

	public int getPlayerPositionY() {
		return playerPositionY;
	}

	public void setPlayerPositionY(int playerPositionY) {
		this.playerPositionY = playerPositionY;
	}
}
