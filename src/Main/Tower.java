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
	private char requiredKey;			//the character/key required to build the current condo

	//level 0 = top level
	private int currentLevel;
	private int newCondoIndex;

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
		this.newCondoIndex= 0;
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

	public int getNewCondoIndex() {
		return this.newCondoIndex;
	}

	public void resetNewCondoIndex() {
		this.newCondoIndex = 0;
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
		resetNewCondoIndex();
		setRequiredKey(newRequiredKey);
	}

	public void completeLevel(char newRequiredKey) {
		setRequiredKey(newRequiredKey);
		addUsableBomb();

		this.currentLevel--;
		this.newCondoIndex = 0;
	}

	public void breakCondo(char newRequiredKey) {
		if (newCondoIndex > 0) {
			this.newCondoIndex--;
		}

		this.setRequiredKey(newRequiredKey);
	}

	public void completeCondo(char newRequiredKey) {
		setRequiredKey(newRequiredKey);

		this.newCondoIndex++;
	}

	public boolean isFinished() {
		return (state == 100);
	}

}
