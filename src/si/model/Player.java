package si.model;

import java.awt.*;

/**
 * The Player class represents the player's ship in the Asteroids game.
 */
public class Player implements Hittable {
    private int centerX;
    private int centerY;
    private final int radius = 40;
    private Rectangle hitBox;
    private int weaponCountdown;
    private boolean alive = true;
    private double angle = 90;
    private double speed;
    private final double MAXSpeed = 15;
    private double AccelerationDirectionAngle;


    public Player() {
        double radianAngle = Math.toRadians(45);
        centerX = AsteroidsGame.SCREEN_WIDTH / 2;
        centerY = 200;

        hitBox = new Rectangle((int) centerX - 20, (int) centerY - 30, (int) (Math.cos(radianAngle) * radius) + 10, (int) ((Math.cos(radianAngle) + 1) * radius));


    }

    /**
     * Checks if the player's ship is hit by a bullet.
     *
     * @param b The bullet to check for a hit.
     * @return True if the ship is hit, false otherwise.
     */
    public boolean isHit(Bullet b) {
        Rectangle s = b.getHitBox();
        if (s.intersects(hitBox.getBounds())) {
            alive = false;
        }
        return s.intersects(hitBox.getBounds());
    }

    public boolean isCrash(Asteroid asteroid) {
        Rectangle s = asteroid.getHitBox();
        if (s.intersects(hitBox.getBounds())) {
            alive = false;
        }
        return s.intersects(hitBox.getBounds());
    }

    public boolean isCrash(EnemyShip enemyShip) {
        Rectangle s = enemyShip.getHitBox();
        if (s.intersects(hitBox.getBounds())) {
            alive = false;
        }
        return s.intersects(hitBox.getBounds());
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    public void tick() {
        if (weaponCountdown > 0) {
            weaponCountdown--;
        } else {
            weaponCountdown = 10;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void resetDestroyed() {
        double radianAngle = Math.toRadians(45);
        alive = true;
        centerX = AsteroidsGame.SCREEN_WIDTH / 2;
        centerY = 200;
        angle = 90;
        speed = 0;

        hitBox = new Rectangle((int) centerX - 20, (int) centerY - 30, (int) (Math.cos(radianAngle) * radius) + 10, (int) ((Math.cos(radianAngle) + 1) * radius));
    }

    public int getPoints() {
        return -100;
    }

    public boolean isPlayer() {
        return true;
    }

    public Bullet fire() {
        double radianAngle = Math.toRadians(angle);
        Bullet b = null;
        if (weaponCountdown == 0) {
            b = new Bullet(centerX + (int) (radius * Math.cos(radianAngle)), centerY - (int) (radius * Math.sin(radianAngle)), getAngle(), "Player", getSpeed() + 5);
        }
        return b;
    }

    public void moveShip(double distance) {
        double radianAngle = Math.toRadians(getAccelerationDirectionAngle());
        int newX = (int) (centerX + distance * Math.cos(radianAngle));
        int newY = (int) (centerY - distance * Math.sin(radianAngle));

        // 创建新的边界框
        Rectangle newBox = new Rectangle(newX - 20, newY - 30, hitBox.width, hitBox.height);

        if (AsteroidsGame.getScreenBounds().contains(newBox.getBounds())) {
            // 如果新位置在屏幕内，更新飞船的位置和边界框
            centerX = newX;
            centerY = newY;
            hitBox.setLocation((int) centerX - 20, (int) centerY - 30);
        } else {
            if (newX < 20) {
                centerX = 1060;
                centerY = newY;
            } else if (newX > 1060) {
                centerX = 20;
                centerY = newY;
            } else if (newY < 30) {
                centerX = newX;
                centerY = 728;
            } else if (newY > 728) {
                centerX = newX;
                centerY = 30;
            }
            hitBox.setLocation(centerX - 20, centerY - 30);


        }

    }

    public void accelerate() {
        double radianAngle = Math.toRadians(getAccelerationDirectionAngle());

        if (AsteroidsGame.getScreenBounds().contains(hitBox) && getSpeed() <= MAXSpeed) {
            speed += 0.08;
            centerX += (int) ((speed) * Math.cos(radianAngle));
            centerY -= (int) ((speed) * Math.sin(radianAngle));
            hitBox.setLocation((int) centerX - 20, (int) centerY - 30);
        }
    }

    public void rotateShip(int deltaAngle) {
        angle = (angle + deltaAngle) % 360;
    }

    public int getX() {
        return centerX;
    }

    public int getY() {
        return centerY;
    }

    public double getAngle() {
        return angle;
    }

    public double getSpeed() {
        return speed;
    }

    public void resetSpeed() {
        speed = 3;
    }

    public int getRadius() {
        return radius;
    }

    public double getAccelerationDirectionAngle() {
        return AccelerationDirectionAngle;
    }

    public void setAccelerationDirectionAngle() {
        AccelerationDirectionAngle = angle;
    }
}

