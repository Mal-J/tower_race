package Main;

import com.sun.org.apache.regexp.internal.RE;

import java.util.Random;

/**
 *
 */
public class TowerController implements Runnable {

	private final Tower tower;
	private final char REQUIRED_KEYS[];
	//how long a planted bomb lasts in milliseconds
	private final int BOMB_LIFESPAN = 1000;

	private long lastEnemyBombTime;
	private boolean acceptingKeyPresses = true;
	Random random = new Random();

	TowerController(Tower tower, char requiredKeys[]) {
		this.tower = tower;
		this.REQUIRED_KEYS = requiredKeys;
		this.lastEnemyBombTime = 0;

		tower.setRequiredKey(getRandomKey());
		//set tower's current brick to next random letter
	}

	@Override
	public void run() {
		try {
			wait();
		} catch (InterruptedException e) {
			System.err.println(tower.getName() + "'s TowerController interruped somehow");
			e.printStackTrace();
			System.exit(1);
		}
	}

	//TODO: handle key presses while the tower is already doing something
	public void handleKeyPress(char key) {
		//if the tower is currently doing something,
		if (tower.getCurrentLevelState() != 0 || !acceptingKeyPresses) {
			return;
		}

		acceptingKeyPresses = false;

		if ((System.currentTimeMillis() - lastEnemyBombTime) < BOMB_LIFESPAN) {
			//if an active bomb is planted in this tower, explode the top level
			explodeTopLevel();

		} else if (key == tower.getRequiredKey()){
			if (tower.getNewBrickIndex() >= tower.getWidth() - 1) {
				//a new level needs to be created
				createNewLevel();
			} else {
				//just add a new brick
				tower.completeBrick(getRandomKey());
			}

		} else {
			//wrong key, break a brick?
			breakBrick();
		}

		acceptingKeyPresses = true;
	}

	public boolean useBomb() {
		if (tower.getBombCount() > 0) {
			tower.removeBomb();
			return true;
		} else {
			return false;
		}
	}

	public void plantEnemyBomb() {
		lastEnemyBombTime = System.currentTimeMillis();
	}

	public boolean towerFinished() {
		return tower.isFinished();
	}


	private char getRandomKey() {
		return REQUIRED_KEYS[random.nextInt(REQUIRED_KEYS.length)];
	}

	private void explodeTopLevel() {
		tower.setCurrentLevelState(-1);

		try {
			Thread.sleep(1200);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}

		tower.setCurrentLevelState(-2);

		try {
			Thread.sleep(1200);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}

		tower.explodeLevel(getRandomKey());
		tower.setCurrentLevelState(0);
	}

	private void createNewLevel() {
		tower.setCurrentLevelState(1);

		try {
			Thread.sleep(1200);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}

		tower.setCurrentLevelState(2);

		try {
			Thread.sleep(1200);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}

		tower.completeLevel(getRandomKey());
		tower.setCurrentLevelState(0);
	}

	private void breakBrick() {
		tower.setRequiredKey('X');

		try {
			Thread.sleep(1200);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}

		tower.breakBrick(getRandomKey());
	}

}
