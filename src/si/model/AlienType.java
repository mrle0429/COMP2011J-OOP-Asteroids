package si.model;

/**
 * Enum representing different types of alien ships in the game.
 */
public enum AlienType {
	Large(500,3,10,100, 90, 75,1,150),
	Small(1000,6,5,30, 45, 40,3,100);

	private int score;
	private int speed;
	private int size;
	private int hitSize;
	private int hitWidth;
	private int hitHeight;
	private int bulletSpeed;
	private int fireFrequency;  // The higher the number, the lower the frequency

	/**
	 * Constructor for each alien type with specified characteristics.
	 *
	 * @param score             The score awarded for destroying the alien.
	 * @param speed         The speed of the alien.
	 * @param size          The size of the alien.
	 * @param hitSize       The hit size of the alien.
	 * @param hitWidth      The hit width of the alien.
	 * @param hitHeight     The hit height of the alien.
	 * @param bulletSpeed   The speed of the bullets fired by the alien.
	 * @param fireFrequency The firing frequency of the alien. The higher the number, the lower the frequency.
	 */
	AlienType( int score,int speed,int size, int hitSize, int hitWidth, int hitHeight, int bulletSpeed, int fireFrequency) {
		this.size = size;
		this.score = score;
		this.speed = speed;
		this.hitSize = size;
		this.hitWidth = hitWidth;
		this.hitHeight = hitHeight;
		this.bulletSpeed = bulletSpeed;
		this.fireFrequency = fireFrequency;

	}


	public int getScore() {
		return score;
	}

	public int getSpeed() {
		return speed;
	}

	public int getSize() {
		return size;
	}

	public int getHitSize() {
		return hitSize;
	}

	public int getHitHeight() {
		return hitHeight;
	}

	public int getHitWidth() {
		return hitWidth;
	}

	public int getBulletSpeed() {
		return bulletSpeed;
	}

	public int getFireFrequency() {
		return fireFrequency;
	}
}
