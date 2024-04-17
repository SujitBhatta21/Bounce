package game;

import city.cs.engine.*;

/**
 *  The Ball class extends the Walker class and represents a ball in the game.
 *  It has different modes and properties like health, speed, and position.
 *
 * @author      Sujit Bhatta, sujit.bhatta@city.ac.uk
 * @version     1.0
 * @since       1.0
 */
public class Ball extends Walker {

    /**
     * The Mode enum represents the different modes a ball can have.
     */
    public enum Mode {
        BOUNCE, ROCKY, VOLLEY
    }

    /**
     * The last portal the ball went through.
     */
    private Portal lastPortal;

    /**
     * The radius of the ball.
     */
    private static float radius = 1;

    /**
     * The x and y positions of the ball.
     */
    private static float x_pos, y_pos;

    /**
     * The shape of the ball.
     */
    private static final Shape ballShape = new CircleShape(radius);

    /**
     * The image of the ball.
     */
    private BodyImage image = new BodyImage("assets/images/character/red_ball.png", 2*radius);

    /**
     * The maximum health and current health of the ball.
     */
    private int ballMaxHealth = 5;
    private int ballHealth = ballMaxHealth;

    /**
     * The speed of the ball.
     */
    private float ballSpeed;

    /**
     * The mode of the ball.
     */
    private Mode ballMode;

    /**
     * Whether the ball is on a moving platform.
     */
    private boolean onMovingPlatform = false;

    /**
     * Constructs a new ball object.
     *
     * @param  world The world in which the ball exists.
     * @param  x_pos The initial x position of the ball.
     * @param  y_pos The initial y position of the ball.
     */
    public Ball(World world, float x_pos, float y_pos) {
        super(world, ballShape);
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.ballSpeed = 8;
        ballMode = Mode.BOUNCE;
        addImage(image);
    }

    /**
     * Gets the x position of the ball.
     *
     * @return The current x position of the ball.
     */
    public static float getXPos() {
        return x_pos;
    }

    /**
     * Gets the y position of the ball.
     *
     * @return The current y position of the ball.
     */
    public static float getYPos() {
        return y_pos;
    }

    /**
     * Sets the x position of the ball.
     *
     * @param newXPos The new x position of the ball.
     */
    public static void setXPos(float newXPos) {
        x_pos = newXPos;
    }

    /**
     * Sets the y position of the ball.
     *
     * @param newYPos The new y position of the ball.
     */
    public static void setYPos(float newYPos) {
        y_pos = newYPos;
    }

    /**
     * Sets the image of the ball.
     * It first removes previous ball image and then adds the new image.
     *
     * @param image The new image of the ball.
     */
    public void setImage(BodyImage image) {
        removeAllImages();
        this.image = image;
        addImage(image);
    }

    /**
     * Sets the friction of the ball.
     * Used to add less or more friction on ball depending on its mode.
     *
     * @param setFriction The new friction of the ball.
     */
    public void setBallFriction(float setFriction) {
        SolidFixture ballFixture = new SolidFixture(this, ballShape);
        ballFixture.setFriction(setFriction);
    }

    /**
     * Gets the last portal the ball went through.
     *
     * @return The last portal the ball went through.
     */
    public Portal getLastPortal() {
        return lastPortal;
    }

    /**
     * Sets the last portal the ball went through.
     *
     * @param lastPortal The last portal the ball went through.
     */
    public void setLastPortal(Portal lastPortal) {
        this.lastPortal = lastPortal;
    }

    /**
     * Gets the current health of the ball.
     *
     * @return The current health of the ball.
     */
    public int getBallHealth() {
        return ballHealth;
    }

    /**
     * Sets the current health of the ball.
     *
     * @param ballHealth The new health of the ball.
     */
    public void setBallHealth(int ballHealth) {
        this.ballHealth = ballHealth;
    }

    /**
     * Gets the maximum health of the ball.
     *
     * @return The maximum health of the ball.
     */
    public int getBallMaxHealth() {
        return ballMaxHealth;
    }

    /**
     * Sets the maximum health of the ball.
     *
     * @param ballMaxHealth The new maximum health of the ball.
     */
    public void setBallMaxHealth(int ballMaxHealth) {
        this.ballMaxHealth = ballMaxHealth;
    }

    /**
     * Sets the mode of the ball.
     *
     * @param mode The new mode of the ball.
     */
    public void setBallMode(Mode mode) {
        this.ballMode = mode;
        changeBallMode(mode);
    }

    /**
     * Gets the mode of the ball.
     *
     * @return The mode of the ball.
     */
    public Mode getBallMode() {
        return ballMode;
    }

    /**
     * Changes the mode of the ball.
     *
     * @param mode The new mode of the ball.
     */
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

    /**
     * Checks if the ball can break the wall.
     *
     * @return True if the ball can break the wall, false otherwise.
     */
    public boolean canBreakWall() {
        if (this.ballMode == Mode.ROCKY) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the speed of the ball.
     *
     * @return The speed of the ball.
     */
    public float getBallSpeed() {
        return ballSpeed;
    }

    /**
     * Sets the speed of the ball.
     *
     * @param ball_speed The new speed of the ball.
     */
    public void setBall_speed(float ball_speed) {
        this.ballSpeed = ball_speed;
    }
}
