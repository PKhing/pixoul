package components;

import java.util.List;
import controller.GameController;
import controller.InterruptController;
import entity.base.DispatchAction;
import items.base.Armor;
import items.base.Item;
import items.base.Weapon;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
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
import scene.GameScene;
import utils.DrawUtil;
import utils.FontUtil;
import utils.GameConfig;

public class InventoryPane extends AnchorPane {
	private FlowPane itemPane;
	private VBox equipmentPane;
	private boolean isDeleteMode = false;
	private static WritableImage itemFrameSprite = DrawUtil.getWritableImage("sprites/inventory/itemFrame.png");
	private static WritableImage headerBackground = DrawUtil.getWritableImage("sprites/inventory/header.png");
	private static WritableImage binSprite = DrawUtil.getWritableImage("sprites/inventory/bin.png");

	public InventoryPane() {

		this.setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				this.remove();
				InterruptController.setOpenFromInside(true);
			}
		});

		// itemPane
		itemPane = new FlowPane();
		AnchorPane.setLeftAnchor(itemPane, (double) (GameConfig.getScreenWidth() / 2 - 80 * GameConfig.getScale()));
		AnchorPane.setTopAnchor(itemPane, (double) (GameConfig.getScreenHeight() / 2 - 100 * GameConfig.getScale()));
		this.getChildren().add(itemPane);
		itemPane.setPrefHeight(200 * GameConfig.getScale());
		itemPane.setPrefWidth(160 * GameConfig.getScale());
		itemPane.setMaxHeight(200 * GameConfig.getScale());
		itemPane.setMaxWidth(160 * GameConfig.getScale());

		// EquipmentPane
		equipmentPane = new VBox();
		this.getChildren().add(equipmentPane);
		AnchorPane.setRightAnchor(equipmentPane,
				(double) (GameConfig.getScreenWidth() / 2 - 130 * GameConfig.getScale()));
		AnchorPane.setTopAnchor(equipmentPane,
				(double) (GameConfig.getScreenHeight() / 2 - 60 * GameConfig.getScale()));

		// DeleteButton
		this.addDeleteButton();

		this.update();

	}

	public void update() {
		itemPane.getChildren().clear();
		equipmentPane.getChildren().clear();

		addHeader();
		List<Item> itemList = GameController.getPlayer().getItemList();
		for (int i = 0; i < GameConfig.MAX_ITEM; i++) {
			if (i < itemList.size())
				addItem(itemList.get(i), itemPane);
			else
				addItem(null, itemPane);
		}

		addItem(GameController.getPlayer().getEquippedWeapon(), equipmentPane);
		addItem(GameController.getPlayer().getEquippedArmor(), equipmentPane);
	}

	private void addItem(Item item, Pane parent) {

		// Draw item frame
		Canvas canvas = new Canvas(40 * GameConfig.getScale(), 40 * GameConfig.getScale());
		canvas.getGraphicsContext2D().drawImage(DrawUtil.scaleUp(itemFrameSprite, GameConfig.getScale()), 0, 0);
		parent.getChildren().add(canvas);

		if (item == null) {
			// Mouse click
			canvas.setOnMouseClicked((event) -> {
				if (isDeleteMode == true) {
					isDeleteMode = false;
					GameScene.getScene().setCursor(Cursor.DEFAULT);
				}
			});
		} else {

			// Draw item
			DrawUtil.drawItem(canvas.getGraphicsContext2D(), 4 * GameConfig.getScale(), 4 * GameConfig.getScale(),
					item);

			// Mouse click
			canvas.setOnMouseClicked((mouseEvent) -> {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (isDeleteMode) {
						GameLogic.gameUpdate(DispatchAction.DELETE_ITEM, item);
					} else if (mouseEvent.getClickCount() == 2) {
						Weapon currentWeapon = GameController.getPlayer().getEquippedWeapon();
						Armor currentArmor = GameController.getPlayer().getEquippedArmor();
						if (item == currentWeapon || item == currentArmor) {
							GameLogic.gameUpdate(DispatchAction.UNEQUIP, item);
						} else {
							if (item instanceof Weapon && currentWeapon != null) {
								GameLogic.gameUpdate(DispatchAction.SWITCH_EQUIP, item);
							} else if (item instanceof Armor && currentArmor != null) {
								GameLogic.gameUpdate(DispatchAction.SWITCH_EQUIP, item);
							} else {
								GameLogic.gameUpdate(DispatchAction.USE_ITEM, item);
							}
						}
					}
				} else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
					if (isDeleteMode == true) {
						isDeleteMode = false;
						GameScene.getScene().setCursor(Cursor.DEFAULT);
					}
				}
			});

			// ItemInfoPane
			canvas.setOnMouseEntered((event) -> {
				this.getChildren().add(new ItemInfoPane(item, (int) canvas.getLayoutY() + (int) parent.getLayoutY(),
						(int) canvas.getLayoutX() + (int) parent.getLayoutX()));
			});
			canvas.setOnMouseExited((event) -> {
				this.getChildren().remove(this.getChildren().size() - 1);
			});
		}
	}

	private void addHeader() {

		StackPane header = new StackPane();
		itemPane.getChildren().add(header);

		// Texture
		Canvas canvas = new Canvas(160 * GameConfig.getScale(), 40 * GameConfig.getScale());
		header.getChildren().add(canvas);
		canvas.getGraphicsContext2D().drawImage(DrawUtil.scaleUp(headerBackground, GameConfig.getScale()), 0, 0);

		// Text
		Text text = new Text("Inventory");
		header.getChildren().add(text);
		text.setFont(FontUtil.getFont(30));
		text.setFill(Color.rgb(123, 126, 94));

		// Exit button
		Text exit = new Text("x ");
		StackPane.setAlignment(exit, Pos.TOP_RIGHT);
		header.getChildren().add(exit);
		exit.setFont(FontUtil.getFont(30));
		exit.setFill(Color.rgb(123, 126, 94));
		exit.setOnMouseClicked((event) -> {
			this.remove();
			isDeleteMode = false;
			GameScene.getScene().setCursor(Cursor.DEFAULT);
		});
	}

	private void addDeleteButton() {

		Canvas deleteButton = new Canvas(32 * GameConfig.getScale(), 32 * GameConfig.getScale());
		this.getChildren().add(deleteButton);

		// Set anchor
		AnchorPane.setRightAnchor(deleteButton,
				(double) (GameConfig.getScreenWidth() / 2 - 125 * GameConfig.getScale()));
		AnchorPane.setTopAnchor(deleteButton, (double) (GameConfig.getScreenHeight() / 2 + 30 * GameConfig.getScale()));

		// Set image
		deleteButton.getGraphicsContext2D().drawImage(DrawUtil.scaleUp(binSprite, GameConfig.getScale()), 0, 0);

		// Add event listener
		deleteButton.setOnMouseClicked((event) -> {
			isDeleteMode = !isDeleteMode;
			if (isDeleteMode == true) {
				GameScene.getScene().setCursor(new ImageCursor(binSprite));
			} else {
				GameScene.getScene().setCursor(Cursor.DEFAULT);
			}
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