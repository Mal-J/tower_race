package Main;

import AsciiPanel.AsciiPanel;

/**
 *
 */
public class GameAsciiPanel extends AsciiPanel {

	private final Tower leftTower;
	private final Tower rightTower;

	private final int TOWER_HEIGHT;
	private final int TOWER_WIDTH;

	private final int TOWER_WALL_OFFSET = 2;
	private final int TOWER_GAP = 5;
	private final int TOWER_TOP_OFFSET = 4;
	private final int SCREEN_WIDTH;
	private final String CONDO = "[_]";
	private final String AIR_ABOVE_CONDO = " _ ";

	private String allCondos;
	private String allAir;
	private String allAirAboveCondos;

	public GameAsciiPanel(Tower leftTower, Tower rightTower) {
		//had to put nums in the place of constants here, can't think of a better solution
		//first 2 = TOWER_WALL_OFFSET, first 5 = TOWER_GAP, 4 = TOWER_TOP_OFFSET
		super(2 * 2 + (leftTower.getWIDTH() * 3) * 2 + 5,		//panel width
				4 + leftTower.getHeight());						//panel height

		this.leftTower = leftTower;
		this.rightTower = rightTower;
		this.TOWER_HEIGHT = leftTower.getHeight();
		this.TOWER_WIDTH = leftTower.getWIDTH();
		this.SCREEN_WIDTH = TOWER_WALL_OFFSET * 2 + (leftTower.getWIDTH() * CONDO.length() * 2) + TOWER_GAP;

		this.allCondos = "";
		this.allAir = "";
		this.allAirAboveCondos = "";
		for (int j = 0; j < TOWER_WIDTH; j++) {
			allCondos += CONDO;
			allAir += "   ";
			allAirAboveCondos += AIR_ABOVE_CONDO;
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
				int newCondoIndex = tower.getNewCondoIndex();

				//write normal condos
				for (int j = 0; j < newCondoIndex; j++) {
					aboveLevel += AIR_ABOVE_CONDO;
					currentLevel += CONDO;
				}

				aboveLevel += AIR_ABOVE_CONDO;
				currentLevel += "[" + tower.getRequiredKey() + "]";

				for (int j = newCondoIndex + 1; j < TOWER_WIDTH; j++) {
					aboveLevel += "   ";
					currentLevel += AIR_ABOVE_CONDO;
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
				currentLevel = allCondos;
				aboveLevel = allAirAboveCondos;
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
			write(allCondos, currentLevelX, currentLevelY + 1);
		}
	}
}
