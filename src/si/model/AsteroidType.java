package si.model;

/**
 * Enum representing different types of asteroids in the game.
 */
public enum AsteroidType {
    SMALL(2, 5, 200, 60, 55, -15, -25), MEDIUM(3, 3, 100, 80, 75, -30, -35), LARGE(6, 2, 50, 170, 165, -60, -80);

    private final int size;
    private int speed;
    private int score;
    private int hitSize;
    private int hitWidth;
    private int hitHeight;
    private int hitOfX;
    private int hitOfY;

    /**
     * Constructor for each asteroid type with specified characteristics.
     *
     * @param size      The size of the asteroid.
     * @param speed     The speed of the asteroid.
     * @param score     The score awarded for destroying the asteroid.
     * @param hitWidth  The hit width of the asteroid.
     * @param hitHeight The hit height of the asteroid.
     * @param hitOfX    The correction factor for the hitbox X-coordinate.
     * @param hitOfY    The correction factor for the hitbox Y-coordinate.
     */
    AsteroidType(int size, int speed, int score, int hitWidth, int hitHeight, int hitOfX, int hitOfY) {
        this.size = size;
        this.speed = speed;
        this.score = score;
        this.hitSize = hitSize;
        this.hitWidth = hitWidth;
        this.hitHeight = hitHeight;
        this.hitOfX = hitOfX;
        this.hitOfY = hitOfY;

    }

    public int getSize() {
        return size;
    }

    public int getSpeed() {
        return speed;
    }

    public int getScore() {
        return score;
    }

    public int getHitSize() {
        return hitSize;
    }

    public int getHitWidth() {
        return hitWidth;
    }

    public int getHitHeight() {
        return hitHeight;
    }

    public int getHitOfX() {
        return hitOfX;
    }

    public int getHitOfY() {
        return hitOfY;
    }


}
