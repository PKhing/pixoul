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
import javafx.scene.text.Text;
import utils.FontUtil;
import utils.GameConfig;

public class PausePane extends VBox {
	private static SettingPane settingPane = new SettingPane();
	private final int heightBox = 90;
	private final int widthBox = 120;

	class ButtonWithStyle extends Button {
		ButtonWithStyle(String text) {
			this.setText(text);
			this.setPadding(new Insets(-GameConfig.getScale()));
			this.setFont(FontUtil.getFont(16));
			this.setMinWidth(widthBox* GameConfig.getScale());
			this.setBackground(new Background(new BackgroundFill(Color.rgb(245, 246, 231), null, null)));
			this.setBorder(new Border(new BorderStroke(Color.rgb(245, 246, 231), BorderStrokeStyle.SOLID,
					CornerRadii.EMPTY, new BorderWidths(GameConfig.getScale()))));

			this.setOnMouseEntered((event) -> {
				this.setBorder(new Border(new BorderStroke(Color.rgb(87, 89, 66), BorderStrokeStyle.SOLID,
						CornerRadii.EMPTY, new BorderWidths(GameConfig.getScale()))));
			});
			this.setOnMouseExited((event) -> {
				this.setBorder(new Border(new BorderStroke(Color.rgb(245, 246, 231), BorderStrokeStyle.SOLID,
						CornerRadii.EMPTY, new BorderWidths(GameConfig.getScale()))));
			});

		}
	}

	public PausePane() {
		super();

		setSpacing(10.0);
		setAlignment(Pos.CENTER);
		this.setBackground(new Background(new BackgroundFill(Color.rgb(245, 246, 231), null, null)));
		this.setPadding(new Insets(5 * GameConfig.getScale()));
		this.setBorder(new Border(new BorderStroke(Color.rgb(87, 89, 66), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				new BorderWidths(GameConfig.getScale()))));
		setPrefHeight(heightBox * GameConfig.getScale());
		setPrefWidth(widthBox * GameConfig.getScale());
		setMaxHeight(heightBox * GameConfig.getScale());
		setMaxWidth(widthBox * GameConfig.getScale());

		addResumeBtn();
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

	private void addResumeBtn() {
		Button resumeBtn = new ButtonWithStyle("Resume");

		resumeBtn.setOnMouseClicked((event) -> {
			remove();
		});

		getChildren().add(resumeBtn);
	}

	private void addSettingBtn() {
		Button settingBtn = new ButtonWithStyle("Setting");

		settingBtn.setOnMouseClicked((event) -> {
			((StackPane) getParent()).getChildren().add(settingPane);
			settingPane.requestFocus();
			InterruptController.setSettingOpen(true);
		});

		getChildren().add(settingBtn);
	}

	private void addToMainMenuBtn() {
		Button toMainMenuBtn = new ButtonWithStyle("Back to main menu");

		toMainMenuBtn.setOnMouseClicked((event) -> {
			GameController.exitToMainMenu();
			InterruptController.resetInterruptState();
		});

		getChildren().add(toMainMenuBtn);
	}

	private void addExitBtn() {
		Button exitBtn = new ButtonWithStyle("Exit");

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
