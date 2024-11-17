package si.display;

import si.model.AsteroidsGame;
import ucd.comp2011j.engine.Score;
import ucd.comp2011j.engine.ScoreKeeper;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the score screen panel for the Asteroids game.
 * This panel is responsible for displaying and rendering top scores.
 */
public class ScoreScreen extends JPanel {
    private static final long serialVersionUID = 1616386874546775416L;
    private ScoreKeeper scoreKeeper;

    public ScoreScreen(ScoreKeeper sc) {
        this.scoreKeeper = sc;
    }

    private void drawString(Graphics g, String text, Rectangle rect, int size) {
        Graphics2D g2d = (Graphics2D) g.create();

        Font font = new Font("Arial", Font.BOLD, size);
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics();
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();

        g2d.setColor(Color.GREEN);
        g2d.drawString(text, x, y);
    }

    public void paintComponent(Graphics g) {
        g.fillRect(0, 0, AsteroidsGame.SCREEN_WIDTH, AsteroidsGame.SCREEN_HEIGHT);
        drawString(g, "Asteroids Hall of Fame", new Rectangle(0, 0, AsteroidsGame.SCREEN_WIDTH, AsteroidsGame.SCREEN_HEIGHT / 8), 36);
        g.setColor(Color.GREEN);
        Score[] scores = scoreKeeper.getScores();
        g.setFont(new Font("Arial", Font.BOLD, 24));
        for (int i = 0; i < scores.length; i++) {
            Score score = scores[i];
            g.drawString(score.getName(), 2 * AsteroidsGame.SCREEN_WIDTH / 6, 96 + i * 32);
            g.drawString("" + score.getScore(), 4 * AsteroidsGame.SCREEN_WIDTH / 6, 96 + i * 32);
        }
        drawString(g, "Press 'M' to return to the Main Menu", new Rectangle(0, 416, AsteroidsGame.SCREEN_WIDTH, 96), 24);
    }
}