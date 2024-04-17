package game;

import city.cs.engine.*;

/**
 * Represents Portals to allow teleport mechanism of ball.
 *
 * @author      Sujit Bhatta, sujit.bhatta@city.ac.uk
 * @version     1.0
 * @since       1.0
 */
public class Portal extends StaticBody {
    /**
     * The radius of the portal.
     */
    private static float radius = 1;
    /**
     * The x and y coordinates of the portal.
     */
    private static float x_pos, y_pos;
    /**
     * The shape of the portal.
     */
    private static final Shape portalShape = new CircleShape(radius);

    /**
     * The image displayed on the portal.
     */
    private BodyImage image = new BodyImage("assets/images/physics/portal.png", 2*radius);
    /**
     * The world in which the portal exists.
     */
    private MyWorld world;

    /**
     * Constructor for Portal.
     *
     * @param  world The world in which the portal exists.
     * @param  x_pos The x coordinate of the portal.
     * @param  y_pos The y coordinate of the portal.
     */
    Portal(MyWorld world, float x_pos, float y_pos) {
        super(world, portalShape);
        this.world = Game.getLevel().getLevelWorld();
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        addImage(image);
    }

    /**
     * Returns the x coordinate of the portal.
     *
     * @return The x coordinate of the portal.
     */
    public static float getXPos() {
        return x_pos;
    }

    /**
     * Returns the y coordinate of the portal.
     *
     * @return The y coordinate of the portal.
     */
    public static float getYPos() {
        return y_pos;
    }
}
