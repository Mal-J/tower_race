package Main;

import javax.swing.JFrame;
import AsciiPanel.AsciiPanel;
import oracle.jvm.hotspot.jfr.JFR;

/**
 *
 */
public class Game extends JFrame {

	private String playerNames[];
	private int towerWidth;
	private int towerHeight;

	private final int TOWER_WALL_OFFSET = 2;
	private final int TOWER_GAP = 5;

	private final char LEFT_REQUIRED_KEYS[] = {'A', 'S', 'D'};
	private final char RIGHT_REQUIRED_KEYS[] = {'J', 'K', 'L'};

	Game(String[] playerNames, int towerWidth, int towerHeight) {
		super();
		this.playerNames = playerNames;
		this.towerWidth = towerWidth;
		this.towerHeight = towerHeight;

		//create tower arrays, chars all spaces
		//
		//set gameReady to true;
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

		//2 Create left and right TowerLogics and begin running them, these are constructed with
		//	their respective Towers
		TowerLogic leftTowerLogic = new TowerLogic(leftTower);
		TowerLogic rightTowerLogic = new TowerLogic(rightTower);


		//3 Create a GameRenderer and give it the left and right towers
		AsciiRenderer renderer = new AsciiRenderer(leftTower, rightTower, TOWER_WALL_OFFSET, TOWER_GAP);
		renderer.render();

		add(renderer);
		pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		//loop to test tower rendering
		for (int i = 0; i < 13; i++) {
			try {
				Thread.sleep(1200);
			} catch (InterruptedException e) {
				System.err.println("...");	//TODO: fix
			}

			if (i == 8) {
				leftTower.completeLevel('L');
			} else if (i == 9){
				rightTower.completeLevel('R');
			} else if (i % 2 == 0) {
				leftTower.completeBrick('L');
			} else if (i % 2 == 1) {
				rightTower.completeBrick('R');
			}

			renderer.render();
			repaint();
		}

		//4 Enable key events, check for game finished then send them through to the correct TowerLogic
		// 	when they happen

		//5 Run the game renderer periodically (individual of the underlying game logic). The towers are
		//	rendered 60 times per second



	}

	private boolean gameFinished(Tower leftTower, Tower rightTower) {
		return (leftTower.getCurrentLevel() < 0 || rightTower.getCurrentLevel() < 0);
	}


}
