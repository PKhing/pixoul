package application;

import logic.MapGenerator;
import logic.Util;

public class Main {
	public static void main(String args[]) {
		MapGenerator mapGenerator = new MapGenerator();
		mapGenerator.generateMap();
		mapGenerator.printMap();
		System.out.println(mapGenerator.getRoomList());
	}
}
