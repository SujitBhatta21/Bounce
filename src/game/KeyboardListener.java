package game;

import city.cs.engine.DynamicBody;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Represents a KeyboardListener in the game.
 *
 * @author      Sujit Bhatta, sujit.bhatta@city.ac.uk
 * @version     1.0
 * @since       1.0
 */
public class KeyboardListener implements KeyListener{
    /**
     * The world in which the game is taking place.
     */
    private MyWorld world;

    /**
     * The frame in which the game is displayed.
     */
    private JFrame frame;

    /**
     * The view of the user.
     */
    private MyUserView view;

    /**
     * The ball that the user controls.
     */
    private Ball ball;

    /**
     * The speed of the ball.
     */
    private float ball_speed;

    /**
     * Constructs a KeyboardListener with the specified parameters.
     *
     * @param  ball The ball that the user controls.
     * @param  world The world in which the game is taking place.
     * @param  frame The frame in which the game is displayed.
     * @param  view The view of the user.
     */
    KeyboardListener(Ball ball, MyWorld world, JFrame frame, MyUserView view) {
        this.ball = ball;
        this.world = world;
        this.frame = frame;
        this.view = view;
    }

    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override
    /**
     * Handles the event when a key is pressed.
     *
     * @param  e The KeyEvent.
     */
    public void keyPressed(KeyEvent e) {
        ball_speed = Game.getLevel().getBall().getBallSpeed();

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            System.out.println("Up Arrow-Key is pressed!");
            ball.jump(ball_speed);
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            System.out.println("DOWN Arrow-Key is pressed!");
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            System.out.println("LEFT Arrow-Key is pressed!");
            ball.startWalking(-ball_speed);
            ball.rotate(0.2f);
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            System.out.println("Right Arrow-Key is pressed!");
            ball.startWalking(ball_speed);
            ball.rotate(-0.2f);
        }
        else if (e.getKeyCode() == KeyEvent.VK_R) {
            Game.resetLevel();
        }
        else if (e.getKeyCode() == KeyEvent.VK_M) {
            Ball.Mode currentMode = ball.getBallMode();
            Ball.Mode newMode;
            if (Game.getLevel() instanceof Level2 || Game.getLevel() instanceof Level3) {
                // For Level2 and Level3, only switch between BOUNCE and ROCKY
                if (currentMode == Ball.Mode.BOUNCE) {
                    newMode = Ball.Mode.ROCKY;
                }
                else {
                    newMode = Ball.Mode.BOUNCE;
                }
            } else if (Game.getLevel() instanceof Level4) {
                // For Level4, cycle through all three modes
                switch (currentMode) {
                    case BOUNCE:
                        newMode = Ball.Mode.ROCKY;
                        break;
                    case ROCKY:
                        newMode = Ball.Mode.VOLLEY;
                        break;
                    case VOLLEY:
                    default:
                        newMode = Ball.Mode.BOUNCE;
                        break;
                }
            } else {
                // For other levels, keep the mode unchanged
                newMode = currentMode;
            }
            ball.setBallMode(newMode);
        }
    }

    @Override
    /**
     * Handles the event when a key is released.
     *
     * @param  e The KeyEvent.
     */
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            // Stop the ball's movement when left or right key is released
            ball.stopWalking();
        }
    }
}
