package si.model;

import si.display.PlayerListener;
import ucd.comp2011j.engine.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Asteroids class represents the main game logic and state for the
 * Asteroids game. It implements the Game interface.
 */
public class AsteroidsGame implements Game {
    // Screen dimensions
    public static final int SCREEN_WIDTH = 1080;
    public static final int SCREEN_HEIGHT = 768;
    private static final Rectangle SCREEN_BOUNDS = new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

    // Screen dimensions
    private int playerLives;
    private int playerScore;
    private boolean pause = true;
    private List<Bullet> playerBullets;
    private List<Bullet> enemyBullets;
    private List<Asteroid> asteroids;
    private ArrayList<Hittable> targets;
    private PlayerListener listener;
    private Player player;
    private Level[] level;
    private int currentLevel = 0;
    private int gameTime = 0;


    public AsteroidsGame(PlayerListener listener) {
        this.listener = listener;
        startNewGame();
    }

    @Override
    public int getPlayerScore() {
        return playerScore;
    }

    @Override
    public int getLives() {
        return playerLives;
    }

    // Method to check if the game should be paused
    public void checkForPause() {
        if (listener.hasPressedPause()) {
            pause = !pause;
            listener.resetPause();
        }
    }

    @Override
    public void updateGame() {

        if (!isPaused()) {
            gameTime++;
            if (gameTime % 1800 == 0) {
                if (currentLevel < 5) {
                    level[currentLevel].setEnemyShip(AlienType.Large);
                } else {
                    level[currentLevel].setEnemyShip(AlienType.Small);
                }
            }
            player.tick();
            targets.clear();
            targets.addAll(level[currentLevel].getHittable());
            targets.add(player);
            playerBullets();
            enemyBullets();
            crashAsteroids();
            crashEnemyShip();
            enemyBullets.addAll(level[currentLevel].move());
            movePlayer();
        }
    }

    /**
     * Checks for collisions between asteroids and the player's ship, updating lives and pausing if necessary.
     */
    public void crashAsteroids() {
        for (Asteroid asteroid : level[currentLevel].getAsteroids()) {
            if (asteroid.isCrash(player)) {
                playerLives--;
                pause = true;
                resetDestroyedPlayer();
            }
        }
    }

    /**
     * Checks for collisions between enemy ships and the player's ship, updating lives and pausing if necessary.
     */
    public void crashEnemyShip() {
        ArrayList<Hittable> remove = new ArrayList<>();
        for (int i = 0; i < level[currentLevel].getEnemyShips().size(); i++) {
            if (level[currentLevel].getEnemyShips().get(i).isCrash(player)) {
                playerLives--;
                pause = true;
            }
        }

    }

    /**
     * Moves the player's ship based on user input and handles firing bullets.
     */
    private void movePlayer() {
        boolean isAccelerate;
        if (listener.hasPressedFire()) {
            Bullet b = player.fire();
            if (b != null) {
                playerBullets.add(b);
                listener.resetFire();
            }
        }

        // Accelerate the player's ship if the 'up' key is pressed
        if (listener.isPressingUp()) {
            isAccelerate = true;
            if (player.getAngle() != player.getAccelerationDirectionAngle()) {
                player.resetSpeed();
            }
            player.setAccelerationDirectionAngle();
            player.accelerate();
        } else {
            isAccelerate = false;
        }

        // Rotate the player's ship based on left and right key presses
        if (listener.isPressingLeft()) {
            if (player.getAngle() != player.getAccelerationDirectionAngle() && isAccelerate) {
                player.rotateShip(5);
                player.setAccelerationDirectionAngle();
                player.resetSpeed();
            } else {
                player.rotateShip(5);
            }


        } else if (listener.isPressingRight()) {
            if (player.getAngle() != player.getAccelerationDirectionAngle() && isAccelerate) {
                player.rotateShip(-5);
                player.setAccelerationDirectionAngle();
                player.resetSpeed();
            } else {
                player.rotateShip(-5);
            }

        }

        player.moveShip(player.getSpeed());
    }

    private void playerBullets() {
        List<Bullet> remove = new ArrayList<Bullet>();
        for (int i = 0; i < playerBullets.size(); i++) {
            if (playerBullets.get(i).isAlive() && playerBullets.get(i).getHitBox().intersects(SCREEN_BOUNDS)) {
                // 如果子弹在页面内
                playerBullets.get(i).move();
                for (Hittable t : targets) {
                    // 判断是否击中可击打目标
                    if (t != playerBullets.get(i)) {
                        if (t.isHit(playerBullets.get(i))) {
                            playerScore += t.getPoints();
                            if (playerScore % 10000 == 0) {
                                playerLives++;
                            }
                            playerBullets.get(i).destroy();
                        }
                    }
                }
            } else {
                remove.add(playerBullets.get(i));
            }
        }
        playerBullets.removeAll(remove);
    }

    private void enemyBullets() {
        List<Bullet> remove = new ArrayList<Bullet>();
        for (EnemyShip ship : level[currentLevel].getEnemyShips()) {
            int fireAngle = 0;
            int x = 0;
            int y = 0;
            if (player.getY() > ship.getY() && player.getX() > ship.getX()) {
                x = ship.getX() + ship.getType().getHitWidth() / 2;
                y = ship.getY() + ship.getType().getHitHeight() / 2;
                fireAngle = (int) -Math.toDegrees(Math.atan2(Math.abs(player.getY() - ship.getY()), Math.abs(ship.getX() - player.getX())));
            } else if (player.getY() > ship.getY() && player.getX() < ship.getX()) {
                x = ship.getX() - ship.getType().getHitWidth() / 2;
                y = ship.getY() + ship.getType().getHitHeight() / 2;
                fireAngle = 180 + (int) Math.toDegrees(Math.atan2(Math.abs(player.getY() - ship.getY()), Math.abs(ship.getX() - player.getX())));
            } else if (player.getY() < ship.getY() && player.getX() < ship.getX()) {
                x = ship.getX() - ship.getType().getHitWidth() / 2;
                y = ship.getY() - ship.getType().getHitHeight() / 2;
                fireAngle = 180 - (int) Math.toDegrees(Math.atan2(Math.abs(player.getY() - ship.getY()), Math.abs(ship.getX() - player.getX())));
            } else if (player.getX() > ship.getX() && player.getY() < ship.getY()) {
                x = ship.getX() + ship.getType().getHitWidth() / 2;
                y = ship.getY() - ship.getType().getHitHeight() / 2;
                fireAngle = (int) Math.toDegrees(Math.atan2(Math.abs(player.getY() - ship.getY()), Math.abs(ship.getX() - player.getX())));

            }
            Bullet b = ship.fire(x, y, fireAngle);
            if (b != null) {
                enemyBullets.add(b);
            }
        }
        for (int i = 0; i < enemyBullets.size(); i++) {
            Bullet b = enemyBullets.get(i);
            if (b.isAlive() && b.getHitBox().intersects(SCREEN_BOUNDS)) {
                b.move();
                for (Hittable t : targets) {
                    if (t != b) {
                        if (t.isHit(b)) {
                            if (t.isPlayer()) {
                                playerLives--;
                                pause = true;
                            }
                            b.destroy();
                        }
                    }
                }
            } else {
                remove.add(b);
            }
        }
        enemyBullets.removeAll(remove);
    }

    public static Rectangle getScreenBounds() {
        return new Rectangle(SCREEN_BOUNDS);
    }

    @Override
    public boolean isPaused() {
        return pause;
    }

    @Override
    public void startNewGame() {
        targets = new ArrayList<Hittable>();
        playerLives = 3;
        playerScore = 0;
        playerBullets = new ArrayList<Bullet>();
        enemyBullets = new ArrayList<Bullet>();
        player = new Player();
        level = new Level[100];


        for (int i = 0; i < level.length; i++) {
            level[i] = new Level(1, i + 1, this);
        }
    }

    @Override
    public boolean isLevelFinished() {
        int shipsRemaining = level[currentLevel].getShipsRemaining();
        int asteroidsRemaining = level[currentLevel].getAsteroidsRemaining();
        if (shipsRemaining == 0 && asteroidsRemaining == 0) {
            gameTime = 0;
        }
        return shipsRemaining == 0 && asteroidsRemaining == 0;


    }

    @Override
    public boolean isPlayerAlive() {
        return player.isAlive();
    }

    @Override
    public void resetDestroyedPlayer() {
        player.resetDestroyed();
        playerBullets = new ArrayList<Bullet>();
        enemyBullets = new ArrayList<Bullet>();
    }

    @Override
    public void moveToNextLevel() {
        pause = true;
        currentLevel++;
        player.resetDestroyed();
        playerBullets = new ArrayList<Bullet>();
        enemyBullets = new ArrayList<Bullet>();
    }

    @Override
    public boolean isGameOver() {
        if (!(playerLives > 0)) {
            currentLevel = 0;
        }

        return !(playerLives > 0);
    }

    @Override
    public int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    @Override
    public int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public Player getShip() {
        return player;
    }

    public List<Bullet> getBullets() {
        ArrayList<Bullet> bullets = new ArrayList<Bullet>();
        bullets.addAll(playerBullets);
        bullets.addAll(enemyBullets);
        return bullets;
    }

    public List<EnemyShip> getEnemyShips() {
        return level[currentLevel].getEnemyShips();
    }

    public ArrayList<Asteroid> getAsteroid() {
        return level[currentLevel].getAsteroids();
    }

    public int getGameTime() {
        return gameTime;
    }


    public int getCurrentLevel() {
        return currentLevel;
    }
}
