package components;

import java.util.ArrayList;

import controller.GameController;
import controller.InterruptController;
import items.base.Item;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import logic.GameLogic;
import utils.DrawUtil;
import utils.FontUtil;
import utils.GameConfig;

public class InventoryPane extends AnchorPane {
	private FlowPane itemPane;
	private VBox equipmentPane;

	public InventoryPane() {
		super();

		// itemPane
		itemPane = new FlowPane();
		AnchorPane.setLeftAnchor(itemPane, (double) (GameConfig.getScreenWidth() / 2 - 80 * GameConfig.getScale()));
		AnchorPane.setTopAnchor(itemPane, (double) (GameConfig.getScreenHeight() / 2 - 100 * GameConfig.getScale()));
		this.getChildren().add(itemPane);
		itemPane.setPrefHeight(200 * GameConfig.getScale());
		itemPane.setPrefWidth(160 * GameConfig.getScale());
		itemPane.setMaxHeight(200 * GameConfig.getScale());
		itemPane.setMaxWidth(160 * GameConfig.getScale());

		this.setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				this.remove();
				InterruptController.setOpenFromInside(true);
			}
		});

		// EquipmentPane
		equipmentPane = new VBox();
		this.getChildren().add(equipmentPane);
		AnchorPane.setRightAnchor(equipmentPane,
				(double) (GameConfig.getScreenWidth() / 2 - 130 * GameConfig.getScale()));
		AnchorPane.setTopAnchor(equipmentPane,
				(double) (GameConfig.getScreenHeight() / 2 - 60 * GameConfig.getScale()));

		update();

	}

	public void update() {
		itemPane.getChildren().clear();
		equipmentPane.getChildren().clear();

		addHeader();
		ArrayList<Item> itemList = GameLogic.getItemList();
		for (int i = 0; i < GameConfig.MAX_ITEM; i++) {
			if (i < itemList.size())
				addItem(itemList.get(i));
			else
				addItem(null);
		}

		addEquipment(GameController.getPlayer().getEquippedWeapon());
		addEquipment(GameController.getPlayer().getEquippedArmor());
	}

	private void addEquipment(Item item) {

		Canvas canvas = new Canvas(40 * GameConfig.getScale(), 40 * GameConfig.getScale());
		equipmentPane.getChildren().add(canvas);
		PixelReader itemFrame = DrawUtil.getImagePixelReader("sprites/inventory/item.png");

		WritableImage img = new WritableImage(itemFrame, 0, 0, 40, 40);
		canvas.getGraphicsContext2D().drawImage(DrawUtil.scaleUp(img), 0, 0);

		if (item != null) {
			canvas.setOnMouseClicked((mouseEvent) -> {
				if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
		            if(mouseEvent.getClickCount() == 2){
		            	GameController.getPlayer().unEquipItem(item);
		            	update();
		            }
		        }
			});
			DrawUtil.drawItem(canvas.getGraphicsContext2D(), 8, 8, item);
		}

	}

	private void addItem(Item item) {

		Canvas canvas = new Canvas(40 * GameConfig.getScale(), 40 * GameConfig.getScale());
		itemPane.getChildren().add(canvas);
		PixelReader itemFrame = DrawUtil.getImagePixelReader("sprites/inventory/item.png");

		

		WritableImage img = new WritableImage(itemFrame, 0, 0, 40, 40);
		canvas.getGraphicsContext2D().drawImage(DrawUtil.scaleUp(img), 0, 0);

		if (item != null) {
			DrawUtil.drawItem(canvas.getGraphicsContext2D(), 8, 8, item);
			canvas.setOnMouseClicked((mouseEvent) -> {
				if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
		            if(mouseEvent.getClickCount() == 2){
		            	GameController.getPlayer().equipItem(item);
		            	update();
		            }
		        }
			});
		}

	}

	private void addHeader() {

		StackPane header = new StackPane();
		itemPane.getChildren().add(header);

		// Texture
		Canvas canvas = new Canvas(160 * GameConfig.getScale(), 40 * GameConfig.getScale());
		header.getChildren().add(canvas);
		PixelReader headerSprite = DrawUtil.getImagePixelReader("sprites/inventory/header.png");
		WritableImage img = new WritableImage(headerSprite, 0, 0, 160, 40);
		canvas.getGraphicsContext2D().drawImage(DrawUtil.scaleUp(img), 0, 0);

		// Text
		Text text = new Text("Inventory");
		header.getChildren().add(text);
		text.setFont(FontUtil.getLargeFont());
		text.setFill(Color.rgb(123, 126, 94));

		// Exit button
		Text exit = new Text("x ");
		StackPane.setAlignment(exit, Pos.TOP_RIGHT);
		header.getChildren().add(exit);
		exit.setFont(FontUtil.getLargeFont());
		exit.setFill(Color.rgb(123, 126, 94));
		exit.setOnMouseClicked((event) -> {
			this.remove();
		});
	}

	public void remove() {
		try {
			((Pane) getParent()).getChildren().remove(InventoryPane.this);
			InterruptController.setInventoryOpen(false);
		} catch (ClassCastException e) {
			System.out.println(this.getClass().getName() + " has already closed");
		} catch (NullPointerException e) {
			System.out.println(this.getClass().getName() + " has not opened yet.");
		}
	}
}
