package si.display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The PlayerListener class implements KeyListener to handle keyboard input for player controls in the Asteroids game.
 * It tracks the state of keys associated with player movement, firing, and pausing.
 */
public class PlayerListener implements KeyListener {
    private boolean left;
    private boolean right;
    private boolean fire;
    private boolean pause;
    private boolean up;

    public boolean isPressingLeft() {
        return left;
    }

    public boolean isPressingRight() {
        return right;
    }

    public boolean hasPressedFire() {
        return fire;
    }

    public boolean hasPressedPause() {
        return pause;
    }

    public boolean isPressingUp() {
        return up;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'P' || e.getKeyChar() == 'p') {
            pause = true;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            fire = true;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            fire = true;
        }
    }

    public void resetPause() {
        pause = false;
    }

    public void resetFire() {
        fire = false;
    }
}
