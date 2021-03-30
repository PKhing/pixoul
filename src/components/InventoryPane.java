package components;

import java.util.ArrayList;

import items.base.Item;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import logic.GameLogic;
import utils.DrawUtil;
import utils.GameConfig;
import utils.Util;

public class InventoryPane extends FlowPane {
	public InventoryPane() {
		super();
		this.setStyle("-fx-background-color: white");
		this.setPrefHeight(200 * GameConfig.getScale());
		this.setPrefWidth(160 * GameConfig.getScale());
		this.setMaxHeight(200 * GameConfig.getScale());
		this.setMaxWidth(160 * GameConfig.getScale());
		addHeader();
		ArrayList<Item> itemList = GameLogic.getItemList();
		for (int i = 0; i < GameConfig.MAX_ITEM; i++) {
			if (i < itemList.size())
				addItem(itemList.get(i));
			else
				addItem(null);
		}
	}

	private void addItem(Item item) {

		// TODO render item

		Canvas canvas = new Canvas(40 * GameConfig.getScale(), 40 * GameConfig.getScale());
		this.getChildren().add(canvas);
		PixelReader headerSprite = DrawUtil.getImagePixelReader("sprites/inventory/item.png");
		WritableImage img = new WritableImage(headerSprite, 0, 0, 40, 40);
		canvas.getGraphicsContext2D().drawImage(DrawUtil.scaleUp(img), 0, 0);
	}

	private void addHeader() {

		StackPane header = new StackPane();
		this.getChildren().add(header);

		// Texture
		Canvas canvas = new Canvas(160 * GameConfig.getScale(), 40 * GameConfig.getScale());
		header.getChildren().add(canvas);
		PixelReader headerSprite = DrawUtil.getImagePixelReader("sprites/inventory/header.png");
		WritableImage img = new WritableImage(headerSprite, 0, 0, 160, 40);
		canvas.getGraphicsContext2D().drawImage(DrawUtil.scaleUp(img), 0, 0);

		// Text
		Text text = new Text("Inventory");
		header.getChildren().add(text);
		text.setFont(Util.getLargeFont());
		text.setFill(Color.rgb(123, 126, 94));
		
		// Exit button
		Text exit = new Text("x ");
		StackPane.setAlignment(exit, Pos.TOP_RIGHT);
		header.getChildren().add(exit);
		exit.setFont(Util.getLargeFont());
		exit.setFill(Color.rgb(123, 126, 94));
		exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				((Pane)getParent()).getChildren().remove(InventoryPane.this);
			}

		});
		
	}
}
