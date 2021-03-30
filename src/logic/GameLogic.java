package logic;

import java.util.ArrayList;

import items.base.Item;

public class GameLogic {
	private static ArrayList<Item> itemList = new ArrayList<Item>();

	public static ArrayList<Item> getItemList() {
		return itemList;
	}

	public static void setItemList(ArrayList<Item> itemList) {
		GameLogic.itemList = itemList;
	}

}
