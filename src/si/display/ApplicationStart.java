package si.display;

import si.model.AsteroidsGame;
import ucd.comp2011j.engine.GameManager;
import ucd.comp2011j.engine.ScoreKeeper;

import javax.swing.*;

// Main class to start the Game
public class ApplicationStart {
    public static void main(String[] args) {
        // Create the main window
        JFrame mainWindow = new JFrame();
        mainWindow.setSize(AsteroidsGame.SCREEN_WIDTH, AsteroidsGame.SCREEN_HEIGHT);
        mainWindow.setResizable(false);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setTitle("Asteroids Game");
        mainWindow.setLocationRelativeTo(null);

        // Create a listener for player input
        PlayerListener playerListener = new PlayerListener();
        mainWindow.addKeyListener(playerListener);

        // Create a listener for menu input
        MenuListener menuListener = new MenuListener();
        mainWindow.addKeyListener(menuListener);

        // Create an instance of the SpaceInvadersGame with the player listener
        AsteroidsGame game = new AsteroidsGame(playerListener);

        // Create instances of the game screens
        GameScreen gameScreen = new GameScreen(game);
        MenuScreen menuScreen = new MenuScreen();

        // Create a ScoreKeeper with a file to store scores
        ScoreKeeper scoreKeeper = new ScoreKeeper("scores.txt");

        // Create a GameManager instance to manage the game, screens, and input listeners
        GameManager mmm = new GameManager(game, mainWindow, menuListener, menuScreen, new AboutScreen(), new ScoreScreen(scoreKeeper), gameScreen, scoreKeeper);
        mainWindow.setVisible(true);

        // Run the game manager
        mmm.run();
    }
}
