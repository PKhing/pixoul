package components;

import items.base.Item;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.DrawUtil;
import utils.FontUtil;
import utils.GameConfig;

public class ItemInfoPane extends StackPane {
	public ItemInfoPane(Item item, int y, int x) {
		super();
		if (x < GameConfig.getScreenWidth() / 2) {
			AnchorPane.setLeftAnchor(this, (double) (x + 45 * GameConfig.getScale()));
		} else {
			AnchorPane.setLeftAnchor(this, (double) (x - 105 * GameConfig.getScale()));
		}
		
		AnchorPane.setTopAnchor(this, (double) y);
		addTexture();
		VBox infoPane = new VBox();
		this.getChildren().add(infoPane);
		infoPane.setPadding(new Insets(5 * GameConfig.getScale(), 0, 0, 5 * GameConfig.getScale()));

		Text name = new Text(item.getName());
		name.setFont(FontUtil.getFont(18));
		name.setWrappingWidth(90.0 * GameConfig.getScale());
		name.setFill(Color.rgb(76, 77, 56));
		infoPane.getChildren().add(name);

		Text description = new Text(item.getDescription());
		description.setWrappingWidth(90.0 * GameConfig.getScale());
		description.setFont(FontUtil.getFont(12));
		description.setFill(Color.rgb(94, 94, 70));
		infoPane.getChildren().add(description);

	}

	public void addTexture() {
		Canvas canvas = new Canvas(100 * GameConfig.getScale(), 50 * GameConfig.getScale());
		PixelReader itemInfoSprite = DrawUtil.getImagePixelReader("sprites/itemInfo.png");
		WritableImage img = new WritableImage(itemInfoSprite, 0, 0, 100, 50);
		canvas.getGraphicsContext2D().drawImage(DrawUtil.scaleUp(img, GameConfig.getScale()), 0, 0);
		this.getChildren().add(canvas);
	}
}
