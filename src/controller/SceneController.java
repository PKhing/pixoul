package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import scene.GameScene;
import scene.LandingScene;

public class SceneController {
	private static Stage mainStage;

	public static void setMainStage(Stage mainStage) {
		if (mainStage == null)
			return;
		SceneController.mainStage = mainStage;
	}

	public static void setSceneToStage(Scene newScene) {
		if (newScene == null)
			return;
		mainStage.setScene(newScene);
	}

	public static void showStage() {
		mainStage.show();
	}
	
	public static void backToMainMenu() {
		GameScene.getInventoryPane().remove();
		GameScene.getPausePane().remove();
		setSceneToStage(LandingScene.getScene());
	}
	
	public static void exitGame() {
		System.exit(0);
	}
}
