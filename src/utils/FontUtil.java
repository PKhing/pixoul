package utils;

import javafx.scene.text.Font;

public class FontUtil {
	private static Font font12;
	private static Font font18;
	private static Font font30;

	public static Font getFont(int size) {
		if (font12 == null) {
			font12 = Font.loadFont(ClassLoader.getSystemResourceAsStream("font.ttf"), 12 * GameConfig.getScale());
			font18 = Font.loadFont(ClassLoader.getSystemResourceAsStream("font.ttf"), 18 * GameConfig.getScale());
			font30 = Font.loadFont(ClassLoader.getSystemResourceAsStream("font.ttf"), 30 * GameConfig.getScale());
		}
		if (size <= 12)
			return font12;
		if (size <= 18)
			return font18;
		return font30;
	}

}
