package si.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents asteroid in the Asteroids game.
 * Implements the Hittable and Movable interface.
 */
public class Asteroid implements Hittable, Movable {
    private int x, y;
    private int speed;
    private int angle;
    private boolean alive;
    private AsteroidType type;
    private Rectangle hitBox;

    /**
     * Constructor to initialize the Asteroid with the specified type, position, and angle.
     *
     * @param type  The type of the asteroid.
     * @param x     The x-coordinate of the asteroid.
     * @param y     The y-coordinate of the asteroid.
     * @param angle The angle at which the asteroid is moving.
     */
    public Asteroid(AsteroidType type, int x, int y, int angle) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.alive = true;
        this.speed = type.getSpeed();
        this.angle = angle;

        hitBox = new Rectangle(x + type.getHitOfX(), y + type.getHitOfY(), type.getHitWidth(), type.getHitHeight());
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public int getPoints() {
        return type.getScore();
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    /**
     * Checks if the asteroid is hit by a bullet.
     *
     * @param b The Bullet object to check for collision.
     * @return true if the asteroid is hit by the bullet, false otherwise.
     */
    @Override
    public boolean isHit(Bullet b) {
        boolean hit = getHitBox().intersects(b.getHitBox());
        if (hit) {
            alive = false;
        }
        return hit;
    }

    /**
     * Checks if the asteroid has crashed with a player's ship.
     *
     * @param player The Player object representing the player's ship.
     * @return true if the asteroid has crashed with the player's ship, false otherwise.
     */
    public boolean isCrash(Player player) {
        boolean crash = getHitBox().intersects(player.getHitBox());
        if (crash) {
            alive = false;
        }
        return crash;
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(x + type.getHitOfX(), y + type.getHitOfY(), type.getHitWidth(), type.getHitHeight());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAngle() {
        return angle;
    }

    public AsteroidType getType() {
        return type;
    }

    ;

    @Override
    public void move() {
        x += (int) (speed * Math.cos(Math.toRadians(angle)));
        y += (int) (speed * Math.sin(Math.toRadians(angle)));
        hitBox.setLocation(x + type.getHitOfX(), y + type.getHitOfY());

        // Check for collisions with screen boundaries and reflect if necessary
        if (!AsteroidsGame.getScreenBounds().contains(getHitBox().getBounds())) {
            if (x < AsteroidsGame.getScreenBounds().getMinX() || x > AsteroidsGame.getScreenBounds().getMaxX()) {
                angle = 180 - angle;
            }
            if (y < AsteroidsGame.getScreenBounds().getMinY() || y > AsteroidsGame.getScreenBounds().getMaxY()) {
                angle = -angle;
            }

            x += (int) (speed * Math.cos(Math.toRadians(angle)));
            y += (int) (speed * Math.sin(Math.toRadians(angle)));
            hitBox.setLocation(x + type.getHitOfX(), y + type.getHitOfY());
        }
    }

    /**
     * Destroys the asteroid and generates new asteroids based on its type.
     *
     * @return ArrayList of new asteroids created after destruction. Returns null for small asteroids.
     */

    public ArrayList<Asteroid> destroy() {
        switch (this.getType()) {
            case LARGE:
                ArrayList<Asteroid> newAsteroidsMedium = new ArrayList<>();
                Random random = new Random();
                Asteroid newasteroid1 = new Asteroid(AsteroidType.MEDIUM, getX(), getY(), random.nextInt(getAngle() - 20, getAngle()));
                Asteroid newasteroid2 = new Asteroid(AsteroidType.MEDIUM, getX(), getY(), random.nextInt(getAngle(), getAngle() + 20));
                newAsteroidsMedium.add(newasteroid1);
                newAsteroidsMedium.add(newasteroid2);
                return newAsteroidsMedium;

            case MEDIUM:
                ArrayList<Asteroid> newAsteroidsSmall = new ArrayList<>();
                Random random1 = new Random();
                Asteroid asteroid1 = new Asteroid(AsteroidType.SMALL, getX(), getY(), random1.nextInt(getAngle() - 20, getAngle()));
                Asteroid asteroid2 = new Asteroid(AsteroidType.SMALL, getX(), getY(), random1.nextInt(getAngle(), getAngle() + 20));
                newAsteroidsSmall.add(asteroid1);
                newAsteroidsSmall.add(asteroid2);
                return newAsteroidsSmall;

            case SMALL:
                return null;

            default:
                return null;
        }
    }

}
