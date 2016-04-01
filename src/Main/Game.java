package Main;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.awt.event.KeyListener;


/**
 *
 */
public class Game extends JFrame implements KeyListener {

	private String playerNames[];
	private int towerWidth;
	private int towerHeight;

	private final int TOWER_WALL_OFFSET = 2;
	private final int TOWER_GAP = 5;

	private final char LEFT_REQUIRED_KEYS[] = {'A', 'S', 'D'};
	private final char RIGHT_REQUIRED_KEYS[] = {'J', 'K', 'L'};

	private final long RENDER_WAIT = (1000/60);

	private TowerController leftTowerController;
	private TowerController rightTowerController;

	Game(String[] playerNames, int towerWidth, int towerHeight) {
		super();
		this.playerNames = playerNames;
		this.towerWidth = towerWidth;
		this.towerHeight = towerHeight;
	}

	/**
	 * Begin running a ready game.
	 */
	public void begin() {
		//TODO: check if the game is valid
		if (false) {
			//Check: width and height are valid, names are not too long, etc
		}

		//Create left and right towers
		Tower leftTower = new Tower(playerNames[0], towerHeight, towerWidth);
		Tower rightTower = new Tower(playerNames[1], towerHeight, towerWidth);

		//2 Create left and right TowerControllers and begin running them, these are constructed with
		//	their respective Towers
		leftTowerController = new TowerController(leftTower, LEFT_REQUIRED_KEYS);
		rightTowerController = new TowerController(rightTower, RIGHT_REQUIRED_KEYS);

		Thread leftTowerThread = new Thread(leftTowerController);
		Thread rightTowerThread = new Thread(rightTowerController);

		leftTowerThread.start();
		rightTowerThread.start();

		//3 Create a GameRenderer and give it the left and right towers
		AsciiRenderer renderer = new AsciiRenderer(leftTower, rightTower, TOWER_WALL_OFFSET, TOWER_GAP);
		renderer.render();

		add(renderer);
		pack();
		addKeyListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		//4 Run the game renderer periodically (individual of the underlying game logic). The towers are
		//	rendered 60 times per second
		while (true) {
			try {
				Thread.sleep(RENDER_WAIT);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}

			//TODO: something different when the game is finished (rather than just close)

			renderer.render();
			repaint();
		}
	}

	private boolean gameFinished() {
		return (leftTowerController.towerFinished() || rightTowerController.towerFinished());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (gameFinished()) {
			return;
		}

		char cLower = Character.toLowerCase(e.getKeyChar());
		char cUpper = Character.toUpperCase(e.getKeyChar());

		for (char requiredKey: LEFT_REQUIRED_KEYS) {
			if (cLower == requiredKey || cUpper == requiredKey) {
				if (leftTowerController.isAcceptingKeyPresses()) {
					leftTowerController.setLatestKeyPress(cUpper);
					(new Thread(leftTowerController)).start();
				}

				return;
			}
		}

		for (char requiredKey: RIGHT_REQUIRED_KEYS) {
			if (cLower == requiredKey || cUpper == requiredKey) {
				if (rightTowerController.isAcceptingKeyPresses()) {
					rightTowerController.setLatestKeyPress(cUpper);
					(new Thread(rightTowerController)).start();
				}

				return;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

}
