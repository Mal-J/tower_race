package Main;

import java.util.Arrays;
import java.util.Random;


/**
 *
 */
public class Tower {

	//Tower specs
	private final String name;
	private final int height;
	private final int width;

	private int bombCount;
	//' ' for air, '*' = brick, other = the letter required to
	private char requiredKey;

	//level 0 = top level (-1 = tower finished)
	private int currentLevel;
	private int newBrickIndex;

	//0 = normal render; 1, 2 = building (first, second stage); 3, 4 = exploding (first, second stage)
	private int currentLevelState;

	Random random = new Random();

	Tower(String name, int height, int width) {
		this.name = name;
		this.height = height;
		this.width = width;

		this.bombCount = 0;
		this.currentLevelState = 0;
		this.currentLevel = height - 1;
		this.newBrickIndex= 0;

		//TODO required key?
	}

	public String getName() {
		return this.name;
	}

	public int getBombCount() {
		return bombCount;
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}

	public int getCurrentLevel() {
		return this.currentLevel;
	}

	public int getNewBrickIndex() {
		return this.newBrickIndex;
	}

	public char getRequiredKey() {
		return requiredKey;
	}

	public void setRequiredKey(char newKey) {
		this.requiredKey = newKey;
	}

	public int getCurrentLevelState() {
		return this.currentLevelState;
	}

	public void setCurrentLevelState(int newState) {
		this.currentLevelState = newState;
	}

	public void successfullyBuild() {
		//if we need to finish building this level, do
	}

	public void completeLevel(char newRequiredKey) {
		this.requiredKey = requiredKey;
		this.bombCount++;
		this.currentLevel--;
		this.newBrickIndex = 0;
	}

	public void completeBrick(char newRequiredKey) {
		this.requiredKey = newRequiredKey;
		this.newBrickIndex++;
	}

	public boolean isFinished() {
		if (currentLevel < 0) {
			return true;
		} else {
			return false;
		}
	}

}
