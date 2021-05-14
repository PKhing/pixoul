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

/**
 * The GameOverPane class represent the pane that show 
 * when {@link Player} health is less than or equal zero
 */

public class GameOverPane extends VBox {
	/**
	 * Represent the height of the box in pixel
	 */
	private int heightBox = 40;

	/**
	 * Represent the width of the box in pixel
	 */
	private int widthBox = 80;

	/**
	 * Represent {@link Text} which is the title text in this pane
	 */
	private Text gameOverTitle;
	
	/**
	 * Represent {@link VBox} which contains all buttons in this pane
	 */
	private VBox buttonBox;

	/**
	 * The constructor of GameOverPane. Initialize the style, button, listener and title. 
	 */
	public GameOverPane() {
		styleSetup();
		addGameOverTitle();
		addButtonBox();
		addButtontoBox();

		getChildren().addAll(gameOverTitle, buttonBox);
	}

	/**
	 * Setup the pane style
	 */
	private void styleSetup() {
		setPrefHeight(heightBox * GameConfig.getScale());
		setPrefWidth(widthBox * GameConfig.getScale());
		setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		setAlignment(Pos.CENTER);
		setSpacing(20.0);
	}

	/**
	 * Initialize the gameOverTitle and add to display in pane
	 */
	private void addGameOverTitle() {
		gameOverTitle = new Text("Game Over");
		gameOverTitle.setFont(FontUtil.getFont(30));
		gameOverTitle.setFill(Color.RED);
		gameOverTitle.setTextAlignment(TextAlignment.CENTER);
		getChildren().add(gameOverTitle);
	}

	/**
	 * Initialize the buttonBox and add to display in pane
	 */
	private void addButtonBox() {
		buttonBox = new VBox();
		buttonBox.setSpacing(8.0 * GameConfig.getScale());
		buttonBox.setAlignment(Pos.CENTER);
		getChildren().add(buttonBox);
	}

	/**
	 * Initialize all {@link Button} that register text and add listener to each button and adding to buttonBox
	 */
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
