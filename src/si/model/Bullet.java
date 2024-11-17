package si.model;

import java.awt.*;

/**
 * The Bullet class represents projectiles fired by various entities in the game.
 * Bullets are movable and hittable objects with a defined speed and trajectory.
 */
public class Bullet implements Movable, Hittable {
    private int x, y;
    private boolean alive = true;
    private Rectangle hitBox;
    private String name;
    private static int bulletCounter = 0;
    public static final int BULLET_HEIGHT = 8;
    public static final int BULLET_WIDTH = 4;
    private static final int BULLET_SPEED = 5;
    private double angle;
    private double speed;

    /**
     * Constructor for creating a new Bullet instance.
     *
     * @param x     The initial x-coordinate of the bullet.
     * @param y     The initial y-coordinate of the bullet.
     * @param angle The firing angle of the bullet in degrees.
     * @param name  The name associated with the entity firing the bullet.
     * @param speed The initial speed of the bullet.
     */
    public Bullet(int x, int y, double angle, String name, double speed) {
        this.angle = angle;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.name = name;
        bulletCounter++;
        hitBox = new Rectangle(x, y, BULLET_WIDTH, BULLET_HEIGHT);
    }

    public void move() {
        double radianAngle = Math.toRadians(angle);
        x += (int) ((speed + BULLET_SPEED) * Math.cos(radianAngle));
        y -= (int) ((speed + BULLET_SPEED) * Math.sin(radianAngle));

        hitBox.setLocation(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHit(Bullet b) {
        if (hitBox.intersects(b.hitBox)) {
            alive = false;
            b.alive = false;
        }
        return hitBox.intersects(b.hitBox);
    }

    public Rectangle getHitBox() {
        return new Rectangle(hitBox);
    }

    public boolean isAlive() {
        return alive;
    }

    public int getPoints() {
        return 0;
    }

    public boolean isPlayer() {
        return false;
    }

    public void destroy() {
        alive = false;
    }

    public String getName() {
        return name;
    }
}
