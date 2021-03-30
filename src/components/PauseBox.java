package components;

import controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import utils.Util;

public class PauseBox extends VBox {
	private static boolean isOpenPause = false;

	public PauseBox() {
		super();

		this.setAlignment(Pos.CENTER);
		this.setStyle("-fx-background-color: white");

		this.addTitle();
		this.addResumeBtn();
		this.addSettingBtn();
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
			((StackPane) this.getParent()).getChildren().add(new OptionBox());
		});

		this.getChildren().add(settingBtn);
	}

	private void addExitBtn() {
		Button exitBtn = new Button("Exit");

		exitBtn.setOnMouseClicked((event) -> {
			GameController.exit();
		});

		this.getChildren().add(exitBtn);
	}

	public static boolean isOpenPause() {
		return isOpenPause;
	}

	public static void setOpenPause(boolean isOpenPause) {
		PauseBox.isOpenPause = isOpenPause;
	}
}
