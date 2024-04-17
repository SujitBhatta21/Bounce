package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import java.awt.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents a falling platform in the game.
 * @author      Sujit Bhatta, sujit.bhatta@city.ac.uk
 * @version     1.0
 * @since       1.0
 */
public class FallingPlatform extends StaticBody {
    /**
     * The width of the platform.
     */
    private static float width = 4f;

    /**
     * The height of the platform.
     */
    private static float height = 0.5f;

    /**
     * The x-coordinate of the platform.
     */
    private static float x_pos;

    /**
     * The y-coordinate of the platform.
     */
    private static float y_pos;

    /**
     * The shape of the platform.
     */
    private static final Shape platformShape = new BoxShape(width, height);

    /**
     * The timer for the platform.
     */
    private Timer timer;

    /**
     * The rotation timer for the platform.
     */
    private Timer rotationTimer;

    /**
     * The speed of rotation for the platform.
     */
    private int rotationSpeed = 1; // degrees per tick

    /**
     * The vertical state of the platform.
     */
    private boolean isVertical = false;

    /**
     * Constructor for the FallingPlatform class.
     * This is the platform that rotates till 90 degree to fall the ball if it touches it.
     * @param  world The world in which the platform exists.
     * @param  x_pos The x-coordinate of the platform.
     * @param  y_pos The y-coordinate of the platform.
     */
    public FallingPlatform(MyWorld world, float x_pos, float y_pos) {
        super(world, platformShape);
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        setPosition(new Vec2(x_pos, y_pos));
        this.setFillColor(Color.orange);

        rotationTimer = new Timer(50, new ActionListener() { // adjust delay for smoother/faster rotation
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isVertical) {
                    if (getAngleDegrees() < 90) {
                        rotateDegrees(rotationSpeed);
                    } else {
                        rotationTimer.stop();
                    }
                } else {
                    if (getAngleDegrees() > 0) {
                        rotateDegrees(-rotationSpeed);
                    } else {
                        rotationTimer.stop();
                    }
                }
            }
        });

        timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isVertical) {
                    isVertical = true;
                    timer.setDelay(3000);
                } else {
                    isVertical = false;
                }
                rotationTimer.start();
            }
        });
        timer.setRepeats(false);
    }

    /**
     * This method is called when the ball touches the platform.
     */
    public void ballTouched() {
        timer.restart();
    }

    /**
     * Getter for the x-coordinate of the platform.
     * @return The x-coordinate of the platform.
     */
    public static float getXPos() {
        return x_pos;
    }

    /**
     * Getter for the y-coordinate of the platform.
     * @return The y-coordinate of the platform.
     */
    public static float getYPos() {
        return y_pos;
    }

    /**
     * This method sets the falling platform to vertical.
     * The buttons are blocked by platform it's not vertical when the game ends
     * for level 3. So this method ensures there is no blockage from falling platform.
     */
    public void setVertical() {
        // Convert 90 degrees to radians setting the falling platform to vertical.
        float angleInRadians = (float) Math.toRadians(90);
        this.setAngle(angleInRadians);
    }
}
