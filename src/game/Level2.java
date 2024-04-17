package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents Level2 in the game.
 *
 * @author      Sujit Bhatta, sujit.bhatta@city.ac.uk
 * @version     1.0
 * @since       1.0
 */
public class Level2 extends GameLevel {
    /**
     * The view of the user.
     */
    private static MyUserView view;

    /**
     * The world in which the game is taking place.
     */
    private final MyWorld world;

    /**
     * The ball that the user controls.
     */
    private static Ball ball;

    /**
     * The pair of portals in the level.
     */
    private static Portal[] portal_pair;

    /**
     * The list of collectable items in the level.
     */
    private final ArrayList<Collectable> collectableList = new ArrayList<>();

    /**
     * The final touch of the level end.
     */
    private static StaticBody levelEndFinalTouch;

    /**
     * The path to the image of the platform.
     */
    private final static String platformImagePath = "assets/images/platform/platform1.gif";

    /**
     * The collection of blockages in the level.
     */
    private static final List<StaticBody> blockageCollection = new ArrayList<>();

    /**
     * The lever in the level.
     */
    private Lever lever;

    /**
     * Constructs a Level2.
     */
    public Level2() {
        super();

        world = new MyWorld();
        this.view = getView();
    }

    /**
     * Starts level 2.
     *
     * @param  frame The frame in which the game is displayed.
     */
    private void start_level_2(JFrame frame) {
        System.out.println("Level 2 is initialised...");

        // Making a debugging view. Used for debugging.
        new DebugViewer(world, 800, 600);

        world.start();

        view.setTimeLeft(100);

        making_world_border(world);

        // Setting up walker ball for Level2.
        ball = new Ball(world, -18, 0);
        ball.setPosition(new Vec2(ball.getXPos(), ball.getYPos()));
        // ball.setBallFriction(10);

        Spring spring1 = new Spring(world, -15, -9.5f);
        spring1.setPosition(new Vec2(spring1.getXPos(), spring1.getYPos()));

        // Making the linear spikes.
        float x_pos = -13;
        for (int i = 0; i < 23; i++) {
            Spike spike1 = new Spike(world, x_pos, -9.5f);
            spike1.setPosition(new Vec2(spike1.getXPos(), spike1.getYPos()));
            x_pos = x_pos + 1.2f;
        }

        /* Making platform where character can walk in */
        // 1st bounce touch platform.
        MovingPlatform p1 = new MovingPlatform(world, -12, 0f);
        MovingPlatform p2 = new MovingPlatform(world, 0, 6f);

        // Drawing exit home.
        StaticBody exit_home = drawBoxShape(world, 0.60f, 1.2f, 17.5f, -9.5f, "visible", "assets/images/dungeonDoor.png",2.5f);

        // Setting end game final touch as exit home for Level2;
        levelEndFinalTouch = exit_home;

        /* Making portal  */
        Portal portal_1 = new Portal(world, 5, -7);
        portal_1.setPosition(new Vec2(portal_1.getXPos(), portal_1.getYPos()));

        Portal portal_2 = new Portal(world, 15, 10);
        portal_2.setPosition(new Vec2(portal_2.getXPos(), portal_2.getYPos()));

        portal_pair = new Portal[]{portal_1, portal_2};

        // Making blockage on top of exit home.
        float x = 15.5f;
        float y = -10f;
        for (int i = 0; i < 5; i++) {
            StaticBody b = drawBoxShape(world, 0.5f, 0.5f, x, y, "visible", "assets/images/physics/fallingBox.png",2*0.5f);
            blockageCollection.add(b);
            y += 1;
        }
        x = x + 1;
        y = y - 1;
        for (int i = 0; i < 4; i++) {
            StaticBody b = drawBoxShape(world, 0.5f, 0.5f, x, y, "visible", "assets/images/physics/fallingBox.png",2*0.5f);
            blockageCollection.add(b);
            x += 1;
        }
        y = y - 1;
        x = x - 1;
        for (int i = 0; i < 4; i++) {
            StaticBody b = drawBoxShape(world, 0.5f, 0.5f, x, y, "visible", "assets/images/physics/fallingBox.png",2*0.5f);
            blockageCollection.add(b);
            y -= 1;
        }

        // making lever.
        lever = new Lever(world);
        lever.setPosition(new Vec2(-10f, 8.5f));

        // Lever holding platform.
        drawBoxShape(world, 2, 0.5f, -10, 7.5f, "visible", platformImagePath,2);

        /* making a border. */
        making_world_border(world);

        /* Making keys that appears in the game */
        Collectable key1 = new Collectable(world, -15, 6);  // Max coin 4 for level 1.
        collectableList.add(key1);

        Collectable key2 = new Collectable(world, 4, -1);
        collectableList.add(key2);

        Collectable key3 = new Collectable(world, -3, 10);
        collectableList.add(key3);

        /* Initialising CollisionListener with ball */
        BallCollisions ballCollisions = new BallCollisions();
        ball.addCollisionListener(ballCollisions);

        /* Initialising StepListener */
        world.addStepListener(new PatrollerController(p1, -12f, -3f));
        world.addStepListener(new PatrollerController(p2, 0f, 12f));

        // Checks if a key is pressed.
        KeyboardListener k = new KeyboardListener(ball, world, frame, view);
        frame.addKeyListener(k);
    }

    /**
     * Returns the collection of blockages in the level.
     *
     * @return The collection of blockages in the level.
     */
    public static List<StaticBody> getBlockageCollection() {
        return blockageCollection;
    }

    /**
     * Returns the final touch of the level end.
     *
     * @return The final touch of the level end.
     */
    public StaticBody getLevelEndFinalTouch() {
        return levelEndFinalTouch;
    }

    @Override
    /**
     * Starts the level.
     *
     * @param  frame The frame in which the game is displayed.
     */
    public void startLevel(JFrame frame) {
        start_level_2(frame);
    }

    @Override
    /**
     * Stops the level.
     */
    public void stopLevel() {
        // This method is intentionally left blank.
    }

    @Override
    /**
     * Returns the list of collectable items in the level.
     *
     * @return The list of collectable items in the level.
     */
    public List<Collectable> getCollectableList() {
        return collectableList;
    }

    @Override
    /**
     * Returns the world of the level.
     *
     * @return The world of the level.
     */
    public MyWorld getLevelWorld() {
        return world;
    }

    @Override
    /**
     * Returns the ball that the user controls.
     *
     * @return The ball that the user controls.
     */
    public Ball getBall() {
        return ball;
    }

    /**
     * Returns the lever in the level.
     *
     * @return The lever in the level.
     */
    public Lever getLever() {
        return lever;
    }

    /**
     * Returns the pair of portals in the level.
     *
     * @return The pair of portals in the level.
     */
    public Portal[] getPortal() {
        return portal_pair;
    }
}
