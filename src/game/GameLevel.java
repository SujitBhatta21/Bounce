package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This abstract class represents a level in the game.
 * @author      Sujit Bhatta, sujit.bhatta@city.ac.uk
 * @version     1.0
 * @since       1.0
 */
public abstract class GameLevel extends World {
    /**
     * The view of the user.
     */
    private static MyUserView view;

    /**
     * The world in which the game is played.
     */
    private static MyWorld world;

    /**
     * The frame of the game.
     */
    private JFrame frame;

    /**
     * The transparent color used in the game.
     */
    private final static Color transparent_colour = new Color(0, 0, 0, 0);

    /**
     * Constructor for the GameLevel class.
     */
    public GameLevel() {
        this.world = Game.getWorld();
        this.view = Game.getView();
    }

    /**
     * Getter for the view of the user.
     * @return The view of the user.
     */
    public static MyUserView getView() {
        return view;
    }

    /**
     * Setter for the world of the game level.
     * @param  world The new world of the game level.
     */
    public void setWorldGameLevel(MyWorld world) {
        this.world = world;
    };

    /**
     * This method draws a box shape in the world.
     * @param  world The world in which the box is drawn.
     * @param  halfWidth The half width of the box.
     * @param  halfHeight The half height of the box.
     * @param  x The x-coordinate of the box.
     * @param  y The y-coordinate of the box.
     * @param  state The state of the box.
     * @param  imagePath The path of the image for the box.
     * @param  imageHeight The height of the image for the box.
     * @return The box that was drawn.
     */
    public static StaticBody drawBoxShape(World world, float halfWidth, float halfHeight, float x, float y, String state, String imagePath,  float imageHeight) {
        StaticBody platform;
        if (state.equals("invisible")) {
            Shape platformShape = new BoxShape(halfWidth, halfHeight);
            platform = new StaticBody(world, platformShape);
            platform.setFillColor(transparent_colour);
            platform.setLineColor(transparent_colour);
            platform.setPosition(new Vec2(x, y));
        } else if (state.equals("no image")) {
            Shape platformShape = new BoxShape(halfWidth, halfHeight);
            platform = new StaticBody(world, platformShape);
            platform.setPosition(new Vec2(x, y));
        } else {
            Shape platformShape = new BoxShape(halfWidth, halfHeight);
            platform = new StaticBody(world, platformShape);
            platform.setPosition(new Vec2(x, y));
            platform.addImage(new BodyImage(imagePath, imageHeight));
        }
        return platform;
    }

    /**
     * This method creates the borders of the world.
     * @param  world The world in which the borders are created.
     */
    public static void making_world_border(World world) {
        // making left border
        Shape left = new BoxShape(0.5f, 15f);
        StaticBody left_border = new StaticBody(world, left);
        left_border.setFillColor(new Color(0, 10,10));
        left_border.setPosition(new Vec2(-20.5f, 0));
        // drawBoxShape(world, 0.5f, 12.5f, -13, 0, "invisible", "", 0);

        // making a ground platform
        Shape down = new BoxShape(30f, 0.5f);
        StaticBody ground = new StaticBody(world, down);
        ground.setFillColor(transparent_colour);
        ground.setLineColor(transparent_colour);
        ground.setPosition(new Vec2(0f, -11f));

        // making right border
        Shape right = new BoxShape(0.5f, 15f);
        StaticBody right_border = new StaticBody(world, right);
        right_border.setPosition(new Vec2(20.5f, 0));

        // making top border
        if (!(Game.getLevel() instanceof Level1)) {
            Shape top = new BoxShape(30f, 0.5f);
            StaticBody top_border = new StaticBody(world, top);
            top_border.setPosition(new Vec2(0, 15f));
        }
    }

    /**
     * This method starts the level.
     * @param  frame The frame in which the level is started.
     */
    public abstract void startLevel(JFrame frame);

    /**
     * This method stops the level.
     */
    public abstract void stopLevel();

    /**
     * This method gets the list of collectables in the level.
     * @return The list of collectables in the level.
     */
    public abstract List<Collectable> getCollectableList();

    /**
     * This method gets the world of the level.
     * @return The world of the level.
     */
    public abstract MyWorld getLevelWorld() ;

    /**
     * This method gets the ball in the level.
     * @return The ball in the level.
     */
    public abstract Ball getBall();

    /**
     * This method gets the portals in the level.
     * @return The portals in the level.
     */
    public abstract Portal[] getPortal();

    /**
     * This method gets the lever in the level.
     * @return The lever in the level.
     */
    public abstract Lever getLever();

    /**
     * This method gets the final touch of the level end.
     * @return The final touch of the level end.
     */
    public abstract StaticBody getLevelEndFinalTouch();
}