package utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Util {
	private static Random rand;

	public static int random(int st, int ed) {
		if (rand == null) {
			rand = new Random();
		}
		return st + rand.nextInt(ed - st + 1);
	}

	public static void shuffle(Integer[] intArray) {
		List<Integer> intList = Arrays.asList(intArray);
		Collections.shuffle(intList);
		intList.toArray(intArray);

	}
}
