package Main;

import java.util.Arrays;


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
	private char bricks[][];

	private int currentLevel;
	private int currentBrick;
	//0 = normal render, 1, 2 = building (first, second stage); 3, 4 = exploding (first, second stage)
	private int topLevelState;

	Tower(String name, int height, int width) {
		this.name = name;
		this.height = height;
		this.width = width;

		this.bombCount = 0;
		this.topLevelState = 0;
		this.currentLevel = 0;
		this.currentBrick = 0;


		//initialise bricks size and all chars to ' '
		bricks = new char[height][width];

		for (int i = 0; i < height; i++) {
			Arrays.fill(bricks[i], ' ');
		}
	}

	char getCurrentNewBrick () {
		return bricks[currentLevel][currentBrick];
	}

	boolean isFinished() {
		if (currentLevel >= height) {
			return true;
		} else {
			return false;
		}
	}

	


}
