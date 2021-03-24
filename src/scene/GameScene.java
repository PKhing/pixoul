package scene;

import application.DrawUtil;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import logic.Cell;
import logic.MapGenerator;

public class GameScene {

	private int playerPositionX = 30;
	private int playerPositionY = 30;
	final private int SCREEN_WIDTH = 854;
	final private int SCREEN_HEIGHT = 480;
	final private int SPRITE_SIZE = 45;
	private static MapGenerator mapGenerator;
	private int[][] gameMap;
	private Scene scene;

	public GameScene() {
		mapGenerator = new MapGenerator();
		mapGenerator.generateMap();
		gameMap = mapGenerator.getMap();
		mapGenerator.printMap();
		System.out.println(mapGenerator.getRoomList());
		StackPane root = new StackPane();
		scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
		Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		root.getChildren().add(canvas);
		drawMap(gc);
		addEventListener(scene, gc);
	}

	private void drawMap(GraphicsContext gc) {
		gc.setFill(Color.rgb(255, 255, 255));
		gc.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

		int centerX = playerPositionX * SPRITE_SIZE + SPRITE_SIZE / 2;
		int centerY = playerPositionY * SPRITE_SIZE + SPRITE_SIZE / 2;
		int startX = centerX - SCREEN_HEIGHT / 2;
		int startY = centerY - SCREEN_WIDTH / 2;
		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 50; j++) {
				if (gameMap[i][j] == Cell.PATH)
					DrawUtil.drawSprite(gc, SPRITE_SIZE * i - startX, SPRITE_SIZE * j - startY, 0);
				if (gameMap[i][j] == Cell.WALL)
					DrawUtil.drawSprite(gc, SPRITE_SIZE * i - startX, SPRITE_SIZE * j - startY, 1);
				if (i == playerPositionX && j == playerPositionY)
					DrawUtil.drawSprite(gc, SPRITE_SIZE * i - startX, SPRITE_SIZE * j - startY, 2);
			}
		}
	}

	private void addEventListener(Scene s, GraphicsContext gc) {
		s.setOnKeyPressed((event) -> {
			KeyCode keycode = event.getCode();
			switch (keycode) {
			case A:
				playerPositionY--;
				break;
			case D:
				playerPositionY++;
				break;
			case W:
				playerPositionX--;
				break;
			case S:
				playerPositionX++;
				break;
			}

			drawMap(gc);
		});
	}

	public Scene getScene() {
		return scene;
	}

}
