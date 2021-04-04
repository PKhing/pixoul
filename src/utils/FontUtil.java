package utils;

import javafx.scene.text.Font;

public class FontUtil {
	private static Font font;
	private static Font largeFont;
	
	public static Font getFont() {
		if (font == null) {
			font = Font.loadFont(ClassLoader.getSystemResourceAsStream("font.ttf"), 12 * GameConfig.getScale());
		}
		return font;
	}

	public static Font getLargeFont() {
		if (largeFont == null) {
			largeFont = Font.loadFont(ClassLoader.getSystemResourceAsStream("font.ttf"), 30 * GameConfig.getScale());
		}
		return largeFont;
	}
}
