package components;

import items.base.Item;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.FontUtil;
import utils.GameConfig;

/**
 * The ItemInfoPane class is the pane that shows the name and description of an individual
 * {@link Item}. This pane will be shown when the mouse hovers over an item
 * frame.
 *
 */
public class ItemInfoPane extends VBox {
	/**
	 * Creates new ItemInfoPane.
	 * 
	 * @param item The item to be shown
	 * @param y    The position of item frame in the Y-axis
	 * @param x    The position of item frame in the X-axis
	 */
	public ItemInfoPane(Item item, int y, int x) {

		// Set anchor
		if (x < (GameConfig.getScreenWidth() / 2)) {
			AnchorPane.setLeftAnchor(this, (double) (x + 45 * GameConfig.getScale()));
		} else {
			AnchorPane.setLeftAnchor(this, (double) (x - 105 * GameConfig.getScale()));
		}
		AnchorPane.setTopAnchor(this, (double) y);

		// Set style
		this.setBackground(new Background(new BackgroundFill(Color.rgb(245, 246, 231), null, null)));
		this.setPadding(new Insets(0, 5 * GameConfig.getScale(), 5 * GameConfig.getScale(), 5 * GameConfig.getScale()));
		this.setBorder(new Border(new BorderStroke(Color.rgb(87, 89, 66), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				new BorderWidths(GameConfig.getScale()))));

		addItemName(item);
		addItemDescription(item);
	}

	/**
	 * Adds item name to this ItemInfoPane.
	 * 
	 * @param item the item to be added.
	 */
	private void addItemName(Item item) {

		Text name = new Text(item.getName());

		name.setFont(FontUtil.getFont(18));
		name.setWrappingWidth(90.0 * GameConfig.getScale());
		name.setFill(Color.rgb(76, 77, 56));

		this.getChildren().add(name);
	}

	/**
	 * Adds item description to this ItemInfoPane.
	 * 
	 * @param item the item to be added.
	 */
	private void addItemDescription(Item item) {

		Text description = new Text(item.getDescription());

		description.setWrappingWidth(90.0 * GameConfig.getScale());
		description.setFont(FontUtil.getFont(12));
		description.setFill(Color.rgb(94, 94, 70));

		this.getChildren().add(description);
	}
}
