package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Level1  extends GameLevel{
    private static MyUserView view;
    private static MyWorld world;
    private Ball ball;
    private final List<Collectable> collectableList = new ArrayList<>();
    private final static String platformImagePath = "assets/images/platform/platform1.gif";
    private final String supportBoxImagePath = "assets/images/physics/fallingBox.png";
    private static Lever lever;
    private static Portal[] portal_pair;
    private static StaticBody levelEndFinalTouch;

    public Level1(){
        //base class will create the student, professor
        super();

        world = new MyWorld();
        this.view = getView();
    }


    private void start_level_1(JFrame frame) {
        world.start();

        System.out.println("level_1 method called");

        // Setting up walker ball for Level1.
        ball = new Ball(world, 0, 0);
        ball.setPosition(new Vec2(ball.getXPos(), ball.getYPos()));
        ball.setBallFriction(10);

        // Making the moving spikes
        Spike spike1 = new Spike(world, -12f, -9.5f);
        spike1.setPosition(new Vec2(spike1.getXPos(), spike1.getYPos()));

        Spike spike2 = new Spike(world, -10f, -9.5f);
        spike2.setPosition(new Vec2(spike2.getXPos(), spike2.getYPos()));

        /* Making portal  */
        Portal portal_1 = new Portal(world, 15, 0);
        portal_1.setPosition(new Vec2(portal_1.getXPos(), portal_1.getYPos()));

        Portal portal_2 = new Portal(world, -18, 5);
        portal_2.setPosition(new Vec2(portal_2.getXPos(), portal_2.getYPos()));

        portal_pair = new Portal[]{portal_1, portal_2};

        /* Making platform where character can walk in */
        // Left bottom platform
        drawBoxShape(world, 4f, 0.5f, 15, -4.5f, "visible", platformImagePath, 8*0.5f);
        // Bottom right platform.
        drawBoxShape(world, 3, 0.5f, -12, -1.5f, "visible", platformImagePath, 6*0.5f);
        // Support box on support platform.
        drawBoxShape(world, 1.5f, 1.5f, 1, 3.5f, "visible", supportBoxImagePath, 2*1.5f);
        // Middle support platform.
        drawBoxShape(world, 4f, 0.5f, 0, 2, "visible", platformImagePath, 8*0.5f);

        // Drawing rock ball on the platform.
        Shape rockballShape = new CircleShape(1);
        StaticBody rockball = new StaticBody(world, rockballShape);
        rockball.setPosition(new Vec2(11,8.3f));
        rockball.addImage(new BodyImage("assets/images/character/rockball.png", 2));

        // Setting end game final touch as rockball for Level1;
        levelEndFinalTouch = rockball;

        // Cage holding platform.
        drawBoxShape(world, 2, 0.5f, 11, 7.5f, "visible", platformImagePath,2);

        // making lever.
        lever = new Lever(world);
        lever.setPosition(new Vec2(15f, -9.5f));

        /* making a border. */
        making_world_border(world);

        /* Making keys that appears in the game */
        Collectable key1 = new Collectable(world, -14, -5);  // Max coin 4 for level 1.
        collectableList.add(key1);

        Collectable key2 = new Collectable(world, 4, -1);
        collectableList.add(key2);

        Collectable key3 = new Collectable(world, -3, 10);
        collectableList.add(key3);

        /* Initialising CollisionListener with ball */
        BallCollisions ballCollisions = new BallCollisions();
        ball.addCollisionListener(ballCollisions);

        /* Initialising StepListener */
        world.addStepListener(new PatrollerController(spike1, -18f, -12f));
        world.addStepListener(new PatrollerController(spike2, -10f, -4f));

        // Checks if a key is pressed.
        KeyboardListener k = new KeyboardListener(ball, world, frame, view);
        frame.addKeyListener(k);
    }

    public Lever getLever() {
        return lever;
    }

    public Portal[] getPortal() {
        return portal_pair;
    }

    public StaticBody getLevelEndFinalTouch() {
        return levelEndFinalTouch;
    }

    @Override
    public List<Collectable> getCollectableList() {
        return collectableList;
    }

    @Override
    public void startLevel(JFrame frame) {
        start_level_1(frame);
    }

    @Override
    public void stopLevel() {
        this.world.stop();
    }

    @Override
    public MyWorld getLevelWorld() {
        return world;
    }

    @Override
    public Ball getBall() {
        return ball;
    }
}

