package Main;


/**
 *
 */
public class Tower {

	//Tower specs
	private final String NAME;
	private final int HEIGHT;
	private final int WIDTH;

	private int enemyBombCount;
	private int usableBombCount;
	//the key required to build the current brick
	private char requiredKey;

	//level 0 = top level
	private int currentLevel;
	private int newBrickIndex;

	//0 = normal render; 1, 2 = building (first, second stage); -1, -2 = exploding (first, second stage)
	//100 = finished
	private int state;

	Tower(String name, int height, int width) {
		this.NAME = name;
		this.HEIGHT = height;
		this.WIDTH = width;

		this.usableBombCount = 0;
		this.state = 0;
		this.currentLevel = height - 1;
		this.newBrickIndex= 0;
	}

	public String getNAME() {
		return this.NAME;
	}

	public int getUsableBombCount() {
		return usableBombCount;
	}

	public void addUsableBomb() {
		this.usableBombCount++;
	}

	public boolean enemyBombSet() {
		return this.enemyBombCount > 0;
	}

	public void addEnemyBomb() {
		this.enemyBombCount++;
	}

	public void removeEnemyBomb() {
		this.enemyBombCount--;
	}

	public void dismantleEnemyBomb() {
		this.enemyBombCount--;
		this.usableBombCount++;
	}

	public void removeBomb() {
		this.usableBombCount--;
	}

	public int getHeight() {
		return this.HEIGHT;
	}

	public int getWIDTH() {
		return this.WIDTH;
	}

	public int getCurrentLevel() {
		return this.currentLevel;
	}

	public int getNewBrickIndex() {
		return this.newBrickIndex;
	}

	public void resetNewBrickIndex() {
		this.newBrickIndex = 0;
	}

	public char getRequiredKey() {
		return requiredKey;
	}

	public void setRequiredKey(char newKey) {
		this.requiredKey = newKey;
	}

	public int getState() {
		return this.state;
	}

	public void setState(int newState) {
		this.state = newState;
	}

	public boolean isOnLastLevel() {
		return (this.currentLevel <= 0);
	}

	public void explodeLevel(char newRequiredKey) {
		resetNewBrickIndex();
		setRequiredKey(newRequiredKey);
	}

	public void completeLevel(char newRequiredKey) {
		setRequiredKey(newRequiredKey);
		addUsableBomb();

		this.currentLevel--;
		this.newBrickIndex = 0;
	}

	public void breakBrick(char newRequiredKey) {
		if (newBrickIndex > 0) {
			this.newBrickIndex--;
		}

		this.setRequiredKey(newRequiredKey);
	}

	public void completeBrick(char newRequiredKey) {
		setRequiredKey(newRequiredKey);

		this.newBrickIndex++;
	}

	public boolean isFinished() {
		return (state == 100);
	}

}
