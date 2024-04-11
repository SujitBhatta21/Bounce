package game;

import city.cs.engine.*;

public class Ball extends Walker {
    private Portal lastPortal;
    private static float radius = 1;
    private static float x_pos, y_pos;
    private static final Shape ballShape = new CircleShape(radius);
    private BodyImage image = new BodyImage("assets/images/character/red_ball.png", 2*radius);
    private int ballMaxHealth = 5;
    private int ballHealth = ballMaxHealth;
    private float ballSpeed;
    private int ballMode = 1;
    private boolean onMovingPlatform = false;

    public Ball(World world, float x_pos, float y_pos) {
        super(world, ballShape);
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.ballSpeed = 8;
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

    public void setBallMode(int mode) {
        this.ballMode = mode;
        changeBallMode(mode);
    }

    public int getBallMode() {
        return ballMode;
    }

    public void changeBallMode(int mode) {
        if (mode == 1) {
            // Set ball to default values.
            setGravityScale(1.0f); // Normal gravity
            setImage(new BodyImage("assets/images/character/red_ball.png", 2*radius));
            setBall_speed(8);
        } else if (mode == 2) {
            // Increase the gravity.
            setGravityScale(2.0f); // Heavier gravity
            setImage(new BodyImage("assets/images/character/rockball.png", 2.4f*radius)); // Change the image to rock ball
            // Less jump.
            setBall_speed(4);
        }
    }


    public boolean canBreakWall() {
        if (this.ballMode == 2) {
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
