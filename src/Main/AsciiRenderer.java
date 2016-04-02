package Main;

import AsciiPanel.AsciiPanel;

/**
 *
 */
public class AsciiRenderer extends AsciiPanel {

	private final Tower leftTower;
	private final Tower rightTower;

	private final int TOWER_HEIGHT;
	private final int TOWER_WIDTH;

	private final int TOWER_WALL_OFFSET = 2;
	private final int TOWER_GAP = 5;
	private final int TOWER_TOP_OFFSET = 4;
	private final int SCREEN_WIDTH;
	private final String BRICK = "[_]";
	private final String AIR_ABOVE_BRICK = " _ ";

	private String allBricks;
	private String allAir;
	private String allAirAboveBricks;

	public AsciiRenderer(Tower leftTower, Tower rightTower) {
		//had to put nums in the place of constants here, can't think of a better solution
		//first 2 = TOWER_WALL_OFFSET, first 5 = TOWER_GAP, 4 = TOWER_TOP_OFFSET
		super(2 * 2 + (leftTower.getWIDTH() * 3) * 2 + 5,
				4 + leftTower.getHeight());

		this.leftTower = leftTower;
		this.rightTower = rightTower;
		this.TOWER_HEIGHT = leftTower.getHeight();
		this.TOWER_WIDTH = leftTower.getWIDTH();
		this.SCREEN_WIDTH = TOWER_WALL_OFFSET * 2 + (leftTower.getWIDTH() * BRICK.length() * 2) + TOWER_GAP;

		this.allBricks = "";
		this.allAir = "";
		this.allAirAboveBricks = "";
		for (int j = 0; j < TOWER_WIDTH; j++) {
			allBricks += BRICK;
			allAir += "   ";
			allAirAboveBricks += AIR_ABOVE_BRICK;
		}

		//write the player names to the top of the screen
		this.write(leftTower.getNAME(), 1, 0);
		this.write(rightTower.getNAME(), SCREEN_WIDTH - rightTower.getNAME().length() - 1, 0);
	}

	public void render() {
		String leftBombCount = Integer.toString(leftTower.getUsableBombCount());
		String rightBombCount = Integer.toString(rightTower.getUsableBombCount());

		this.write(leftBombCount, 1, 1);
		this.write(rightBombCount, SCREEN_WIDTH - rightBombCount.length() - 1, 1);

		//write the current tower state for both towers
		writeTowerState(leftTower, false);
		writeTowerState(rightTower, true);
	}

	private void writeTowerState(Tower tower, boolean isRightTower) {
		String aboveLevel = "";
		String currentLevel = "";

		//the offset from the left hand side of the screen depends whether the tower is the left or right
		int currentLevelX;
		if (!isRightTower) {
			currentLevelX = TOWER_WALL_OFFSET;
		} else {
			currentLevelX = TOWER_WALL_OFFSET + TOWER_WIDTH * 3 + TOWER_GAP;
		}
		int currentLevelY = TOWER_TOP_OFFSET + tower.getCurrentLevel();

		switch (tower.getState()) {
			case 0:
				//render normally
				int newBrickIndex = tower.getNewBrickIndex();

				//write normal bricks
				for (int j = 0; j < newBrickIndex; j++) {
					aboveLevel += AIR_ABOVE_BRICK;
					currentLevel += BRICK;
				}

				aboveLevel += AIR_ABOVE_BRICK;
				currentLevel += "[" + tower.getRequiredKey() + "]";

				for (int j = newBrickIndex + 1; j < TOWER_WIDTH; j++) {
					aboveLevel += "   ";
					currentLevel += AIR_ABOVE_BRICK;
				}

				break;
			case -1:
				//TODO make these cases more exciting
				//building stage one
				aboveLevel = allAir;
				currentLevel = "exploding...";
				break;
			case -2:
				//building stage two
				aboveLevel = allAir;
				currentLevel = "exploding.....";
				break;
			case 1:
				//exploding stage one
				aboveLevel = allAir;
				currentLevel = "building...";
				break;
			case 2:
				//exploding stage two
				aboveLevel = allAir;
				currentLevel = "building.....";
				break;
			case 100:
				//the tower is finished
				//TODO: fireworks
				currentLevel = allBricks;
				aboveLevel = allAirAboveBricks;
				break;
		}

		//fill the remainder of the current level with air so that old chars are overwritten
		for (int i = currentLevel.length(); i < TOWER_WIDTH * 3; i++) {
			currentLevel += " ";
		}

		//write above current level, current level, and below
		write(aboveLevel, currentLevelX, currentLevelY - 1);
		write(currentLevel, currentLevelX, currentLevelY);
		if (tower.getCurrentLevel() < TOWER_HEIGHT - 1) {
			write(allBricks, currentLevelX, currentLevelY + 1);
		}
	}
}
