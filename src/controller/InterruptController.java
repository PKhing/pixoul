package controller;

public class InterruptController {
	private static boolean isPauseOpen;
	private static boolean isInventoryOpen;
	private static boolean isSettingOpen;
	private static boolean isOpenFromInside;
	private static boolean isTransition;
	private static boolean isImmobilize;
	
	public static void resetInterruptState() {
		isPauseOpen = false;
		isInventoryOpen = false;
		isSettingOpen = false;
		isTransition = false;
		isImmobilize = false;
	}
	
	public static boolean isInterruptPlayerInput() {
		return isPauseOpen || isInventoryOpen || isTransition;
	}
	
	public static boolean isPauseOpen() {
		return isPauseOpen;
	}

	public static void setPauseOpen(boolean isPauseOpen) {
		InterruptController.isPauseOpen = isPauseOpen;
	}

	public static boolean isInventoryOpen() {
		return isInventoryOpen;
	}

	public static void setInventoryOpen(boolean isInventoryOpen) {
		InterruptController.isInventoryOpen = isInventoryOpen;
	}

	public static boolean isSettingOpen() {
		return isSettingOpen;
	}

	public static void setSettingOpen(boolean isSettingOpen) {
		InterruptController.isSettingOpen = isSettingOpen;
	}

	public static boolean isOpenFromInside() {
		return isOpenFromInside;
	}

	public static void setOpenFromInside(boolean isOpenFromInside) {
		InterruptController.isOpenFromInside = isOpenFromInside;
	}

	public static boolean isTransition() {
		return isTransition;
	}

	public static void setTransition(boolean isTransition) {
		InterruptController.isTransition = isTransition;
	}

	public static boolean isImmobilize() {
		return isImmobilize;
	}

	public static void setImmobilize(boolean isImmobilize) {
		InterruptController.isImmobilize = isImmobilize;
	}

}
