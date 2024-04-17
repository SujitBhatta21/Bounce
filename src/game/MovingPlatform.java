package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * MovingPlatform is a class that extends StaticBody and represents a moving platform in the game.
 * @author Your Name
 * @version 1.0
 * @since 1.0
 */
public class MovingPlatform extends StaticBody {
    /**
     * The x position of the moving platform.
     */
    private static float x_pos;

    /**
     * The y position of the moving platform.
     */
    private static float y_pos;

    /**
     * The width of the moving platform.
     */
    private static float width = 3f;

    /**
     * The height of the moving platform.
     */
    private static float height = 0.5f;

    /**
     * The shape of the moving platform.
     */
    private static final Shape platformShape = new BoxShape(width, height);

    /**
     * The image of the moving platform.
     */
    private BodyImage image = new BodyImage("assets/images/platform/platform1.gif", 6*height);

    /**
     * The constructor for the MovingPlatform class.
     * @param world The world in which the game takes place.
     * @param x_pos The x position of the moving platform.
     * @param y_pos The y position of the moving platform.
     */
    public MovingPlatform(MyWorld world, float x_pos, float y_pos) {
        super(world, platformShape);
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        setPosition(new Vec2(x_pos, y_pos)); // set the position of the MovingPlatform
        // setGravityScale(0);
        addImage(image);
    }

    /**
     * Gets the x position of the moving platform.
     * @return The x position of the moving platform.
     */
    public static float getXPos() {
        return x_pos;
    }

    /**
     * Gets the y position of the moving platform.
     * @return The y position of the moving platform.
     */
    public static float getYPos() {
        return y_pos;
    }
}
