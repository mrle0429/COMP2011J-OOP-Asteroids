package si.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The Swarm class represents a group of enemy ships and asteroids in the  game.
 * It handles their movement, creation, and removal based on game interactions.
 */
public class Swarm implements Movable {
    private List<EnemyShip> ships;
    private ArrayList<Asteroid> asteroids;
    private AsteroidsGame game;
    private int shipNum;
    private int asteroidsNum;

    /**
     * Constructs a Swarm object with the specified game, number of enemy ships, and number of asteroids.
     *
     * @param game         The SpaceInvadersGame instance.
     * @param shipNum      The number of enemy ships in the swarm.
     * @param asteroidsNum The number of asteroids in the swarm.
     */
    public Swarm(AsteroidsGame game, int shipNum, int asteroidsNum) {
        this.game = game;
        this.shipNum = shipNum;
        this.asteroidsNum = asteroidsNum;
        ships = new ArrayList<EnemyShip>();
        asteroids = new ArrayList<Asteroid>();

        // Create and add asteroids to the swarm
        for (int i = 0; i < asteroidsNum; i++) {
            Asteroid asteroid;
            Random random = new Random();
            int asteroidX = random.nextInt(game.getScreenWidth() - 120);
            int asteroidY = random.nextInt(game.getScreenHeight() - 120);
            int asteroidAngle = random.nextInt(360);

            // Ensure that the asteroid is not intersecting with the player's initial position or out of bounds
            asteroid = new Asteroid(AsteroidType.LARGE, asteroidX, asteroidY, asteroidAngle);
            while (asteroid.getHitBox().intersects(new Rectangle(520, 170, 40, 80)) || !AsteroidsGame.getScreenBounds().contains(asteroid.getHitBox().getBounds())) {
                asteroidX = random.nextInt(game.getScreenWidth() - 120);
                asteroidY = random.nextInt(game.getScreenHeight() - 120);
                asteroidAngle = random.nextInt(360);
                asteroid = new Asteroid(AsteroidType.LARGE, asteroidX, asteroidY, asteroidAngle);
            }
            asteroids.add(asteroid);
        }
    }

    public void addShip(AsteroidsGame g, AlienType type) {
        for (int i = 0; i < shipNum; i++) {
            EnemyShip a;
            Random random = new Random();
            int shipX = random.nextInt(g.getScreenWidth() - 120);
            int shipY = random.nextInt(g.getScreenHeight() - 120);
            a = new EnemyShip(shipX, shipY, type);
            while (a.getHitBox().intersects(new Rectangle(520, 170, 10, 40))) {
                shipX = random.nextInt(g.getScreenWidth() - 120);
                shipY = random.nextInt(g.getScreenHeight() - 120);
                a = new EnemyShip(shipX, shipY, type);
            }
            ships.add(a);
        }
    }

    public void move() {
        List<EnemyShip> removeship = new ArrayList<EnemyShip>();
        ArrayList<Asteroid> removeAsteroid = new ArrayList<>();
        ArrayList<Asteroid> newAsteroid = new ArrayList<>();
        for (EnemyShip s : ships) {
            if (!s.isAlive()) {
                removeship.add(s);

            }
        }
        for (Asteroid asteroid : asteroids) {
            if (!asteroid.isAlive()) {
                removeAsteroid.add(asteroid);
                if (asteroid.destroy() != null) {
                    newAsteroid.addAll(asteroid.destroy());
                }
            }
        }
        asteroids.addAll(newAsteroid);
        ships.removeAll(removeship);
        asteroids.removeAll(removeAsteroid);

        for (EnemyShip ship : ships) {
            ship.move();
        }

        for (Asteroid asteroid : asteroids) {
            if (asteroid != null) {
                asteroid.move();
            }
        }

    }

    public List<Hittable> getHittableAsteroids() {
        return new ArrayList<Hittable>(asteroids);
    }

    public ArrayList<Hittable> getHittableShip() {
        return new ArrayList<Hittable>(ships);
    }

    public List<EnemyShip> getEnemyShips() {
        return new ArrayList<EnemyShip>(ships);
    }

    public ArrayList<Asteroid> getAsteroids() {
        return new ArrayList<Asteroid>(asteroids);
    }

    public int getShipsRemaining() {
        return ships.size();
    }

    public int getAsteroidsRemaining() {
        return asteroids.size();
    }
}
