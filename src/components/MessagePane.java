package components;

import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.FontUtil;
import utils.GameConfig;

public class MessagePane extends VBox {
	private final int MAX_MESSAGE = 8;

	public MessagePane() {
		super();
		AnchorPane.setBottomAnchor(this, 0.0);
		AnchorPane.setLeftAnchor(this, 0.0);
		this.setPrefHeight(50.0 * GameConfig.getScale());
		this.setPrefWidth(140.0 * GameConfig.getScale());
		this.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));
		this.setPadding(new Insets(7, 7, 7, 14));
	}

	public void addMessage(String text) {
		if (this.getChildren().size() >= MAX_MESSAGE) {
			this.getChildren().remove(0);
		}
		Text message = new Text(text);
		message.setWrappingWidth(200.0 * GameConfig.getScale());
		message.setFont(FontUtil.getFont(12));
		message.setFill(Color.WHITE);
		this.getChildren().add(message);
	}

	public void resetMessage() {
		this.getChildren().clear();
	}
}
