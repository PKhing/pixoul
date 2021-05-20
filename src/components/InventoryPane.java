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
import javafx.scene.canvas.GraphicsContext;
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

/**
 * The InventoryPane is the pane that contains components about an {@link Item item}. It displays the item that the player has and the item that the player is equipping.
 *
 */
public class InventoryPane extends AnchorPane {
	
	/**
	 * The pane that contains all items that the player currently has.
	 */
	private FlowPane itemPane;
	
	/**
	 * The pane that contains items that the player is equipping.
	 */
	private VBox equipmentPane;
	
	/**
	 * Specifies whether this pane is in delete mode or not. If the pane is in
	 * delete mode, left-click on an item will delete it permanently. Right-click on
	 * an item will switch back to normal mode.
	 */
	private boolean isDeleteMode = false;
	
	/**
	 * The sprite of an item frame.
	 */
	private static WritableImage itemFrameSprite = DrawUtil.getWritableImage("sprites/inventory/itemFrame.png");
	
	/**
	 * The background of this pane header.
	 */
	private static WritableImage headerBackground = DrawUtil.getWritableImage("sprites/inventory/header.png");
	
	/**
	 * The sprite of a bin.
	 */
	private static WritableImage binSprite = DrawUtil.getWritableImage("sprites/inventory/bin.png");

	/**
	 * Creates new InventoryPane.
	 */
	public InventoryPane() {

		// Press Esc to close InventoryPane
		this.setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				remove();
			}
		});

		this.addItemPane();
		this.addEquipmentPane();
		this.addDeleteButton();

		this.update();
	}

	/**
	 * Updates item in this InventoryPane.
	 */
	public void update() {
		itemPane.getChildren().clear();
		equipmentPane.getChildren().clear();

		addHeader();

		List<Item> itemList = GameController.getPlayer().getItemList();
		for (int i = 0; i < GameConfig.MAX_ITEM; i++) {
			if (i < itemList.size()) {
				addItem(itemList.get(i), itemPane);
			} else {
				addItem(null, itemPane);
			}
		}
		addItem(GameController.getPlayer().getEquippedWeapon(), equipmentPane);
		addItem(GameController.getPlayer().getEquippedArmor(), equipmentPane);
	}

	/**
	 * Removes this InventoryPane from {@link GameScene}.
	 */
	public void remove() {
		try {
			((Pane) getParent()).getChildren().remove(this);
			InterruptController.setInventoryOpen(false);
		} catch (ClassCastException e) {
			System.out.println(this.getClass().getName() + " has already closed");
		} catch (NullPointerException e) {
			System.out.println(this.getClass().getName() + " has not opened yet.");
		}
	}

	/* Header */

	/**
	 * Adds header to this InventoryPane.
	 */
	private void addHeader() {
		StackPane header = new StackPane();

		addTextureToHeader(header);
		addInventoryText(header);
		addExitButton(header);

		itemPane.getChildren().add(header);
	}

	/**
	 * Adds texture to header pane.
	 * 
	 * @param header Header pane
	 */
	private void addTextureToHeader(StackPane header) {
		Canvas canvas = new Canvas(160 * GameConfig.getScale(), 40 * GameConfig.getScale());
		canvas.getGraphicsContext2D().drawImage(DrawUtil.scaleUp(headerBackground, GameConfig.getScale()), 0, 0);

		header.getChildren().add(canvas);
	}

	/**
	 * Adds "Inventory" text to header pane.
	 * 
	 * @param header Header pane
	 */
	private void addInventoryText(StackPane header) {
		Text text = new Text("Inventory");
		text.setFont(FontUtil.getFont("large"));
		text.setFill(Color.rgb(123, 126, 94));

		header.getChildren().add(text);
	}

	/**
	 * Adds exit button to header pane.
	 * 
	 * @param header Header pane
	 */
	private void addExitButton(StackPane header) {
		Text exit = new Text("x ");
		StackPane.setAlignment(exit, Pos.TOP_RIGHT);
		exit.setFont(FontUtil.getFont("large"));
		exit.setFill(Color.rgb(123, 126, 94));
		exit.setOnMouseClicked((event) -> {
			this.remove();
			isDeleteMode = false;
			GameScene.getScene().setCursor(Cursor.DEFAULT);
		});

		header.getChildren().add(exit);
	}

	/* Item */

	/**
	 * Adds itemPane to InventoryPane.
	 */
	private void addItemPane() {
		itemPane = new FlowPane();
		AnchorPane.setLeftAnchor(itemPane, (double) (GameConfig.getScreenWidth() / 2 - 80 * GameConfig.getScale()));
		AnchorPane.setTopAnchor(itemPane, (double) (GameConfig.getScreenHeight() / 2 - 100 * GameConfig.getScale()));
		itemPane.setPrefSize(160 * GameConfig.getScale(), 200 * GameConfig.getScale());
		itemPane.setMaxSize(160 * GameConfig.getScale(), 200 * GameConfig.getScale());

		this.getChildren().add(itemPane);
	}

	/**
	 * Adds equipmentPane to InventoryPane.
	 */
	private void addEquipmentPane() {
		equipmentPane = new VBox();
		AnchorPane.setRightAnchor(equipmentPane,
				(double) (GameConfig.getScreenWidth() / 2 - 130 * GameConfig.getScale()));
		AnchorPane.setTopAnchor(equipmentPane,
				(double) (GameConfig.getScreenHeight() / 2 - 60 * GameConfig.getScale()));

		this.getChildren().add(equipmentPane);
	}

	/**
	 * Adds item canvas to the specified pane.
	 * 
	 * @param item   The item to be added
	 * @param parent The pane that item canvas will be added
	 */
	private void addItem(Item item, Pane parent) {

		Canvas canvas = new Canvas(40 * GameConfig.getScale(), 40 * GameConfig.getScale());

		// Draws item frame
		canvas.getGraphicsContext2D().drawImage(DrawUtil.scaleUp(itemFrameSprite, GameConfig.getScale()), 0, 0);
		parent.getChildren().add(canvas);

		if (item == null) {

			// If player clicks on the blank item frame in delete mode, the mode will be
			// switch to normal.
			canvas.setOnMouseClicked((event) -> {
				if (isDeleteMode == true) {
					isDeleteMode = false;
					GameScene.getScene().setCursor(Cursor.DEFAULT);
				}
			});
		} else {

			// Draw item
			GraphicsContext gc = canvas.getGraphicsContext2D();
			DrawUtil.drawItem(gc, 4 * GameConfig.getScale(), 4 * GameConfig.getScale(), item);

			canvas.setOnMouseClicked((mouseEvent) -> {
				// Left click
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

					// If the player left-clicks an item in delete mode, it will be deleted
					// permanently.
					if (isDeleteMode) {
						GameLogic.gameUpdate(DispatchAction.DELETE_ITEM, item);
					}

					// If the player double-clicks an item in normal mode, it will be used or
					// equipped.
					else if (mouseEvent.getClickCount() == 2) {
						Weapon currentWeapon = GameController.getPlayer().getEquippedWeapon();
						Armor currentArmor = GameController.getPlayer().getEquippedArmor();

						if ((item == currentWeapon) || (item == currentArmor)) {
							GameLogic.gameUpdate(DispatchAction.UNEQUIP, item);
						} else {
							if ((item instanceof Weapon) && (currentWeapon != null)) {
								GameLogic.gameUpdate(DispatchAction.SWITCH_EQUIP, item);
							} else if ((item instanceof Armor) && (currentArmor != null)) {
								GameLogic.gameUpdate(DispatchAction.SWITCH_EQUIP, item);
							} else {
								GameLogic.gameUpdate(DispatchAction.USE_ITEM, item);
							}
						}
					}
				}

				// Right click
				else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {

					// If the player right-clicks an item in delete mode, it will be switched to
					// normal.
					if (isDeleteMode) {
						isDeleteMode = false;
						GameScene.getScene().setCursor(Cursor.DEFAULT);
					}
				}
			});

			// ItemInfoPane
			// Shows ItemInfoPane when the mouse is hovering on item canvas.
			canvas.setOnMouseEntered((event) -> {
				this.getChildren().add(new ItemInfoPane(item, (int) canvas.getLayoutY() + (int) parent.getLayoutY(),
						(int) canvas.getLayoutX() + (int) parent.getLayoutX()));
			});
			canvas.setOnMouseExited((event) -> {
				this.getChildren().remove(this.getChildren().size() - 1);
			});
		}
	}

	/* Delete Button */

	/**
	 * Adds deleteButton to InventoryPane.
	 */
	private void addDeleteButton() {
		Canvas deleteButton = new Canvas(32 * GameConfig.getScale(), 32 * GameConfig.getScale());

		AnchorPane.setRightAnchor(deleteButton,
				(double) (GameConfig.getScreenWidth() / 2 - 125 * GameConfig.getScale()));
		AnchorPane.setTopAnchor(deleteButton, (double) (GameConfig.getScreenHeight() / 2 + 30 * GameConfig.getScale()));
		deleteButton.getGraphicsContext2D().drawImage(DrawUtil.scaleUp(binSprite, GameConfig.getScale()), 0, 0);

		deleteButton.setOnMouseClicked((event) -> {

			// Switches mode from normal to delete or delete to normal.
			isDeleteMode = !isDeleteMode;
			if (isDeleteMode) {
				GameScene.getScene().setCursor(new ImageCursor(binSprite));
			} else {
				GameScene.getScene().setCursor(Cursor.DEFAULT);
			}
		});

		this.getChildren().add(deleteButton);
	}
}