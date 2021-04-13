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

public class ItemInfoPane extends VBox {
	public ItemInfoPane(Item item, int y, int x) {

		// Set anchor
		if (x < GameConfig.getScreenWidth() / 2) {
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

		// Add name
		addItemName(item);
		
		// Add description
		addItemDescription(item);
	}
	
	private void addItemName(Item item) {
		Text name = new Text(item.getName());
		name.setFont(FontUtil.getFont(18));
		name.setWrappingWidth(90.0 * GameConfig.getScale());
		name.setFill(Color.rgb(76, 77, 56));
		this.getChildren().add(name);		
	}
	
	private void addItemDescription(Item item) {
		Text description = new Text(item.getDescription());
		description.setWrappingWidth(90.0 * GameConfig.getScale());
		description.setFont(FontUtil.getFont(12));
		description.setFill(Color.rgb(94, 94, 70));
		this.getChildren().add(description);
	}
}
