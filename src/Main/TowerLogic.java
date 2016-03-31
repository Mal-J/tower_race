package Main;

import com.sun.org.apache.regexp.internal.RE;

import java.util.Random;

/**
 *
 */
public class TowerLogic implements Runnable {

	private final Tower tower;
	private final char REQUIRED_KEYS[];
	Random random = new Random();

	TowerLogic(Tower tower, char requiredKeys[]) {
		this.tower = tower;
		this.REQUIRED_KEYS = requiredKeys;

		tower.setRequiredKey(getRandomKey());
		//set tower's current brick to next random letter
	}

	@Override
	public void run() {
		//THread.wait();
	}

	private char getRandomKey() {
		return REQUIRED_KEYS[random.nextInt(REQUIRED_KEYS.length - 1)];
	}
}
