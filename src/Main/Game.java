package Main;

import java.util.Scanner;

/**
 *
 */
public class Game {

	private String playerNames[] = new String[2];
	private int towerWidth;
	private int towerHeight;

	// the possible condo building keys for the left and right towers, must be upper case
	private final char LEFT_REQUIRED_KEYS[] = {'A', 'S', 'D'};
	private final char RIGHT_REQUIRED_KEYS[] = {'J', 'K', 'L'};

	private final int NAME_MAX_LENGTH = 10;

	private TowerController leftTowerController;
	private TowerController rightTowerController;

	Game(int towerWidth, int towerHeight) {
		super();
		this.towerWidth = towerWidth;
		this.towerHeight = towerHeight;

		Tower leftTower = new Tower(retrievePlayerName(1), towerHeight, towerWidth);
		Tower rightTower = new Tower(retrievePlayerName(2), towerHeight, towerWidth);

		// Create left and right TowerControllers and begin running them, these are constructed with
		// their respective Towers
		leftTowerController = new TowerController(leftTower, LEFT_REQUIRED_KEYS);
		rightTowerController = new TowerController(rightTower, RIGHT_REQUIRED_KEYS);

	}

	/**
	 * Begin running a ready game.
	 */
	public void begin() {
		new GameWindow(leftTowerController, rightTowerController).beginRendering();
	}

	/**
	 * Retrieve a player's name from the command line.
	 * @param playerNumber	the player number whose name is being asked for (1 or 2)
	 * @return				the player's name
	 */
	private String retrievePlayerName(int playerNumber) {
		Scanner scanner = new Scanner(System.in);
		String nameAttempt;

		if (playerNumber == 1) {
			System.out.print("Player one name: ");
		} else if (playerNumber == 2) {
			System.out.print("Player two name: ");
		} else {
			return null;
		}

		while(true) {
			nameAttempt = scanner.nextLine().trim();

			if (nameAttempt.length() > NAME_MAX_LENGTH) {
				System.out.print("Please enter a name shorter than " + NAME_MAX_LENGTH + " characters: ");
			} else if (nameAttempt.length() <= 1) {
				System.out.print("Please enter a longer name: ");
			} else {
				return nameAttempt;
			}
		}
	}

}