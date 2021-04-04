package components;

import controller.GameController;
import controller.InterruptController;
import controller.SceneController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import utils.FontUtil;
import utils.RandomUtil;

public class PausePane extends VBox {
	private static SettingPane settingPane = new SettingPane();
	public PausePane() {
		super();
		
		setAlignment(Pos.CENTER);
		setStyle("-fx-background-color: white");
		
		addTitle();
		addResumeBtn();
		addSettingBtn();
		addToMainMenuBtn();
		addExitBtn();
		
		setOnKeyPressed((event) -> {
			System.out.println("Run Pause");
			if(event.getCode() == KeyCode.ESCAPE) {
				remove();
				InterruptController.setOpenFromInside(true);
			}
		});
		
	}

	private void addTitle() {
		Text titleText = new Text("Paused");
		
		titleText.setFont(FontUtil.getLargeFont());
		
		getChildren().add(titleText);
	}
	
	private void addResumeBtn() {
		Button resumeBtn = new Button("Resume");

		resumeBtn.setOnMouseClicked((event) -> {
			remove();
		});

		getChildren().add(resumeBtn);
	}

	private void addSettingBtn() {
		Button settingBtn = new Button("Setting");

		settingBtn.setOnMouseClicked((event) -> {
			((StackPane) getParent()).getChildren().add(settingPane);
			settingPane.requestFocus();
			InterruptController.setSettingOpen(true);
		});

		getChildren().add(settingBtn);
	}
	
	private void addToMainMenuBtn() {
		Button toMainMenuBtn = new Button("Back to main menu");

		toMainMenuBtn.setOnMouseClicked((event) -> {
			GameController.exitToMainMenu();
			InterruptController.resetInterruptState();
		});

		getChildren().add(toMainMenuBtn);
	}
	
	private void addExitBtn() {
		Button exitBtn = new Button("Exit");

		exitBtn.setOnMouseClicked((event) -> {
			SceneController.exitGame();
		});

		getChildren().add(exitBtn);
	}
	
	public void remove() {
		try {
			((StackPane) getParent()).getChildren().remove(this);
			InterruptController.setPauseOpen(false);
		} catch(ClassCastException e) {
			System.out.println(this.getClass().getName() + " has already closed");
		} catch (NullPointerException e) {
			System.out.println(this.getClass().getName() + " has not opened yet.");
		}
	}
}
