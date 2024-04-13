package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class Mole extends StaticBody {
    private static float width = 0.75f, height = 1f;
    private float x_pos, y_pos;
    private static float undergroundYPos; // New variable for underground y-position
    private static boolean isUnderground = false;
    private long lastSwitchTime = System.currentTimeMillis();
    private static final Shape moleShape = new BoxShape(width, height);
    private BodyImage image = new BodyImage("assets/images/enemy/mole_image.png", 2*height);
    private BodyImage cloudImage = new BodyImage("assets/images/enemy/shield.png", 2*height);
    private AttachedImage moleImage;
    private AttachedImage moleCloudImage;

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

    public float getXPos() {
        return x_pos;
    }

    public float getYPos() {
        return y_pos;
    }

    public static boolean isUnderground() {
        return isUnderground;
    }

    public AttachedImage getMoleImage() {
        return moleImage;
    }
}
