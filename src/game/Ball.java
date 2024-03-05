package game;

import city.cs.engine.*;

public class Ball extends Walker {
    private Portal lastPortal;
    private static float radius = 1;
    private static float x_pos, y_pos;
    private static final Shape ballShape = new CircleShape(radius);

    private BodyImage image = new BodyImage("assets/images/character/red_ball.png", 2*radius);

    public Ball(World world, float x_pos, float y_pos) {
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

    public static void setXPos(float newXPos) {
        x_pos = newXPos;
    }

    public static void setYPos(float newYPos) {
        y_pos = newYPos;
    }

    public void setImage(BodyImage image) {
        this.image = image;
    }

    public void setBallFriction(float setFriction) {
        SolidFixture ballFixture = new SolidFixture(this, ballShape);
        ballFixture.setFriction(setFriction);
    }

    public Portal getLastPortal() {
        return lastPortal;
    }

    public void setLastPortal(Portal lastPortal) {
        this.lastPortal = lastPortal;
    }
}
