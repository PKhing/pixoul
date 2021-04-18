package controller;

import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scene.GameScene;
import scene.LandingScene;
import utils.GameConfig;

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
		if (mainStage.getScene() != null) {
			mainStage.getScene().setCursor(Cursor.DEFAULT);
		}
		mainStage.setScene(newScene);
		newScene.setCursor(Cursor.DEFAULT);
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

	public static Scene makeNewScene(Parent node) {
		Scene scene = new Scene(node, GameConfig.getScreenWidth(), GameConfig.getScreenHeight());
		return scene;
	}
}
