package Main;

import java.util.Random;

/**
 *
 */
public class TowerController implements Runnable {

	private final Tower tower;
	private final char REQUIRED_KEYS[];

	private boolean acceptingKeyPresses = true;
	private char latestKeyPress;
	Random random = new Random();

	TowerController(Tower tower, char requiredKeys[]) {
		this.tower = tower;
		this.REQUIRED_KEYS = requiredKeys;

		// set tower's current condo to next random letter
		tower.setRequiredKey(getRandomKey());
	}

	@Override
	public void run() {
		handleKeyPress();
	}

	// TODO: handle key presses while the tower is already doing something
	public void handleKeyPress() {
		if (tower.enemyBombSet()) {
			// if an active bomb is planted in this tower, explode the top level
			tower.removeEnemyBomb();
			explodeTopLevel();
		} else if (latestKeyPress == tower.getRequiredKey()){
			// if the key is the one required to build the condo
			if (tower.getNewCondoIndex() >= tower.getWIDTH() - 1) {
				// a level has been completed
				if (!tower.isOnLastLevel() && !towerFinished()) {
					// if the tower is not finished yet, we need to finish its level and go up to the next
					createNewLevel();
				} else {
					tower.setState(100);
				}
			} else {
				// just add a new condo
				tower.completeCondo(getRandomKey());
			}

		} else {
			// wrong key, break a condo
			breakCondo();
		}

		acceptingKeyPresses = true;
	}

	public Tower getTower() {
		return this.tower;
	}

	public boolean isRequiredKey(char key) {
		for (char c: REQUIRED_KEYS) {
			if (c == key) {
				return true;
			}
		}

		return false;
	}

	public synchronized boolean setNewKeyPress(char newKeyPress) {
		if (acceptingKeyPresses && !towerFinished()) {
			acceptingKeyPresses = false;
			this.latestKeyPress = newKeyPress;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Attempt to provide a bomb to be used on the other tower
	 * @return	true if the attempt was successful
	 */
	public boolean useBomb() {
		if (tower.getUsableBombCount() > 0) {
			tower.removeBomb();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Receive an enemy bomb (ie. have one planted in our tower so any key presses will explode the top level)
	 */
	public void receiveEnemyBomb() {
		tower.addEnemyBomb();
	}

	public void dismantleBomb() {
		if (tower.enemyBombSet()) {
			tower.dismantleEnemyBomb();
		}
	}

	public boolean towerFinished() {
		return tower.isFinished();
	}

	private char getRandomKey() {
		return REQUIRED_KEYS[random.nextInt(REQUIRED_KEYS.length)];
	}

	private void explodeTopLevel() {
		tower.setState(-1);

		try {
			Thread.sleep(900);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}

		tower.setState(-2);

		try {
			Thread.sleep(900);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}

		tower.explodeLevel(getRandomKey());
		tower.setState(0);
	}

	private void createNewLevel() {
		if (towerFinished()) {
			return;
		}

		tower.setState(1);

		try {
			Thread.sleep(900);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}

		tower.setState(2);

		try {
			Thread.sleep(900);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}

		tower.completeLevel(getRandomKey());
		tower.setState(0);
	}

	private void breakCondo() {
		tower.setRequiredKey('X');

		try {
			Thread.sleep(900);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}

		tower.breakCondo(getRandomKey());
	}

}
