package scene;

import components.GameOverPane;
import controller.SceneController;
import javafx.scene.Scene;

public class GameOverScene {
	private static Scene scene = null;
	
	public static Scene getScene() {
		if(scene != null) {
			return scene;
		}
		GameOverPane newPane = new GameOverPane();
		scene = SceneController.makeNewScene(newPane);
		return scene;
	}
}
