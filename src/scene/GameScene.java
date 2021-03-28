package scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
		player = new Player(roomList.get(0).getKey(), roomList.get(0).getValue(), gameMap);

		root.getChildren().add(canvas);
		drawMap(gc);

		addEventListener(scene, gc);
	}

	private void drawMap(GraphicsContext gc) {
		gc.setFill(Color.rgb(0, 0, 0));
		gc.fillRect(0, 0, GameConfig.getScreenWidth(), GameConfig.getScreenHeight());

		int centerY = player.getPosY() * GameConfig.SPRITE_SIZE + GameConfig.SPRITE_SIZE / 2;
		int centerX = player.getPosX() * GameConfig.SPRITE_SIZE + GameConfig.SPRITE_SIZE / 2;

		int startY = centerY - GameConfig.getScreenHeight() / 2;
		int startX = centerX - GameConfig.getScreenWidth() / 2;

		int maxCellY = GameConfig.getScreenHeight() / GameConfig.SPRITE_SIZE;
		int maxCellX = GameConfig.getScreenWidth() / GameConfig.SPRITE_SIZE;

		int startIdxY = Math.max(0, player.getPosY() - maxCellY / 2 - 1);
		int endIdxY = Math.min(GameConfig.MAP_SIZE, player.getPosY() + maxCellY / 2 + 1);

		int startIdxX = Math.max(0, player.getPosX() - maxCellX / 2 - 1);
		int endIdxX = Math.min(GameConfig.MAP_SIZE, player.getPosX() + maxCellX / 2 + 1);

		/* Uncomment when game is ready
		ArrayList<Pair<Integer, Integer>> allVisibleField = new ArrayList<Pair<Integer, Integer>>();

		getAllVisibleField(allVisibleField, 3, 0, player.getPosY(), player.getPosX());

		allVisibleField.sort(Comparator.comparing(Pair<Integer, Integer>::getKey).thenComparingInt(Pair::getValue));

		int prevX = -1;
		int prevY = -1;

		for (Pair<Integer, Integer> pos : allVisibleField) {
			int posX = pos.getValue();
			int posY = pos.getKey();

			if (posX == prevX && posY == prevY) {
				continue;
			}

			if (prevY == -1) {
				prevY = posY;
			}

			if (prevX == -1) {
				prevX = posX;
			}

			DrawUtil.drawSprite(gc, GameConfig.SPRITE_SIZE * posY - startY, GameConfig.SPRITE_SIZE * posX - startX,
					gameMap.get(posY, posX).getType());
			if (gameMap.get(posY, posX).getEntity() instanceof Player)
				DrawUtil.drawCharacter(gc, GameConfig.SPRITE_SIZE * posY - startY,
						GameConfig.SPRITE_SIZE * posX - startX, player.getDirection());
		}
		*/

		for (int i = startIdxY; i <= endIdxY; i++) {
			for (int j = startIdxX; j <= endIdxX; j++) {
				DrawUtil.drawSprite(gc, GameConfig.SPRITE_SIZE * i - startY, GameConfig.SPRITE_SIZE * j - startX,
						gameMap.get(i, j).getType());
				if (gameMap.get(i, j).getEntity() instanceof Player)
					DrawUtil.drawCharacter(gc, GameConfig.SPRITE_SIZE * i - startY, GameConfig.SPRITE_SIZE * j - startX,
							player.getDirection());
			}
		}

	}

	@SuppressWarnings("unused")
	private void getAllVisibleField(ArrayList<Pair<Integer, Integer>> allPos, int lineOfSight, int level, int posY,
			int posX) {

		if (lineOfSight < level) {
			return;
		}

		if (gameMap.get(posY, posX).getType() == Cell.WALL) {
			allPos.add(new Pair<Integer, Integer>(posY, posX));
			return;
		}

		allPos.add(new Pair<Integer, Integer>(posY, posX));

		final int directionArr[][] = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 }, { -1, -1 }, { 1, 1 }, { 1, -1 },
				{ -1, 1 } };

		for (int i = 0; i < 8; i++) {
			int newX = posX + directionArr[i][0];
			int newY = posY + directionArr[i][1];
			boolean found = false;

			if (found)
				continue;

			getAllVisibleField(allPos, lineOfSight, level + 1, newY, newX);
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
