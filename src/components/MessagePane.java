package components;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.GameConfig;
import utils.Util;

public class MessagePane extends VBox {
	private final int MAX_MESSAGE = 4;

	public MessagePane() {
		AnchorPane.setBottomAnchor(this, 0.0);
		AnchorPane.setLeftAnchor(this, 0.0);
		this.setPrefHeight(50.0 * GameConfig.getScale());
		this.setPrefWidth(140.0 * GameConfig.getScale());
		this.setStyle("-fx-background-color: rgba(0,0,0, 0.5);-fx-padding:7 7 7 14");

	}

	public void addMessage(String text) {
		if (this.getChildren().size() >= MAX_MESSAGE) {
			this.getChildren().remove(0);
		}
		Text message = new Text(text);
		message.setFont(Util.getFont());
		message.setFill(Color.WHITE);
		this.getChildren().add(message);
	}
}
