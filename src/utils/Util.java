package utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javafx.scene.text.Font;

public class Util {
	private static Random rand = new Random();
	private static Font font;
	private static Font lagreFont;

	public static int random(int st, int ed) {
		return st + rand.nextInt(ed - st + 1);
	}

	public static Font getFont() {
		if(font == null) {
			font = Font.loadFont(ClassLoader.getSystemResourceAsStream("font.ttf"), 12*GameConfig.getScale());
		}
		return font;
	}
	public static Font getLargeFont() {
		if(lagreFont==null) {
			lagreFont= Font.loadFont(ClassLoader.getSystemResource("font.ttf").toString(), 30*GameConfig.getScale());
		}
		return lagreFont;
	}
	public static void shuffle(Integer[] intArray) {
		List<Integer> intList = Arrays.asList(intArray);
		Collections.shuffle(intList);
		intList.toArray(intArray);

	}
}
