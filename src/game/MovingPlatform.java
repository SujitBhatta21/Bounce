package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class MovingPlatform extends StaticBody {
    private static float x_pos, y_pos;
    private static float width = 3f ,height = 0.5f;
    private static final Shape platformShape = new BoxShape(width, height);
    private BodyImage image = new BodyImage("assets/images/platform1.gif", 6*height);

    public MovingPlatform(MyWorld world, float x_pos, float y_pos) {
        super(world, platformShape);
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        setPosition(new Vec2(x_pos, y_pos)); // set the position of the MovingPlatform
        // setGravityScale(0);
        addImage(image);
    }

    public static float getXPos() {
        return x_pos;
    }

    public static float getYPos() {
        return y_pos;
    }
}

