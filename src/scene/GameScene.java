package scene;

import controller.SceneController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import utils.GlobalConstant;
import javafx.scene.paint.Color;
import logic.Cell;
import logic.Direction;
import logic.MapGenerator;
import utils.DrawUtil;

public class GameScene {

	private int playerPositionX = 30;
	private int playerPositionY = 30;
	final private int SPRITE_SIZE = 45;
	private static MapGenerator mapGenerator;
	private int[][] gameMap;
	private Scene scene;
	private int direction = Direction.Down;
	public GameScene() {
		mapGenerator = new MapGenerator();
		mapGenerator.generateMap();
		
		gameMap = mapGenerator.getMap();
		mapGenerator.printMap();
		
		System.out.println(mapGenerator.getRoomList());
		
		StackPane root = new StackPane();
		scene = new Scene(root, GlobalConstant.SCREEN_WIDTH, GlobalConstant.SCREEN_HEIGHT);
		
		Canvas canvas = new Canvas(GlobalConstant.SCREEN_WIDTH, GlobalConstant.SCREEN_HEIGHT);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		root.getChildren().add(canvas);
		drawMap(gc);
		
		addEventListener(scene, gc);
	}

	private void drawMap(GraphicsContext gc) {
		gc.setFill(Color.rgb(255, 255, 255));
		gc.fillRect(0, 0, GlobalConstant.SCREEN_WIDTH, GlobalConstant.SCREEN_HEIGHT);

		int centerX = playerPositionX * SPRITE_SIZE + SPRITE_SIZE / 2;
		int centerY = playerPositionY * SPRITE_SIZE + SPRITE_SIZE / 2;
		int startX = centerX - GlobalConstant.SCREEN_HEIGHT / 2;
		int startY = centerY - GlobalConstant.SCREEN_WIDTH / 2;
		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 50; j++) {
				if(gameMap[i][j] != Cell.VOID)
					DrawUtil.drawSprite(gc, SPRITE_SIZE * i - startX, SPRITE_SIZE * j - startY, gameMap[i][j]);
				if (i == playerPositionX && j == playerPositionY)
					DrawUtil.drawCharacter(gc, SPRITE_SIZE * i - startX, SPRITE_SIZE * j - startY, direction);
			}
		}
	}

	private void addEventListener(Scene s, GraphicsContext gc) {
		s.setOnKeyPressed((event) -> {
			KeyCode keycode = event.getCode();
			switch (keycode) {
			case A:
				playerPositionY--;
				direction = Direction.Left;
				break;
			case D:
				playerPositionY++;
				direction = Direction.Right;
				break;
			case W:
				playerPositionX--;
				direction = Direction.Up;
				break;
			case S:
				playerPositionX++;
				direction = Direction.Down;
				break;
			case ESCAPE:
				SceneController.setSceneToStage(new LandingScene().getScene());
				break;
			}

			drawMap(gc);
		});
	}

	public Scene getScene() {
		return scene;
	}

}
