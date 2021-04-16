package utils;

public class MonsterLevelFilter {
//	(DARK_MAGE),(HAUNTED_MAID),(PUMPKIN_HEAD),(REAPER),(SKELETON),(SOUL)

	private int level;
	private boolean isDarkMageAppear;
	private boolean isHauntedMaidAppear;
	private boolean isPumpkinHeadAppear;
	private boolean isReaperAppear;
	private boolean isSkeletonAppear;
	private boolean isSoulAppear;

	public MonsterLevelFilter(int level, boolean isDarkMageAppear, boolean isHauntedMaidAppear, boolean isPumpkinHeadAppear,
			boolean isReaperAppear, boolean isSkeletonAppear, boolean isSoulAppear) {
		setLevel(level);
		setDarkMageAppear(isDarkMageAppear);
		setHauntedMaidAppear(isHauntedMaidAppear);
		setPumpkinHeadAppear(isPumpkinHeadAppear);
		setReaperAppear(isReaperAppear);
		setSkeletonAppear(isSkeletonAppear);
		setSoulAppear(isSoulAppear);
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isDarkMageAppear() {
		return isDarkMageAppear;
	}

	public void setDarkMageAppear(boolean isDarkMageAppear) {
		this.isDarkMageAppear = isDarkMageAppear;
	}

	public boolean isHauntedMaidAppear() {
		return isHauntedMaidAppear;
	}

	public void setHauntedMaidAppear(boolean isHauntedMaidAppear) {
		this.isHauntedMaidAppear = isHauntedMaidAppear;
	}

	public boolean isPumpkinHeadAppear() {
		return isPumpkinHeadAppear;
	}

	public void setPumpkinHeadAppear(boolean isPumpkinHeadAppear) {
		this.isPumpkinHeadAppear = isPumpkinHeadAppear;
	}

	public boolean isReaperAppear() {
		return isReaperAppear;
	}

	public void setReaperAppear(boolean isReaperAppear) {
		this.isReaperAppear = isReaperAppear;
	}

	public boolean isSkeletonAppear() {
		return isSkeletonAppear;
	}

	public void setSkeletonAppear(boolean isSkeletonAppear) {
		this.isSkeletonAppear = isSkeletonAppear;
	}

	public boolean isSoulAppear() {
		return isSoulAppear;
	}

	public void setSoulAppear(boolean isSoulAppear) {
		this.isSoulAppear = isSoulAppear;
	}


}
