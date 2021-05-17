package utils;

import javafx.scene.text.Font;

public class FontUtil {
	private static Font smallFont;
	private static Font mediumFont;
	private static Font largeFont;

	static {
		smallFont = Font.loadFont(ClassLoader.getSystemResourceAsStream("font.ttf"), 12 * GameConfig.getScale());
		mediumFont = Font.loadFont(ClassLoader.getSystemResourceAsStream("font.ttf"), 18 * GameConfig.getScale());
		largeFont = Font.loadFont(ClassLoader.getSystemResourceAsStream("font.ttf"), 30 * GameConfig.getScale());
	}

	public static Font getFont(String size) {
		if (size == "small") {
			return smallFont;
		}
		if (size == "medium") {
			return mediumFont;
		}
		return largeFont;
	}

}
