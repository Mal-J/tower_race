package Main;

import javax.swing.JPanel;

import AsciiPanel.AsciiPanel;

/**
 *
 */
public class AsciiRenderer extends AsciiPanel implements GameRenderer {
	//Just implement the required methods from GameRenderer, nothing else

	//private AsciiPanel panel;
	private Tower leftTower;
	private Tower rightTower;

	public AsciiRenderer() {
		//super();
		//this.write("test", 1, 1);
		//add(panel);
		//pack();
	}

	//@Override
	public void render(String str) {
		this.write(str, 2, 3);
	}

	public void render() {

	}
}
