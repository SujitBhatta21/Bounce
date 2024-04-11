package game;

import city.cs.engine.*;


public class Spike extends StaticBody {
    private static float width = 0.60f ,height = 1.2f;
    private static float x_pos, y_pos;
    private static final Shape ballShape = new BoxShape(width, height);

    private BodyImage image = new BodyImage("assets/images/physics/spike_image.png", 2*height);

    public Spike(MyWorld world, float x_pos, float y_pos) {
        super(world, ballShape);
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        addImage(image);
    }

    public static float getXPos() {
        return x_pos;
    }

    public static float getYPos() {
        return y_pos;
    }
}
