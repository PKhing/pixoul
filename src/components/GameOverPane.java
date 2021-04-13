package components;

import controller.GameController;
import controller.SceneController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import utils.FontUtil;
import utils.GameConfig;

public class GameOverPane extends VBox {
	private int heightBox = 40;
	private int widthBox = 80;
	
	public GameOverPane() {
		setPrefHeight(heightBox * GameConfig.getScale());
		setPrefWidth(widthBox * GameConfig.getScale());
		setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		setAlignment(Pos.CENTER);
		setSpacing(20.0);
		
		Text gameOverTitle = new Text("Game Over");
		gameOverTitle.setFont(FontUtil.getFont(30));
		gameOverTitle.setFill(Color.WHITE);
		gameOverTitle.setTextAlignment(TextAlignment.CENTER);
		
		HBox buttonBox = new HBox();
		buttonBox.setSpacing(15.0 * GameConfig.getScale());
		buttonBox.setAlignment(Pos.CENTER);
		
		Button tryAgainBtn = new Button("Try Again");
		
		tryAgainBtn.setOnMouseClicked((event) -> GameController.start());
		
		Button backToMenuBtn = new Button("Back To Main Menu");
		backToMenuBtn.setOnMouseClicked((event) -> SceneController.backToMainMenu());
		
		Button exitBtn = new Button("Exit Game");
		exitBtn.setOnMouseClicked((event) -> SceneController.exitGame());
		
		buttonBox.getChildren().addAll(tryAgainBtn, backToMenuBtn, exitBtn);
		
		getChildren().addAll(gameOverTitle, buttonBox);
	}
}
