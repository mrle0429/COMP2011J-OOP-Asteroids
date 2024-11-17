package si.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Level class represents a game level in Asteroids.
 * It manages the swarm of enemy ships and asteroids for each level.
 */
public class Level {
    private Swarm swarm;
    private AsteroidsGame game;
    private int shipNum;
    private int asteroidNum;

    /**
     * Constructs a new Level instance with the specified number of ships,
     * number of asteroids, and the associated game.
     *
     * @param shipNum     The number of enemy ships in the swarm.
     * @param asteroidNum The number of asteroids in the swarm.
     * @param game        The SpaceInvadersGame associated with the level.
     */
    public Level(int shipNum, int asteroidNum, AsteroidsGame game) {
        this.game = game;
        this.shipNum = shipNum;
        this.asteroidNum = asteroidNum;

        reset();
    }

    public int getShipsRemaining() {
        return swarm.getShipsRemaining();
    }

    public int getAsteroidsRemaining() {
        return swarm.getAsteroidsRemaining();
    }

    public List<Hittable> getHittable() {
        List<Hittable> targets = new ArrayList<Hittable>();
        targets.addAll(swarm.getHittableAsteroids());
        targets.addAll(swarm.getHittableShip());

        return targets;
    }

    public List<Bullet> move() {
        swarm.move();
        List<Bullet> eBullets = new ArrayList<Bullet>();

        return eBullets;
    }

    public List<EnemyShip> getEnemyShips() {
        if (swarm.getEnemyShips() != null) {
            return swarm.getEnemyShips();
        }
        return null;
    }

    public ArrayList<Asteroid> getAsteroids() {
        return swarm.getAsteroids();
    }

    public void setEnemyShip(AlienType type) {
        swarm.addShip(game, type);
    }

    public int getAsteroidNum() {
        return asteroidNum;
    }

    public int getShipNum() {
        return shipNum;
    }

    public void reset() {
        swarm = new Swarm(game, shipNum, asteroidNum);
    }
}
