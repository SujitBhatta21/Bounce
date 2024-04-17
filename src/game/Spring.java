package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * This class represents a Spring in the game.
 *
 * @author      Sujit Bhatta, sujit.bhatta@city.ac.uk
 * @version     1.0
 * @since       1.0
 */
public class Spring extends StaticBody {
    /**
     * Width of the spring.
     */
    private static float width = 0.60f;

    /**
     * Height of the spring.
     */
    private static float height = 1.2f;

    /**
     * X position of the spring.
     */
    private static float x_pos;

    /**
     * Y position of the spring.
     */
    private static float y_pos;

    /**
     * Shape of the spring.
     */
    private static final Shape springShape = new BoxShape(width, height);

    /**
     * Image of the spring.
     */
    private BodyImage image = new BodyImage("assets/images/physics/spring_image.png", 2*height);

    /**
     * Constructor for the Spring class.
     *
     * @param  world The world in which the spring exists.
     * @param  x_pos The x position of the spring.
     * @param  y_pos The y position of the spring.
     */
    public Spring(MyWorld world, float x_pos, float y_pos) {
        super(world, springShape);
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        setPosition(new Vec2(x_pos, y_pos));
        addImage(image);
    }

    /**
     * Get the x position of the spring.
     *
     * @return The x position of the spring.
     */
    public static float getXPos() {
        return x_pos;
    }

    /**
     * Get the y position of the spring.
     *
     * @return The y position of the spring.
     */
    public static float getYPos() {
        return y_pos;
    }
}
