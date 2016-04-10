package Main;

/**
 * Keeps track of a bomb that has been planted in a TowerController object. After the required time has
 * passed, this class will tell the TowerController to dismantle its bomb.
 */
public class BombTracker implements Runnable {

	private final long BOMB_LIFESPAN = 1000;		// how long a bomb lasts in ms

	private TowerController victimTowerController;

	BombTracker (TowerController victimTowerController) {
		this.victimTowerController = victimTowerController;
	}

	@Override
	public void run() {

		victimTowerController.receiveEnemyBomb();

		try {
			Thread.sleep(BOMB_LIFESPAN);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}

		victimTowerController.dismantleBomb();
	}

}
