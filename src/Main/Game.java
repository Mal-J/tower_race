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
	//private boolean gameReady = false;

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
		//if (!gameReady) {
			//game not ready, Exception?
			//check player names is size 2?
		//}

		//Create left and right towers
		Tower leftTower = new Tower(playerNames[0], towerHeight, towerWidth);
		Tower rightTower = new Tower(playerNames[1], towerHeight, towerWidth);

		//2 Create left and right TowerLogics and begin running them, these are constructed with
		//	their respective Towers
		TowerLogic leftTowerLogic = new TowerLogic(leftTower);
		TowerLogic rightTowerLogic = new TowerLogic(rightTower);


		//3 Create a GameRenderer and give it the left and right towers
		AsciiRenderer renderer = new AsciiRenderer();
		String str = "Welcome To";
		renderer.render(str);

		add(renderer);
		pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			System.err.println("...");	//TODO: fix
		}
		str = "Commit #1";

		renderer.render(str);
		repaint();
		//add(renderer);
		//pack();


		//4 Enable key events, check for game finished then send them through to the correct TowerLogic
		// 	when they happen

		//5 Run the game renderer periodically (individual of the underlying game logic). The towers are
		//	rendered 60 times per second



	}

	private boolean gameFinished(Tower leftTower, Tower rightTower) {
		return false;
	}


}
