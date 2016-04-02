package Main;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
//import java.awt.event.KeyListener;


/**
 *
 */
public class Game extends JFrame implements KeyListener {

	private String playerNames[] = new String[2];
	private int towerWidth;
	private int towerHeight;

	//the possible brick building keys for the left and right towers, must be upper case
	private final char LEFT_REQUIRED_KEYS[] = {'A', 'S', 'D'};
	private final char RIGHT_REQUIRED_KEYS[] = {'J', 'K', 'L'};

	private final long RENDER_WAIT = (1000/60);
	private final long BOMB_LIFESPAN = 1000;
	private final int NAME_MAX_LENGTH = 10;

	private TowerController leftTowerController;
	private TowerController rightTowerController;

	Game(int towerWidth, int towerHeight) {
		super();
		this.towerWidth = towerWidth;
		this.towerHeight = towerHeight;

		//take player names
		Scanner scanner = new Scanner(System.in);
		String nameAttempt;
		playerNames[0] = playerNames[1] = "";

		//extract player one's name
		System.out.print("Player one name: ");
		while (playerNames[0].equals("")) {
			nameAttempt = scanner.nextLine().trim();

			if (nameAttempt.length() > NAME_MAX_LENGTH) {
				System.out.print("Please enter a name shorter than " + NAME_MAX_LENGTH + " characters: ");
			} else if (nameAttempt.length() <= 1) {
				System.out.print("Please enter a longer name: ");
			} else {
				playerNames[0] = nameAttempt;
			}
		}

		//get player two's name
		System.out.print("Player two name: ");
		while (playerNames[1].equals("")) {
			nameAttempt = scanner.nextLine().trim();

			if (nameAttempt.length() > NAME_MAX_LENGTH) {
				System.out.print("Please enter a name shorter than " + NAME_MAX_LENGTH + " characters: ");
			} else if (nameAttempt.length() <= 1) {
				System.out.print("Please enter a longer name: ");
			} else {
				playerNames[1] = nameAttempt;
			}
		}

	}

	/**
	 * Begin running a ready game.
	 */
	public void begin() {
		//Create left and right towers
		Tower leftTower = new Tower(playerNames[0], towerHeight, towerWidth);
		Tower rightTower = new Tower(playerNames[1], towerHeight, towerWidth);

		//Create left and right TowerControllers and begin running them, these are constructed with
		//their respective Towers
		leftTowerController = new TowerController(leftTower, LEFT_REQUIRED_KEYS);
		rightTowerController = new TowerController(rightTower, RIGHT_REQUIRED_KEYS);

		Thread leftTowerThread = new Thread(leftTowerController);
		Thread rightTowerThread = new Thread(rightTowerController);

		leftTowerThread.start();
		rightTowerThread.start();

		//Create a GameRenderer and give it the left and right towers
		//TODO: abstract the JFrame element of this class
		AsciiRenderer renderer = new AsciiRenderer(leftTower, rightTower);
		renderer.render();

		add(renderer);
		pack();
		addKeyListener(this);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true);

		//Run the game renderer periodically (individual of the underlying game logic). The towers are
		//rendered 60 times per second
		while (true) {
			try {
				Thread.sleep(RENDER_WAIT);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}

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

		//handle bomb plant attempts
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			if (e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT && leftTowerController.useBomb()) {
				//the bomb plant came from the left, start a new BombTracker thread with the right tower as
				//the victim
				new Thread(new BombTracker(rightTowerController, BOMB_LIFESPAN)).start();
			} else if (e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT && rightTowerController.useBomb()) {
				//the bomb plant came from the right, start a new BombTracker thread with the left tower as
				//the victim
				new Thread(new BombTracker(leftTowerController, BOMB_LIFESPAN)).start();
			}

			return;
		}

		char cUpper = Character.toUpperCase(e.getKeyChar());

		for (char requiredKey: LEFT_REQUIRED_KEYS) {
			if (cUpper == requiredKey) {
				if (leftTowerController.setNewKeyPress(cUpper)) {
					(new Thread(leftTowerController)).start();
				}

				return;
			}
		}

		for (char requiredKey: RIGHT_REQUIRED_KEYS) {
			if (cUpper == requiredKey) {
				if (rightTowerController.setNewKeyPress(cUpper)) {
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
