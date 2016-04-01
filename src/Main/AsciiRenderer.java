package Main;

import AsciiPanel.AsciiPanel;

/**
 *
 */
public class AsciiRenderer extends AsciiPanel implements GameRenderer {
	//Just implement the required methods from GameRenderer, nothing else

	//private AsciiPanel panel;
	private final Tower leftTower;
	private final Tower rightTower;

	private final int TOWER_HEIGHT;
	private final int TOWER_WIDTH;

	private final int TOWER_WALL_OFFSET;
	private final int TOWER_GAP;
	private final String BRICK = "[_]";
	private final String AIR_ABOVE_BRICK = " _ ";

	private String allBricks;
	private String allAir;

	public AsciiRenderer(Tower leftTower, Tower rightTower, int towerWallOffset, int towerGap) {
		super(towerWallOffset * 2 + (leftTower.getWidth() * 3) * 2 + towerGap,
				3 + leftTower.getHeight());

		this.leftTower = leftTower;
		this.rightTower = rightTower;
		this.TOWER_HEIGHT = leftTower.getHeight();
		this.TOWER_WIDTH = leftTower.getWidth();
		this.TOWER_WALL_OFFSET = towerWallOffset;
		this.TOWER_GAP = towerGap;

		this.allBricks = "";
		this.allAir = "";
		for (int j = 0; j < TOWER_WIDTH; j++) {
			allBricks += BRICK;
			allAir += "   ";
		}

		//super();
		//this.write("test", 1, 1);
		//add(panel);
		//pack();
	}

	@Override
	public void render() {
		String leftStats = leftTower.getName() + ": " + leftTower.getBombCount();
		String rightStats = rightTower.getName() + ": " + rightTower.getBombCount();

		//TODO: find a way to set the spaces in the middle to the right length
		this.write(leftStats + "    " + rightStats, 1, 1);

		//write the current tower state for both towers
		writeTowerState(leftTower, false);
		writeTowerState(rightTower, true);
	}

	private void writeTowerState(Tower tower, boolean isRightTower) {
		String aboveLevel = "";
		String currentLevel = "";

		int currentLevelX;
		if (!isRightTower) {
			currentLevelX = TOWER_WALL_OFFSET;
		} else {
			currentLevelX = TOWER_WALL_OFFSET + TOWER_WIDTH * 3 + TOWER_GAP;
		}
		int currentLevelY = 3 + tower.getCurrentLevel();

		switch (tower.getCurrentLevelState()) {
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
					currentLevel += AIR_ABOVE_BRICK;
				}

				break;
			case -1:
				//TODO make these cases more exciting
				//building stage one
				aboveLevel =
				currentLevel = "exploding...";
				break;
			case -2:
				//building stage two
				currentLevel = "exploding.....";
				break;
			case 1:
				//exploding stage one
				currentLevel = "building...";
				break;
			case 2:
				//exploding stage two
				currentLevel = "building.....";
				break;
		}

		//write above current level, current level, and below
		write(aboveLevel, currentLevelX, currentLevelY - 1);
		write(currentLevel, currentLevelX, currentLevelY);
		if (tower.getCurrentLevel() < tower.getHeight() - 1) {
			write(allBricks, currentLevelX, currentLevelY + 1);
		}
	}

}
