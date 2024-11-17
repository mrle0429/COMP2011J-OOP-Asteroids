package si.display;

import si.model.*;

import javax.swing.*;
import java.awt.*;


/**
 * Represents the game screen panel for the Asteroids game.
 * This panel is responsible for rendering the game graphics.
 */
public class GameScreen extends JPanel {
    private static final long serialVersionUID = -8282302849760730222L;
    private AsteroidsGame game;

    public GameScreen(AsteroidsGame game) {
        this.game = game;
    }

    /**
     * Draws the player's spaceship on the game screen.
     *
     * @param gc The Graphics2D object for drawing.
     * @param p  The Player object representing the player's spaceship.
     */
    private void drawPlayerShip(Graphics2D gc, Player p) {
        int centerX = p.getX();
        int centerY = p.getY();

        int radius = p.getRadius();
        int[] shipX = new int[3];
        int[] shipY = new int[3];

        double radianAngle = Math.toRadians(p.getAngle());

        // Calculate the coordinates of the three points of the spaceship
        shipX[0] = centerX + (int) (radius * Math.cos(radianAngle));
        shipY[0] = centerY - (int) (radius * Math.sin(radianAngle));

        shipX[1] = centerX + (int) (radius * Math.cos(radianAngle + Math.toRadians(135)));
        shipY[1] = centerY - (int) (radius * Math.sin(radianAngle + Math.toRadians(135)));

        shipX[2] = centerX + (int) (radius * Math.cos(radianAngle - Math.toRadians(135)));
        shipY[2] = centerY - (int) (radius * Math.sin(radianAngle - Math.toRadians(135)));

        Polygon pg = new Polygon(shipX, shipY, shipX.length);

        gc.setColor(Color.WHITE);
        gc.fillPolygon(pg);
    }

    /**
     * Draws a bullet on the game screen.
     *
     * @param gc The Graphics2D object for drawing.
     * @param b  The Bullet object representing the bullet to be drawn.
     */
    private void drawBullet(Graphics2D gc, Bullet b) {
        gc.setColor(Color.GREEN);
        gc.fillRect(b.getX(), b.getY(), b.BULLET_WIDTH, b.BULLET_HEIGHT);
    }

    /**
     * Draws an enemy ship on the game screen. The specific appearance depends on the type of the enemy ship.
     *
     * @param gc The Graphics2D object for drawing.
     * @param es The EnemyShip object representing the enemy ship to be drawn.
     */
    private void drawEnemyShip(Graphics2D gc, EnemyShip es) {
        if (es.getType() == AlienType.Large) {
            drawEnemy(gc, es);
            // Uncomment the line below to visualize the hitBox of the enemyShip
            //gc.draw(es.getHitBox());
        } else if (es.getType() == AlienType.Small) {
            drawEnemy(gc, es);
            //gc.draw(es.getHitBox());
        }
    }

    /**
     * Draws asteroids on the game screen. The appearance of the asteroids depends on their type.
     *
     * @param gc       The Graphics2D object for drawing.
     * @param asteroid The Asteroid object representing the asteroid to be drawn.
     */
    public void drawAsteroids(Graphics2D gc, Asteroid asteroid) {
        if (asteroid.getType() == AsteroidType.LARGE) {
            drawAsteroid(gc, asteroid);
            // Uncomment the line below to visualize the hitBox of the asteroid
            //gc.draw(asteroid.getHitBox());
        } else if (asteroid.getType() == AsteroidType.MEDIUM) {
            drawAsteroid(gc, asteroid);
            //gc.draw(asteroid.getHitBox());
        } else if (asteroid.getType() == AsteroidType.SMALL) {
            drawAsteroid(gc, asteroid);
            //gc.draw(asteroid.getHitBox());
        }
    }

    /**
     * Draws a single asteroid on the game screen.
     *
     * @param gc The Graphics2D object for drawing.
     * @param as The Asteroid object representing the asteroid to be drawn.
     */
    private void drawAsteroid(Graphics2D gc, Asteroid as) {
        int x = as.getX();
        int y = as.getY();

        int[] x_coords = new int[]{-10, 5, 15, 20, 10, 0, -10};
        int[] y_coords = new int[]{-5, -15, -10, 5, 15, 10, 0};

        int[] x_adjusted = new int[x_coords.length];
        int[] y_adjusted = new int[y_coords.length];
        for (int i = 0; i < x_coords.length; i++) {
            x_adjusted[i] = x + x_coords[i] * as.getType().getSize();
            y_adjusted[i] = y + y_coords[i] * as.getType().getSize();
        }

        gc.setColor(Color.GRAY);
        gc.fillPolygon(x_adjusted, y_adjusted, x_adjusted.length);
    }

    /**
     * Draws a single enemy ship on the game screen.
     *
     * @param gc The Graphics2D object for drawing.
     * @param es The EnemyShip object representing the enemy ship to be drawn.
     */
    private void drawEnemy(Graphics2D gc, EnemyShip es) {
        int x = es.getX();
        int y = es.getY();
        int[] x_coords = new int[]{0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 8, 8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 2, 1, 1, 0};
        int[] y_coords = new int[]{7, 4, 4, 3, 3, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2, 3, 3, 4, 4, 7, 7, 5, 5, 7, 7, 6, 6, 7, 7, 6, 6, 7, 7, 5, 5, 7, 7};
        int[] x_adjusted = new int[x_coords.length];
        int[] y_adjusted = new int[y_coords.length];
        for (int i = 0; i < x_coords.length; i++) {
            x_adjusted[i] = x + x_coords[i] * es.getType().getSize();
            y_adjusted[i] = y + y_coords[i] * es.getType().getSize();
        }

        gc.setColor(Color.GREEN);
        gc.fillPolygon(x_adjusted, y_adjusted, x_adjusted.length);
        gc.fillRect(x + 2 * es.getType().getSize(), y + es.getType().getSize() * 0, es.getType().getSize(), es.getType().getSize());
        gc.fillRect(x + 6 * es.getType().getSize(), y + es.getType().getSize() * 0, es.getType().getSize(), es.getType().getSize());

    }

    /**
     * Overrides the paintComponent method to paint the game screen.
     *
     * @param g The Graphics object for painting.
     */
    protected void paintComponent(Graphics g) {
        if (game != null) {
            Graphics2D g2 = (Graphics2D) g;
            g.setColor(Color.black);
            g.fillRect(0, 0, AsteroidsGame.SCREEN_WIDTH, AsteroidsGame.SCREEN_HEIGHT);
            g.setColor(Color.green);
            g.drawString("Lives: " + game.getLives(), 0, 20);
            g.drawString("Score: " + game.getPlayerScore(), AsteroidsGame.SCREEN_WIDTH / 2, 20);
            //g.drawString("Time " + game.getGameTime(), 40,20);
            g.drawString("Level: " + (game.getCurrentLevel() + 1), 1000, 20);

            drawPlayerShip(g2, game.getShip());
            // Uncomment the line below to visualize the hitBox of the player ship
            //g2.draw(game.getShip().getHitBox());

            for (Bullet bullet : game.getBullets()) {
                drawBullet(g2, bullet);
            }

            for (EnemyShip e : game.getEnemyShips()) {
                drawEnemyShip(g2, e);
            }

            for (Asteroid asteroid : game.getAsteroid()) {
                drawAsteroids(g2, asteroid);
            }

            if (game.isPaused() && !game.isGameOver()) {
                g.setColor(Color.GREEN);
                g.drawString("Press 'p' to continue ", 256, 256);
            } else if (game.isGameOver()) {
                g.setColor(Color.GREEN);
                g.drawString("Game over ", 480, 256);
            }
        }
    }
}
