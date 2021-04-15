package components;

import controller.GameController;
import controller.SceneController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import utils.FontUtil;
import utils.GameConfig;

public class GameOverPane extends VBox {
	private int heightBox = 40;

	private int widthBox = 80;

	private Text gameOverTitle;

	private VBox buttonBox;

	public GameOverPane() {
		styleSetup();
		addGameOverTitle();
		addButtonBox();
		addButtontoBox();

		getChildren().addAll(gameOverTitle, buttonBox);
	}

	private void styleSetup() {
		setPrefHeight(heightBox * GameConfig.getScale());
		setPrefWidth(widthBox * GameConfig.getScale());
		setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		setAlignment(Pos.CENTER);
		setSpacing(20.0);
	}

	private void addGameOverTitle() {
		gameOverTitle = new Text("Game Over");
		gameOverTitle.setFont(FontUtil.getFont(30));
		gameOverTitle.setFill(Color.RED);
		gameOverTitle.setTextAlignment(TextAlignment.CENTER);
	}

	private void addButtonBox() {
		buttonBox = new VBox();
		buttonBox.setSpacing(8.0 * GameConfig.getScale());
		buttonBox.setAlignment(Pos.CENTER);
	}

	private void addButtontoBox() {
		Button startNewGameBtn = new StyledButton(widthBox * GameConfig.getScale(), "Start New Game", Color.WHITE,
				Color.BLACK);
		startNewGameBtn.setTextFill(Color.WHITE);
		startNewGameBtn.setOnMouseClicked((event) -> GameController.start());

		Button backToMenuBtn = new StyledButton(widthBox * GameConfig.getScale(), "Back To Menu", Color.WHITE,
				Color.BLACK);
		backToMenuBtn.setTextFill(Color.WHITE);
		backToMenuBtn.setOnMouseClicked((event) -> SceneController.backToMainMenu());

		Button exitBtn = new StyledButton(widthBox * GameConfig.getScale(), "Exit Game", Color.WHITE, Color.BLACK);
		exitBtn.setTextFill(Color.WHITE);
		exitBtn.setOnMouseClicked((event) -> SceneController.exitGame());

		buttonBox.getChildren().addAll(startNewGameBtn, backToMenuBtn, exitBtn);
	}
}
