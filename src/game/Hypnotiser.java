package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * Represents a Hypnotiser in the game.
 *
 * @author      Sujit Bhatta, sujit.bhatta@city.ac.uk
 * @version     1.0
 * @since       1.0
 */
public class Hypnotiser extends StaticBody {
    /**
     * Width of the Hypnotiser.
     */
    private static float width = 2f;

    /**
     * Height of the Hypnotiser.
     */
    private static float height = 1.2f;

    /**
     * X position of the Hypnotiser.
     */
    private static float x_pos;

    /**
     * Y position of the Hypnotiser.
     */
    private static float y_pos;

    /**
     * Number of times the Hypnotiser has been hit by a ball.
     */
    private static int ballHits = 0;

    /**
     * Shape of the Hypnotiser.
     */
    private static final Shape boxShape = new BoxShape(width, height);

    // Images for the Hypnotiser
    /**
     * Image of hypnotiser before any hits.
     */
    private BodyImage image1 = new BodyImage("assets/images/enemy/Hypnotiser_1.png", 2.7f * height);
    /**
     * Image of hypnotiser after 1 hit.
     */
    private BodyImage image2 = new BodyImage("assets/images/enemy/Hypnotiser_2.png", 2.7f * height);
    /**
     * Image of hypnotiser after 2 hits.
     */
    private BodyImage image3 = new BodyImage("assets/images/enemy/Hypnotiser_3.png", 2.7f * height);
    /**
     * Image of hypnotiser after 3 hits.
     */
    private BodyImage image4 = new BodyImage("assets/images/enemy/Hypnotiser_4.png", 2.7f * height);

    /**
     * Constructs a Hypnotiser at the specified position in the specified world.
     *
     * @param world The world in which to place the Hypnotiser.
     * @param x_pos The x position of the Hypnotiser.
     * @param y_pos The y position of the Hypnotiser.
     */
    public Hypnotiser(MyWorld world, float x_pos, float y_pos) {
        super(world, boxShape);
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        setPosition(new Vec2(x_pos, y_pos));
        addImage(image1);
    }

    /**
     * Returns the number of times the Hypnotiser has been hit by a ball.
     *
     * @return The number of times the Hypnotiser has been hit by a ball.
     */
    public static int getBallHits() {
        return ballHits;
    }

    /**
     * Sets the number of times the Hypnotiser has been hit by a ball.
     *
     * @param ballHits The number of times the Hypnotiser has been hit by a ball.
     */
    public void setBallHits(int ballHits) {
        this.ballHits = ballHits;
    }

    /**
     * Returns the x position of the Hypnotiser.
     *
     * @return The x position of the Hypnotiser.
     */
    public static float getXPos() {
        return x_pos;
    }

    /**
     * Returns the y position of the Hypnotiser.
     *
     * @return The y position of the Hypnotiser.
     */
    public static float getYPos() {
        return y_pos;
    }

    /**
     * Updates the image of the Hypnotiser based on the number of times it has been hit by a ball.
     */
    public void updateImage() {
        if (this.ballHits == 2) {
            removeAllImages();
            addImage(image2);
        } else if (this.ballHits == 4) {
            removeAllImages();
            addImage(image3);
        } else if (this.ballHits == 6) {
            removeAllImages();
            addImage(image4);
        }
    }
}
