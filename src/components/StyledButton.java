package components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import utils.FontUtil;
import utils.GameConfig;

/**
 * The StyledButton is {@link Button} that specifically using in {@link LandingPane} and {@link PausePane}
 *
 */
public class StyledButton extends Button {
	public StyledButton(int widthBox, String text, Color onHover, Color bgColor) {
		this.setText(text);
		this.setPadding(new Insets(-GameConfig.getScale()));
		this.setFont(FontUtil.getFont(16));
		this.setMinWidth(widthBox * GameConfig.getScale());
		this.setBackground(new Background(new BackgroundFill(bgColor, null, null)));
		this.setBorder(new Border(new BorderStroke(bgColor, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				new BorderWidths(GameConfig.getScale()))));

		this.setOnMouseEntered((event) -> {
			this.setBorder(new Border(new BorderStroke(onHover, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
					new BorderWidths(GameConfig.getScale()))));
		});
		this.setOnMouseExited((event) -> {
			this.setBorder(new Border(new BorderStroke(bgColor, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
					new BorderWidths(GameConfig.getScale()))));
		});

	}
}
