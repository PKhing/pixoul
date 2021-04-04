package components;

import controller.GameController;
import controller.InterruptController;
import items.base.Item;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import utils.DrawUtil;
import utils.GameConfig;

public class EquipmentPane extends VBox {
	public EquipmentPane() {
		super();
		AnchorPane.setRightAnchor(this, (double) (GameConfig.getScreenWidth() / 2 - 130 * GameConfig.getScale()));
		AnchorPane.setTopAnchor(this, (double) (GameConfig.getScreenHeight() / 2 - 60 * GameConfig.getScale()));
		addItem(GameController.getPlayer().getEquippedWeapon());
		addItem(GameController.getPlayer().getEquippedArmor());
	}

	private void addItem(Item item) {

		// TODO render item

		Canvas canvas = new Canvas(40 * GameConfig.getScale(), 40 * GameConfig.getScale());
		this.getChildren().add(canvas);
		PixelReader itemFrame = DrawUtil.getImagePixelReader("sprites/inventory/item.png");

		canvas.setOnMouseClicked((event) -> {
			System.out.println("Hello World");
		});

		WritableImage img = new WritableImage(itemFrame, 0, 0, 40, 40);
		canvas.getGraphicsContext2D().drawImage(DrawUtil.scaleUp(img), 0, 0);

	}

	public void remove() {
		try {
			((Pane) getParent()).getChildren().remove(EquipmentPane.this);
		} catch (ClassCastException e) {
			System.out.println(this.getClass().getName() + " has already closed.");
		} catch (NullPointerException e) {
			System.out.println(this.getClass().getName() + " has not opened yet.");
		}
	}
}
