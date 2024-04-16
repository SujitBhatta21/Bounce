package game;

import city.cs.engine.*;

public class Ball extends Walker {
    public enum Mode {
        BOUNCE, ROCKY, VOLLEY
    }
    private Portal lastPortal;
    private static float radius = 1;
    private static float x_pos, y_pos;
    private static final Shape ballShape = new CircleShape(radius);
    private BodyImage image = new BodyImage("assets/images/character/red_ball.png", 2*radius);
    private int ballMaxHealth = 5;
    private int ballHealth = ballMaxHealth;
    private float ballSpeed;
    private Mode ballMode;
    private boolean onMovingPlatform = false;

    public Ball(World world, float x_pos, float y_pos) {
        super(world, ballShape);
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.ballSpeed = 8;
        ballMode = Mode.BOUNCE;
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
        removeAllImages();
        this.image = image;
        addImage(image);
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

    public int getBallHealth() {
        return ballHealth;
    }

    public void setBallHealth(int ballHealth) {
        this.ballHealth = ballHealth;
    }

    public int getBallMaxHealth() {
        return ballMaxHealth;
    }

    public void setBallMaxHealth(int ballMaxHealth) {
        this.ballMaxHealth = ballMaxHealth;
    }

    public void setBallMode(Mode mode) {
        this.ballMode = mode;
        changeBallMode(mode);
    }

    public Mode getBallMode() {
        return ballMode;
    }

    public void changeBallMode(Mode mode) {
        if (mode == Mode.BOUNCE) {
            // Set ball to default values.
            setGravityScale(1.0f); // Normal gravity
            setImage(new BodyImage("assets/images/character/red_ball.png", 2*radius));
            setBall_speed(8);
        } else if (mode == Mode.ROCKY) {
            // Increase the gravity.
            setGravityScale(1.5f); // Heavier gravity
            setImage(new BodyImage("assets/images/character/rockball.png", 2.4f*radius)); // Change the image to rock ball
            // Less jump.
            setBall_speed(5);
        } else if (mode == Mode.VOLLEY) {
            // Decrease the gravity.
            setGravityScale(0.5f); // Lighter gravity
            setImage(new BodyImage("assets/images/character/beach_ball.png", 2f*radius)); // Change the image to rock ball
            // More jump.
            setBall_speed(9.5f);
        }
    }

    public boolean canBreakWall() {
        if (this.ballMode == Mode.ROCKY) {
            return true;
        } else {
            return false;
        }
    }

    public float getBallSpeed() {
        return ballSpeed;
    }

    public void setBall_speed(float ball_speed) {
        this.ballSpeed = ball_speed;
    }
}
