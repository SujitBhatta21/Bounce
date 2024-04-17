package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Level4 is a class that extends GameLevel and represents the fourth level of the game.
 *
 * @author      Sujit Bhatta, sujit.bhatta@city.ac.uk
 * @version     1.0
 * @since       1.0
 */
public class Level4 extends GameLevel {
    /**
     * The view of the user.
     */
    private static MyUserView view;

    /**
     * The world in which the game takes place.
     */
    private static MyWorld world;

    /**
     * The ball used in the game.
     */
    private Ball ball;

    /**
     * The list of collectable items in the game.
     */
    private List<Collectable> collectableList = new ArrayList<Collectable>();

    /**
     * The list of moving spikes in the game.
     */
    private List<Spike> movingSpikes = new ArrayList<>();

    /**
     * The transparent color used in the game.
     */
    private static final Color transparent_colour = new Color(0, 0, 0, 0);

    /**
     * The path to the image of the platform.
     */
    private final static String platformImagePath = "assets/images/platform1.gif";

    /**
     * The path to the image of the support box.
     */
    private final String suppportBoxImagePath = "assets/images/physics/fallingBox.png";

    /**
     * The lever used in the game.
     */
    private static Lever lever;

    /**
     * The pair of portals used in the game.
     */
    private static Portal[] portal_pair;

    /**
     * The final touch of the level end.
     */
    private static StaticBody levelEndFinalTouch;

    /**
     * The constructor for the Level4 class.
     */
    public Level4() {
        //base class will create the student, professor
        super();

        world = new MyWorld();
        this.view = getView();
    }

    /**
     * Starts the fourth level of the game.
     * @param frame The JFrame in which the game is displayed.
     */
    private void start_level_4(JFrame frame) {
        world.start();

        System.out.println("level_4 method called");

        //optional: draw a 1metre grid over the view
        //view.setGridResolution(1);

        // Making a debugging view. Used for debugging.
        //new DebugViewer(world, 800, 600);

        making_L4_world(world);

        // Setting up walker ball for Level2.
        ball = new Ball(world, 0, -9.5f);
        ball.setPosition(new Vec2(ball.getXPos(), ball.getYPos()));
        ball.setBallFriction(10);

        // Putting main villian hypnotiser in the game.
        Hypnotiser finalBoss = new Hypnotiser(world, -5, 8);
        levelEndFinalTouch = finalBoss;

        Collectable key2 = new Collectable(world, 0, -7);
        collectableList.add(key2);

        key2.setMax_coin_count(collectableList.size());

        /* Initialising CollisionListener with ball */
        BallCollisions ballCollisions = new BallCollisions();
        ball.addCollisionListener(ballCollisions);

        // Creating a tracker for ball.
        world.addStepListener(new Tracker(view, finalBoss));

        // Checks if a key is pressed.
        KeyboardListener k = new KeyboardListener(ball, world, frame, view);
        frame.addKeyListener(k);
    }

    /**
     * Makes the world for the fourth level of the game.
     * @param world The world in which the game takes place.
     */
    private void making_L4_world(World world) {
        // Making the moving spikes right
        float y_pos = -20;
        for (int i = 0; i < 30; i++) {
            Spike spike1 = new Spike(this.getLevelWorld(), 54, y_pos);
            y_pos = y_pos + 2*0.60f;
        }
        // Making the moving spikes left
        y_pos = -20;
        for (int i = 0; i < 30; i++) {
            Spike spike1 = new Spike(this.getLevelWorld(), -19, y_pos);
            movingSpikes.add(spike1);
            y_pos = y_pos + 2*0.60f;
        }

        // Making black giant box attached to spike.
        StaticBody blackLeft = drawBoxShape(world, 20f, 15f, 2*-20f, 0, "no image", "assets/images/physics/fallingBox.png", 2*1f);


        // Adding step listener for moving spike, hypnotiser and moving static wall.
        this.getLevelWorld().addStepListener(new StepListener() {
            @Override
            public void preStep(StepEvent e) {
                // Nothing to do here
            }

            @Override
            public void postStep(StepEvent e) {
                // Move each spike to the left by __ unit
                float movement_speed = 0.03f;
                for (Spike spike : movingSpikes) {
                    Vec2 position = spike.getPosition();
                    spike.setPosition(new Vec2(position.x + movement_speed, position.y));
                }

                // Moving Hypnotiser like the spike wall
                Vec2 position = levelEndFinalTouch.getPosition();
                if (position.x <= 50) {
                    levelEndFinalTouch.setPosition(new Vec2(position.x + movement_speed, position.y));
                }

                position = blackLeft.getPosition();
                blackLeft.setPosition(new Vec2(position.x + movement_speed, position.y));
            }
        });

        // making a ground platform
        Shape down = new BoxShape(100f, 0.5f);
        StaticBody ground = new StaticBody(world, down);
        ground.setFillColor(transparent_colour);
        ground.setLineColor(transparent_colour);
        ground.setPosition(new Vec2(0f, -11f));

        // making right border
        drawBoxShape(world, 20f, 15f, 2*36.5f, 0, "no image", "assets/images/physics/fallingBox.png", 2*1f);

        // making top border
        Shape top = new BoxShape(100f, 0.5f);
        StaticBody top_border = new StaticBody(world, top);
        top_border.setPosition(new Vec2(0, 15f));

        // Making pyramid platform
        float x = 15.5f;
        float y = -9.5f;
        int numBoxes = 5; // Number of boxes in the base of the pyramid

        for (int i = numBoxes; i > 0; i--) {
            // Calculate the starting x position for this row of the pyramid
            float rowStartX = x - i;

            for (int j = 0; j < i; j++) {
                drawBoxShape(world, 1f, 1f, rowStartX + 2*j, y, "visible", "assets/images/physics/fallingBox.png", 2*1f);
            }

            // Move up for the next row
            y += 2;
        }

        // Lower second stage small platform.
        drawBoxShape(world, 1f, 1f, 28f, -8.5f, "visible", "assets/images/physics/fallingBox.png", 2*1f);
        drawBoxShape(world, 1f, 1f, 31f, -6.5f, "visible", "assets/images/physics/fallingBox.png", 2*1f);

        // Making patrolling second platform at the end moving up and down.
        MovingPlatform p1 = new MovingPlatform(this.world, 36, 0f);

        // Don't need to pass anything in left and right for this.
        world.addStepListener(new PatrollerController(p1, 0f, 0f));

        // Spring in level4.
        Spring spring = new Spring(this.world, 48,-9.5f);
    }

    /**
     * Gets the lever used in the game.
     * @return The lever used in the game.
     */
    public Lever getLever() {
        return lever;
    }

    /**
     * Gets the pair of portals used in the game.
     * @return The pair of portals used in the game.
     */
    public Portal[] getPortal() {
        return portal_pair;
    }

    /**
     * Gets the final touch of the level end.
     * @return The final touch of the level end.
     */
    public StaticBody getLevelEndFinalTouch() {
        return levelEndFinalTouch;
    }

    /**
     * Gets the list of collectable items in the game.
     * @return The list of collectable items in the game.
     */
    @Override
    public List<Collectable> getCollectableList() {
        return collectableList;
    }

    /**
     * Starts the level.
     * @param frame The JFrame in which the game is displayed.
     */
    @Override
    public void startLevel(JFrame frame) {
        start_level_4(frame);
    }

    /**
     * Stops the level.
     */
    @Override
    public void stopLevel() {
        this.world.stop();
    }

    /**
     * Gets the world in which the game takes place.
     * @return The world in which the game takes place.
     */
    @Override
    public MyWorld getLevelWorld() {
        return world;
    }

    /**
     * Gets the ball used in the game.
     * @return The ball used in the game.
     */
    @Override
    public Ball getBall() {
        return ball;
    }
}
