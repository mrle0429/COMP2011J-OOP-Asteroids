package si.model;

import java.awt.*;
import java.util.Random;

/**
 * Represents an enemy ship in the Asteroids game.
 * Implements the Hittable interface.
 */
public class EnemyShip implements Hittable {
    private String enemyName;
    private boolean alive;
    private int x, y;
    private int angle;
    private double shipSpeed;
    private double bulletSpeed;
    private AlienType type;
    public static int SHIP_SCALE;
    private Rectangle hitBox;
    private Player player;

    /**
     * Constructs an EnemyShip with the specified coordinates and type.
     *
     * @param x    The x-coordinate of the enemy ship.
     * @param y    The y-coordinate of the enemy ship.
     * @param type The AlienType of the enemy ship.
     */
    public EnemyShip(int x, int y, AlienType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.alive = true;


        Random random = new Random();
        angle = random.nextInt(360);
        SHIP_SCALE = type.getSize();
        shipSpeed = type.getSpeed();
        enemyName = "enemyName";

        // Create a hitBox for collision detection
        hitBox = new Rectangle(x, y, type.getHitSize(), type.getHitSize());
    }

    /**
     * Moves the enemy ship based on its angle and speed.
     * Performs boundary checking and reflects the ship upon collision with the screen edges.
     */
    public void move() {
        x += (int) (shipSpeed * Math.cos(Math.toRadians(angle)));
        y += (int) (shipSpeed * Math.sin(Math.toRadians(angle)));
        hitBox.setLocation(x, y);

        // Check for collisions with screen boundaries and reflect the ship if necessary
        if (!AsteroidsGame.getScreenBounds().contains(getHitBox().getBounds())) {
            if (x < AsteroidsGame.getScreenBounds().getMinX() || x > AsteroidsGame.getScreenBounds().getMaxX()) {
                angle = 180 - angle; // Reflect horizontally
            }
            if (y < AsteroidsGame.getScreenBounds().getMinY() || y > AsteroidsGame.getScreenBounds().getMaxY()) {
                angle = -angle; // Reflect vertically
            }

            // Update position after reflection
            x += (int) (shipSpeed * Math.cos(Math.toRadians(angle)));
            y += (int) (shipSpeed * Math.sin(Math.toRadians(angle)));
            hitBox.setLocation(x, y);
        }
    }

    /**
     * Checks if the enemy ship is hit by a bullet.
     * If hit, sets the ship as not alive.
     *
     * @param b The Bullet object to check for a hit.
     * @return True if the ship is hit, false otherwise.
     */
    public boolean isHit(Bullet b) {
        boolean hit = getHitBox().intersects(b.getHitBox());
        if (hit && !b.getName().equals(enemyName)) {
            alive = false;
        }
        return hit;
    }

    /**
     * Checks if the enemy ship crashes into the player's ship.
     * If crashed, sets the ship as not alive.
     *
     * @param player The Player object to check for a crash.
     * @return True if the ship crashes into the player's ship, false otherwise.
     */
    public boolean isCrash(Player player) {
        if (player.isCrash(this)) {
            alive = false;
        }
        return player.isCrash(this);
    }

    /**
     * Gets the hitbox of the enemy ship for collision detection.
     *
     * @return The Rectangle representing the hitbox.
     */
    @Override
    public Rectangle getHitBox() {
        return new Rectangle((int) x, (int) y, type.getHitWidth(), type.getHitHeight());
    }

    public boolean isAlive() {
        return alive;
    }

    public int getPoints() {
        return type.getScore();
    }

    public boolean isPlayer() {
        return false;
    }

    /**
     * Fires a bullet from the enemy ship.
     *
     * @param x         The x-coordinate of the bullet's starting position.
     * @param y         The y-coordinate of the bullet's starting position.
     * @param fireAngle The firing angle of the bullet.
     * @return The Bullet object if a bullet is fired, null otherwise.
     */
    public Bullet fire(int x, int y, int fireAngle) {
        Bullet bullet = null;
        Random random = new Random();
        if (random.nextInt() % type.getFireFrequency() == 0) {
            // Firing bullets at random frequency
            bullet = new Bullet(x, y, fireAngle, "enemyName", shipSpeed + type.getBulletSpeed());
        }
        return bullet;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public AlienType getType() {
        return type;
    }

}
