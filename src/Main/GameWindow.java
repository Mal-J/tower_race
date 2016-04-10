package Main;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 */
public class GameWindow extends JFrame implements KeyListener {

	GameAsciiPanel panel;

	private TowerController leftTowerController;
	private TowerController rightTowerController;

	private final long RENDER_WAIT = (1000/60);		//how long to wait between renders of the panel

	GameWindow(TowerController leftTowerController, TowerController rightTowerController) {
		this.leftTowerController = leftTowerController;
		this.rightTowerController = rightTowerController;

		this.panel = new GameAsciiPanel(leftTowerController.getTower(), rightTowerController.getTower());

		add(panel);
		pack();
		addKeyListener(this);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	void beginRendering() {
		while (true) {
			try {
				Thread.sleep(RENDER_WAIT);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}

			panel.render();
			repaint();
		}
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
				new Thread(new BombTracker(rightTowerController)).start();
			} else if (e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT && rightTowerController.useBomb()) {
				//the bomb plant came from the right, start a new BombTracker thread with the left tower as
				//the victim
				new Thread(new BombTracker(leftTowerController)).start();
			}

			return;
		}

		char cUpper = Character.toUpperCase(e.getKeyChar());

		if (leftTowerController.isRequiredKey(cUpper)) {
			if (leftTowerController.setNewKeyPress(cUpper)) {
				(new Thread(leftTowerController)).start();
			}
		} else if (rightTowerController.isRequiredKey(cUpper)) {
			if (rightTowerController.setNewKeyPress(cUpper)) {
				(new Thread(rightTowerController)).start();
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

	private boolean gameFinished() {
		return (leftTowerController.towerFinished() || rightTowerController.towerFinished());
	}
}
