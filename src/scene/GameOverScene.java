package scene;

import components.GameOverPane;
import controller.SceneController;
import javafx.scene.Scene;

public class GameOverScene {
	private static Scene cachedScene = null;
	private static GameOverPane gameOverPane;
	
	public static Scene getScene() {
		if(cachedScene != null) {
			return cachedScene;
		}
		gameOverPane = new GameOverPane();
		cachedScene = SceneController.makeNewScene(gameOverPane);
		return cachedScene;
	}
}
