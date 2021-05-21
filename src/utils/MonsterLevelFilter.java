package utils;

import entity.DarkMage;
import entity.HauntedMaid;
import entity.Player;
import entity.PumpkinHead;
import entity.Skeleton;
import entity.base.Monster;

/**
 * <p>
 * The MonsterLevelFilter is the class for filtering {@link Monster} which
 * generate in that level.
 * </p>
 * 
 * <p>
 * The filter is active when the present level is at least the level which
 * specify in {@link #level} and it will ignore the previous active filter
 * </p>
 * 
 * <p>
 * For example, the "A" filter instance have level 4 and the "B" filter instance
 * have level 5 if {@link Player} is in level 6 then Game will pick "B" as the
 * filter
 * </p>
 */
public class MonsterLevelFilter {

	/**
	 * Represent the minimum level that the filter is active
	 */
	private int level;

	/**
	 * Represent at least {@link #level} will generate the {@link DarkMage} or not
	 */
	private boolean isDarkMageAppear;

	/**
	 * Represent at least {@link #level} will generate the {@link HauntedMaid} or
	 * not
	 */
	private boolean isHauntedMaidAppear;

	/**
	 * Represent at least {@link #level} will generate the {@link PumpkinHead} or
	 * not
	 */
	private boolean isPumpkinHeadAppear;

	/**
	 * Represent at least {@link #level} will generate the {@link Reaper} or not
	 */
	private boolean isReaperAppear;

	/**
	 * Represent at least {@link #level} will generate the {@link Skeleton} or not
	 */
	private boolean isSkeletonAppear;

	/**
	 * Represent at least {@link #level} will generate the {@link Soul} or not
	 */
	private boolean isSoulAppear;

	/**
	 * The constructor of class
	 * 
	 * @param level               initial value of {@link #level}
	 * @param isDarkMageAppear    initial value of {@link #isDarkMageAppear}
	 * @param isHauntedMaidAppear initial value of {@link #isHauntedMaidAppear}
	 * @param isPumpkinHeadAppear initial value of {@link #isPumpkinHeadAppear}
	 * @param isReaperAppear      initial value of {@link #isReaperAppear}
	 * @param isSkeletonAppear    initial value of {@link #isSkeletonAppear}
	 * @param isSoulAppear        initial value of {@link #isSoulAppear}
	 */
	public MonsterLevelFilter(int level, boolean isDarkMageAppear, boolean isHauntedMaidAppear,
			boolean isPumpkinHeadAppear, boolean isReaperAppear, boolean isSkeletonAppear, boolean isSoulAppear) {
		setLevel(level);
		setDarkMageAppear(isDarkMageAppear);
		setHauntedMaidAppear(isHauntedMaidAppear);
		setPumpkinHeadAppear(isPumpkinHeadAppear);
		setReaperAppear(isReaperAppear);
		setSkeletonAppear(isSkeletonAppear);
		setSoulAppear(isSoulAppear);
	}

	/**
	 * Getter for {@link #level}
	 * 
	 * @return {@link #level}
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Setter for {@link #level}
	 * 
	 * @param level the new {@link #level}
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * Getter for {@link #isDarkMageAppear}
	 * 
	 * @return {@link #isDarkMageAppear}
	 */
	public boolean isDarkMageAppear() {
		return isDarkMageAppear;
	}

	/**
	 * Setter for {@link #isDarkMageAppear}
	 * 
	 * @param isDarkMageAppear the new {@link #isDarkMageAppear}
	 */
	public void setDarkMageAppear(boolean isDarkMageAppear) {
		this.isDarkMageAppear = isDarkMageAppear;
	}

	/**
	 * Getter for {@link #isHauntedMaidAppear}
	 * 
	 * @return {@link #isHauntedMaidAppear}
	 */
	public boolean isHauntedMaidAppear() {
		return isHauntedMaidAppear;
	}

	/**
	 * Setter for {@link #isHauntedMaidAppear}
	 * 
	 * @param isHauntedMaidAppear the new {@link #isHauntedMaidAppear}
	 */
	public void setHauntedMaidAppear(boolean isHauntedMaidAppear) {
		this.isHauntedMaidAppear = isHauntedMaidAppear;
	}

	/**
	 * Getter for {@link #isPumpkinHeadAppear}
	 * 
	 * @return {@link #isPumpkinHeadAppear}
	 */
	public boolean isPumpkinHeadAppear() {
		return isPumpkinHeadAppear;
	}

	/**
	 * Setter for {@link #isPumpkinHeadAppear}
	 * 
	 * @param isPumpkinHeadAppear the new {@link #isPumpkinHeadAppear}
	 */
	public void setPumpkinHeadAppear(boolean isPumpkinHeadAppear) {
		this.isPumpkinHeadAppear = isPumpkinHeadAppear;
	}

	/**
	 * Getter for {@link #isReaperAppear}
	 * 
	 * @return {@link #isReaperAppear}
	 */
	public boolean isReaperAppear() {
		return isReaperAppear;
	}

	/**
	 * Setter for {@link #isReaperAppear}
	 * 
	 * @param isReaperAppear the new {@link #isReaperAppear}
	 */
	public void setReaperAppear(boolean isReaperAppear) {
		this.isReaperAppear = isReaperAppear;
	}

	/**
	 * Getter for {@link #isSkeletonAppear}
	 * 
	 * @return {@link #isSkeletonAppear}
	 */
	public boolean isSkeletonAppear() {
		return isSkeletonAppear;
	}

	/**
	 * Setter for {@link #isSkeletonAppear}
	 * 
	 * @param isSkeletonAppear the new {@link #isSkeletonAppear}
	 */
	public void setSkeletonAppear(boolean isSkeletonAppear) {
		this.isSkeletonAppear = isSkeletonAppear;
	}

	/**
	 * Getter for {@link #isSoulAppear}
	 * 
	 * @return {@link #isSoulAppear}
	 */
	public boolean isSoulAppear() {
		return isSoulAppear;
	}

	/**
	 * Setter for {@link #isSoulAppear}
	 * 
	 * @param isSoulAppear the new {@link #isSoulAppear}
	 */
	public void setSoulAppear(boolean isSoulAppear) {
		this.isSoulAppear = isSoulAppear;
	}

}
