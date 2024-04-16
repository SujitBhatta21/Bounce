package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;


public class Spike extends StaticBody {
    private static float width = 0.60f ,height = 1.2f;
    private static float x_pos, y_pos;
    private static Shape boxShape = new BoxShape(width, height);
    private BodyImage image = new BodyImage("assets/images/physics/spike_image.png", 2*height);

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

    public static float getXPos() {
        return x_pos;
    }

    public static float getYPos() {
        return y_pos;
    }
}
