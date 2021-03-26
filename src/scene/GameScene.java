package scene;

import java.util.ArrayList;
import java.util.Collections;

import controller.SceneController;
import entity.Player;
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
	private GameMap gameMap;
	private Scene scene;
	Player player;
	public GameScene() {
		gameMap = new GameMap();
		gameMap.printMap();

		StackPane root = new StackPane();
		scene = new Scene(root, GameConfig.getScreenWidth(), GameConfig.getScreenHeight());

		Canvas canvas = new Canvas(GameConfig.getScreenWidth(), GameConfig.getScreenHeight());

		GraphicsContext gc = canvas.getGraphicsContext2D();

		ArrayList<Pair<Integer, Integer>> roomList = gameMap.getRoomList();

		Collections.shuffle(roomList);
		player = new Player(roomList.get(0).getKey(),roomList.get(0).getValue(),gameMap);

		root.getChildren().add(canvas);
		drawMap(gc);

		addEventListener(scene, gc);
	}

	private void drawMap(GraphicsContext gc) {
		gc.setFill(Color.rgb(255, 255, 255));
		gc.fillRect(0, 0, GameConfig.getScreenWidth(), GameConfig.getScreenHeight());

		int centerY = player.getPosY() * GameConfig.SPRITE_SIZE + GameConfig.SPRITE_SIZE / 2;
		int centerX = player.getPosX() * GameConfig.SPRITE_SIZE + GameConfig.SPRITE_SIZE / 2;
		int startY = centerY - GameConfig.getScreenHeight() / 2;
		int startX = centerX - GameConfig.getScreenWidth() / 2;
		int maxCellY = GameConfig.getScreenHeight() / GameConfig.SPRITE_SIZE;
		int maxCellX = GameConfig.getScreenWidth() / GameConfig.SPRITE_SIZE;
		for (int i = Math.max(0, player.getPosY() - maxCellY / 2 - 1); i <= Math.min(GameConfig.MAP_SIZE,
				player.getPosY() + maxCellY / 2 + 1); i++) {
			for (int j = Math.max(0, player.getPosX() - maxCellX / 2 - 1); j <= Math.min(GameConfig.MAP_SIZE,
					player.getPosX() + maxCellX / 2 + 1); j++) {
				DrawUtil.drawSprite(gc, GameConfig.SPRITE_SIZE * i - startY, GameConfig.SPRITE_SIZE * j - startX,
						gameMap.get(i, j).getType());
				if (gameMap.get(i, j).getEntity() instanceof Player)
					DrawUtil.drawCharacter(gc, GameConfig.SPRITE_SIZE * i - startY, GameConfig.SPRITE_SIZE * j - startX,
							player.getDirection());
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
