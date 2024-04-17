package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * This class represents a Spike in the game.
 *
 * @author      Sujit Bhatta, sujit.bhatta@city.ac.uk
 * @version     1.0
 * @since       1.0
 */
public class Spike extends StaticBody {
    /**
     * Width of the spike.
     */
    private static float width = 0.60f;

    /**
     * Height of the spike.
     */
    private static float height = 1.2f;

    /**
     * X position of the spike.
     */
    private static float x_pos;

    /**
     * Y position of the spike.
     */
    private static float y_pos;

    /**
     * Shape of the spike.
     */
    private static Shape boxShape = new BoxShape(width, height);

    /**
     * Image of the spike.
     */
    private BodyImage image = new BodyImage("assets/images/physics/spike_image.png", 2*height);

    /**
     * Constructor for the Spike class.
     *
     * @param  world The world in which the spike exists.
     * @param  x_pos The x position of the spike.
     * @param  y_pos The y position of the spike.
     */
    public Spike(MyWorld world, float x_pos, float y_pos) {
        super(world, boxShape);
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        setPosition(new Vec2(x_pos, y_pos));
        // addImage(image);
        if (Game.getLevel() instanceof Level4 && x_pos < 0) {
            rotateDegrees(-90);
            addImage(image);
        } else if (Game.getLevel() instanceof Level4 && x_pos > 0) {
            rotateDegrees(90);
            addImage(image);
        } else {
            addImage(image);
        }
    }

    /**
     * Get the x position of the spike.
     *
     * @return The x position of the spike.
     */
    public static float getXPos() {
        return x_pos;
    }

    /**
     * Get the y position of the spike.
     *
     * @return The y position of the spike.
     */
    public static float getYPos() {
        return y_pos;
    }
}
