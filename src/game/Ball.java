package game;

import city.cs.engine.*;

public class Ball extends DynamicBody {
    static float radius = 2;
    private static final Shape ballShape = new CircleShape(radius);

    private static final BodyImage image = new BodyImage("assets/images/character/red_ball.png", 2*radius);

    public Ball(World world) {
        super(world, ballShape);
        addImage(image);
    }
}
