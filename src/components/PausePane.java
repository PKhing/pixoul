package components;

import controller.GameController;
import controller.InterruptController;
import controller.SceneController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import utils.GameConfig;

public class PausePane extends VBox {
	private final int heightBox = 90;
	private final int widthBox = 120;
	
	private static final Color colorOnHover = Color.rgb(87, 89, 66);
	private static final Color colorBg = Color.rgb(245, 246, 231);
	
	private static SettingPane settingPane = new SettingPane();
	
	public PausePane() {
		super();

		setSpacing(10.0);
		setAlignment(Pos.CENTER);
		this.setBackground(new Background(new BackgroundFill(colorBg, null, null)));
		this.setPadding(new Insets(5 * GameConfig.getScale()));
		this.setBorder(new Border(new BorderStroke(colorOnHover, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				new BorderWidths(GameConfig.getScale()))));

		setPrefHeight(heightBox * GameConfig.getScale());
		setPrefWidth(widthBox * GameConfig.getScale());
		setMaxHeight(heightBox * GameConfig.getScale());
		setMaxWidth(widthBox * GameConfig.getScale());

		addResumeBtn();
		addStartNewGameBtn();
		addSettingBtn();
		addToMainMenuBtn();
		addExitBtn();

		setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				remove();
				InterruptController.setOpenFromInside(true);
			}
		});

	}

	private void addStartNewGameBtn() {
		Button startNewGameBtn = new StyledButton(widthBox, "Start New Game", colorOnHover, colorBg);

		startNewGameBtn.setOnMouseClicked((event) -> {
			remove();
			GameController.start();
		});

		getChildren().add(startNewGameBtn);
	}

	private void addResumeBtn() {
		Button resumeBtn = new StyledButton(widthBox, "Resume", colorOnHover, colorBg);

		resumeBtn.setOnMouseClicked((event) -> {
			remove();
		});

		getChildren().add(resumeBtn);
	}

	private void addSettingBtn() {
		Button settingBtn = new StyledButton(widthBox, "Setting", colorOnHover, colorBg);

		settingBtn.setOnMouseClicked((event) -> {
			settingPane.updateSetting();
			((StackPane) getParent()).getChildren().add(settingPane);
			settingPane.requestFocus();
			InterruptController.setSettingOpen(true);
		});

		getChildren().add(settingBtn);
	}

	private void addToMainMenuBtn() {
		Button toMainMenuBtn = new StyledButton(widthBox, "Back to main menu", colorOnHover, colorBg);

		toMainMenuBtn.setOnMouseClicked((event) -> {
			GameController.exitToMainMenu();
			InterruptController.resetInterruptState();
		});

		getChildren().add(toMainMenuBtn);
	}

	private void addExitBtn() {
		Button exitBtn = new StyledButton(widthBox, "Exit", colorOnHover, colorBg);

		exitBtn.setOnMouseClicked((event) -> {
			SceneController.exitGame();
		});

		getChildren().add(exitBtn);
	}

	public void remove() {
		try {
			((StackPane) getParent()).getChildren().remove(this);
			InterruptController.setPauseOpen(false);
		} catch (ClassCastException e) {
			System.out.println(this.getClass().getName() + " has already closed");
		} catch (NullPointerException e) {
			System.out.println(this.getClass().getName() + " has not opened yet.");
		}
	}
}
