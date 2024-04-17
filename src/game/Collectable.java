package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * This class represents a collectable item in the game.
 * @author      Sujit Bhatta, sujit.bhatta@city.ac.uk
 * @version     1.0
 * @since       1.0
 */
public class Collectable extends StaticBody {
    /**
     * The x-coordinate of the collectable.
     */
    private float x;

    /**
     * The y-coordinate of the collectable.
     */
    private float y;

    /**
     * The body of the collectable.
     */
    private StaticBody collectableBody;

    /**
     * The count of coins collected.
     */
    private static int coin_count = 0;

    /**
     * The maximum count of coins that can be collected.
     */
    private static int max_coin_count = 3;

    /**
     * The image of the collectable.
     */
    private BodyImage image = new BodyImage("assets/images/physics/key.gif", 1f);

    /**
     * The shape of the collectable.
     */
    private static final Shape collectableShape = new BoxShape(1, 0.5f);

    /**
     * Constructor for the Collectable class.
     * @param  world The world in which the collectable exists.
     * @param  x The x-coordinate of the collectable.
     * @param  y The y-coordinate of the collectable.
     */
    public Collectable(MyWorld world, float x, float y) {
        super(world, collectableShape);
        setPosition(new Vec2(x, y));

        // Load an image for the collectable
        addImage(new BodyImage("assets/images/physics/key.gif"));
    }

    /**
     * Getter for the x-coordinate of the collectable.
     * @return The x-coordinate of the collectable.
     */
    public float getX() {
        return x;
    }

    /**
     * Getter for the y-coordinate of the collectable.
     * @return The y-coordinate of the collectable.
     */
    public float getY() {
        return y;
    }

    /**
     * Getter for the count of coins collected.
     * @return The count of coins collected.
     */
    public int getCoin_count() {
        return coin_count;
    }

    /**
     * Setter for the count of coins collected.
     * @param  coin_count The new count of coins collected.
     */
    public void setCoin_count(int coin_count) {
        this.coin_count = coin_count;
    }

    /**
     * Getter for the maximum count of coins that can be collected.
     * @return The maximum count of coins that can be collected.
     */
    public int getMax_coin_count() {
        return max_coin_count;
    }

    /**
     * Setter for the maximum count of coins that can be collected.
     * @param  max_coin_count The new maximum count of coins that can be collected.
     */
    public void setMax_coin_count(int max_coin_count) {
        this.max_coin_count = max_coin_count;
    }
}
