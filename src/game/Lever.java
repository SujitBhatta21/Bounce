package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * Lever is a class that extends StaticBody and represents a lever in the game.
 *
 * @author      Sujit Bhatta, sujit.bhatta@city.ac.uk
 * @version     1.0
 * @since       1.0
 */
public class Lever extends StaticBody {
    /**
     * The world in which the game takes place.
     */
    private MyWorld world;

    /**
     * The count of the lever.
     */
    private int lever_count = 0;

    /**
     * The state of the lever.
     */
    private static String leverState = "off";

    /**
     * The shape of the lever.
     */
    private static Shape leverShape = new BoxShape(1, 1);

    /**
     * The image of the lever when it's off.
     */
    private static BodyImage lever_off = new BodyImage("assets/images/physics/lever_off.png", 2);

    /**
     * The image of the lever when it's on.
     */
    private static BodyImage lever_on = new BodyImage("assets/images/physics/lever_on.png", 13);

    /**
     * The constructor for the Lever class.
     * @param world The world in which the game takes place.
     */
    Lever(MyWorld world) {
        super(world, leverShape);
        this.world = world;
        addImage(lever_off);
    }

    /**
     * Gets the state of the lever.
     * @return The state of the lever.
     */
    public String getLeverState() {
        return leverState;
    }

    /**
     * Sets the state of the lever.
     * @param leverState The new state of the lever.
     */
    public void setLeverState(String leverState) {
        this.leverState = leverState;
        removeAllImages();    // Removes previous lever image.
        changeImage();        // Sets new image based on lever state.
    }

    /**
     * Changes the image of the lever based on its state.
     */
    public void changeImage() {
        if (leverState == "off") {
            addImage(lever_off);
        }
        else {

            addImage(lever_on);
            // To make sure only 1 boc falls.
            if (lever_count < 1) {
                // Change the instance later.
                if (Game.getLevel() instanceof Level2) {
                    for (StaticBody b: Level2.getBlockageCollection()) {
                        // Produce some desctuction sound before destroying.
                        b.destroy();
                    }
                }
                else if (Game.getLevel() instanceof Level1) {
                    draw_falling_box(world, 7f, 25f);
                }
            }
        }
    }

    /**
     * Draws a falling box in the world at the specified position.
     * @param world The world in which the game takes place.
     * @param x_pos The x position of the box.
     * @param y_pos The y position of the box.
     */
    public void draw_falling_box(World world, float x_pos, float y_pos) {
        Shape rodShape = new BoxShape(1.5f, 1.5f);
        DynamicBody rod = new DynamicBody(world, rodShape);
        rod.setPosition(new Vec2(x_pos, y_pos));
        rod.addImage(new BodyImage("assets/images/physics/fallingBox.png", 2*1.5f));

        // Add a collision listener to the rod
        rod.addCollisionListener(new CollisionListener() {
            @Override
            public void collide(CollisionEvent collisionEvent) {
                // Check if the rod has collided with the ground
                if (collisionEvent.getOtherBody() instanceof StaticBody) {
                    rod.setGravityScale(500);
                }
            }
        });

        lever_count++;
    }

}
