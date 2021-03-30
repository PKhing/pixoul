package components;

import controller.GameController;
import controller.SceneController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import utils.Util;

public class PausePane extends VBox {
	private static boolean isOpenPause = false;
	
	public PausePane() {
		super();
		
		this.setAlignment(Pos.CENTER);
		this.setStyle("-fx-background-color: white");
		
		this.addTitle();
		this.addResumeBtn();
		this.addSettingBtn();
		this.addToMainMenuBtn();
		this.addExitBtn();
	}

	private void addTitle() {
		Text titleText = new Text("Paused");
		
		titleText.setFont(Util.getLargeFont());
		
		this.getChildren().add(titleText);
	}
	
	private void addResumeBtn() {
		Button resumeBtn = new Button("Resume");

		resumeBtn.setOnMouseClicked((event) -> {
			((StackPane) this.getParent()).getChildren().remove(this);
		});

		this.getChildren().add(resumeBtn);
	}

	private void addSettingBtn() {
		Button settingBtn = new Button("Setting");

		settingBtn.setOnMouseClicked((event) -> {
			((StackPane) this.getParent()).getChildren().add(new SettingPane());
		});

		this.getChildren().add(settingBtn);
	}
	
	private void addToMainMenuBtn() {
		Button toMainMenuBtn = new Button("Back to main menu");

		toMainMenuBtn.setOnMouseClicked((event) -> {
			GameController.exitToMainMenu();
		});

		this.getChildren().add(toMainMenuBtn);
	}
	
	private void addExitBtn() {
		Button exitBtn = new Button("Exit");

		exitBtn.setOnMouseClicked((event) -> {
			SceneController.exitGame();
		});

		this.getChildren().add(exitBtn);
	}

	public static boolean isOpenPause() {
		return isOpenPause;
	}

	public static void setOpenPause(boolean isOpenPause) {
		PausePane.isOpenPause = isOpenPause;
	}
}