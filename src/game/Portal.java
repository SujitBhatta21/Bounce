package game;

import city.cs.engine.*;

public class Portal extends StaticBody {
    private static float radius = 1;
    private static float x_pos, y_pos;
    private static final Shape portalShape = new CircleShape(radius);

    private BodyImage image = new BodyImage("assets/images/physics/portal.png", 2*radius);
    private MyWorld world;

    Portal(MyWorld world, float x_pos, float y_pos) {
        super(world, portalShape);
        this.world = Game.getLevel().getLevelWorld();
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
