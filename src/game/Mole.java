package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * Mole is a class that extends StaticBody and represents a mole in the game.
 *
 * @author      Sujit Bhatta, sujit.bhatta@city.ac.uk
 * @version     1.0
 * @since       1.0
 */
public class Mole extends StaticBody {
    /**
     * The width of the mole.
     */
    private static float width = 0.75f;

    /**
     * The height of the mole.
     */
    private static float height = 1f;

    /**
     * The x position of the mole.
     */
    private float x_pos;

    /**
     * The y position of the mole.
     */
    private float y_pos;

    /**
     * The y position of the mole when it's underground.
     */
    private static float undergroundYPos;

    /**
     * The state of the mole, whether it's underground or not.
     */
    private boolean isUnderground = false;

    /**
     * The last time the mole switched its state.
     */
    private long lastSwitchTime = System.currentTimeMillis();

    /**
     * The shape of the mole.
     */
    private static final Shape moleShape = new BoxShape(width, height);

    /**
     * The image of the mole.
     */
    private BodyImage image = new BodyImage("assets/images/enemy/mole_image.png", 2*height);

    /**
     * The image of the cloud.
     */
    private BodyImage cloudImage = new BodyImage("assets/images/enemy/shield.png", 2*height);

    /**
     * The attached image of the mole.
     */
    private AttachedImage moleImage;

    /**
     * The attached image of the mole's cloud.
     */
    private AttachedImage moleCloudImage;

    /**
     * The constructor for the Mole class.
     * @param world The world in which the game takes place.
     * @param x_pos The x position of the mole.
     * @param y_pos The y position of the mole.
     */
    public Mole(MyWorld world, float x_pos, float y_pos) {
        super(world, moleShape);
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.undergroundYPos = y_pos - 0.5f; // Set the underground y-position
        setPosition(new Vec2(getXPos(), getYPos()));
        moleImage = new AttachedImage(this, image, 1.0f, 0.0f, new Vec2(0, 0));
        moleCloudImage = new AttachedImage(this, cloudImage, 1.0f, 0.0f, new Vec2(0, 100));
        moleCloudImage.setScale(1.2f);
    }

    /**
     * Updates the timer of the mole.
     */
    public void updateMoleTimer() {
        long currentTime = System.currentTimeMillis();
        if (isUnderground && currentTime - lastSwitchTime >= 10000) {
            // Mole pops out after 10 seconds
            isUnderground = false;
            lastSwitchTime = currentTime;
            moleCloudImage.reset();
        } else if (!isUnderground && currentTime - lastSwitchTime >= 5000) {
            // Mole goes underground after 5 seconds
            isUnderground = true;
            lastSwitchTime = currentTime;
            moleCloudImage.setOffset(moleImage.getOffset());
        }

        // Testing if the timer works correctly for mole.
        System.out.println("Is UNDERGROUND? : " + isUnderground());
    }


    /**
     * Gets the x position of the mole.
     * @return The x position of the mole.
     */
    public float getXPos() {
        return x_pos;
    }

    /**
     * Gets the y position of the mole.
     * @return The y position of the mole.
     */
    public float getYPos() {
        return y_pos;
    }

    /**
     * Checks if the mole is underground.
     * @return True if the mole is underground, false otherwise.
     */
    public boolean isUnderground() {
        return isUnderground;
    }

    /**
     * Gets the attached image of the mole.
     * @return The attached image of the mole.
     */
    public AttachedImage getMoleImage() {
        return moleImage;
    }
}