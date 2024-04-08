package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Level2 extends GameLevel {
    private static MyUserView view;
    private World world;
    private static Ball ball;
    private List<Collectable> collectableList = new ArrayList<Collectable>();
    private static StaticBody levelEndFinalTouch;
    private final static String platformImagePath = "assets/images/platform1.gif";
    private final String suppportBoxImagePath = "assets/images/physics/fallingBox.png";


    public Level2() {
        super();

        world = new World();
        System.out.println(world);
        this.view = getView();

    }


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
        ball.setBallFriction(10);

        Spring spring1 = new Spring(world, -15, -9.5f);
        spring1.setPosition(new Vec2(spring1.getXPos(), spring1.getYPos()));

        // Making the moving spikes
        float x_pos = -13;
        for (int i = 0; i < 25; i++) {
            StaticSpike spike1 = new StaticSpike(world, x_pos, -9.5f);
            spike1.setPosition(new Vec2(spike1.getXPos(), spike1.getYPos()));
            x_pos = x_pos + 1.2f;
        }


        /* Making platform where character can walk in */
        // 1st bounce touch platform.
        MovingPlatform p1 = new MovingPlatform(world, -12, -1.5f);
        MovingPlatform p2 = new MovingPlatform(world, 0, 6f);

        // Drawing game exit home...
        Shape rockballShape = new BoxShape(0.60f, 1.2f);
        StaticBody exit_home = new StaticBody(world, rockballShape);
        exit_home.setPosition(new Vec2(18f,-9.5f));
        exit_home.addImage(new BodyImage("assets/images/dungeonDoor.png", 2.5f));

        // Setting end game final touch as rockball for Level1;
        levelEndFinalTouch = exit_home;

        // making lever.
        Lever lever = new Lever(world);
        lever.setPosition(new Vec2(15f, -9.5f));

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
        world.addStepListener(new PatrollerController(p2, 3f, 12f));

        // Checks if a key is pressed.
        KeyboardListener k = new KeyboardListener(ball, world, frame, view);
        frame.addKeyListener(k);
    }

    public static StaticBody getLevelEndFinalTouch() {
        return levelEndFinalTouch;
    }

    @Override
    public void startLevel(JFrame frame) {
        start_level_2(frame);
    }

    @Override
    public void stopLevel() {

    }

    @Override
    public List<Collectable> getCollectableList() {
        return collectableList;
    }

    @Override
    public World getLevelWorld() {
        return world;
    }

    @Override
    public Ball getBall() {
        return ball;
    }
}
