package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;

public class Level1  extends GameLevel{
    private static MyUserView view;
    private static World world;
    private static Ball ball;
    private final static String platformImagePath = "assets/images/platform1.gif";

    public Level1(World world, MyUserView view){
        //base class will create the student, professor
        super(world, view);

        this.world = world;
        this.view = view;

        // Making walker ball
        ball = getBall();
        ball.setPosition(new Vec2(ball.getXPos(), ball.getYPos()));
        ball.setBallFriction(10);

        /* create level specific platforms, enemies,
           pickups, collision listeners, etc.*/

    }
    @Override
    public boolean isComplete() {
//        if (something)
//            // Clear the collectable list...
//            return true;
//        else {
//            return false;
//        }
        return false;
    }

    public void start_level_1(JFrame frame) {

        System.out.println("level_1 method called");

        // Making the moving spikes
        Spike spike1 = new Spike(world, -11.5f, -7);
        spike1.setPosition(new Vec2(spike1.getXPos(), spike1.getYPos()));

        Spike spike2 = new Spike(world, -2.5f, -7);
        spike2.setPosition(new Vec2(spike2.getXPos(), spike2.getYPos()));

        /* Making portal  */
        Portal portal_1 = new Portal(world, -10, 2);
        portal_1.setPosition(new Vec2(portal_1.getXPos(), portal_1.getYPos()));

        Portal portal_2 = new Portal(world, 8, 9);
        portal_2.setPosition(new Vec2(portal_2.getXPos(), portal_2.getYPos()));

        Portal[] portal_pair = new Portal[]{portal_1, portal_2};

        /* Making platform where character can walk in */
        // Left bottom platform
        drawBoxShape(world, 3, 0.5f, -8, -3, "visible", platformImagePath, 6*0.5f);
        // Top right platform.
        drawBoxShape(world, 3, 0.5f, 7, 3, "visible", platformImagePath, 6*0.5f);

        // Drawing rock ball on the platform.
        Shape rockballShape = new CircleShape(1);
        StaticBody rockball = new StaticBody(world, rockballShape);
        rockball.setPosition(new Vec2(-7.8f,7));
        rockball.addImage(new BodyImage("assets/images/character/rockball.png", 2));

        // Cage holding platform.
        drawBoxShape(world, 2, 0.5f, -8, 6, "visible", platformImagePath,2);

        // making lever.
        Lever lever = new Lever(world);
        lever.setPosition(new Vec2(10, -8));

        /* making a border. */
        making_world_border(world);

        /* Making keys that appears in the game */
        Collectable key1 = new Collectable(world);  // Max coin 4 for level 1.
        key1.setPosition(new Vec2(-10, -5));
        getCollectableList().add(key1);

        Collectable key2 = new Collectable(world);
        key2.setPosition(new Vec2(4, -1));
        getCollectableList().add(key2);

        Collectable key3 = new Collectable(world);
        key3.setPosition(new Vec2(10, 7));
        getCollectableList().add(key3);

        /* Initialising CollisionListener with ball */
        BallCollisions ballCollisions = new BallCollisions(world, view, ball, lever, getCollectableList(), portal_pair, rockball);
        ball.addCollisionListener(ballCollisions);

        /* Initialising StepListener */
        world.addStepListener(new PatrollerController(spike1, -11.5f, -8f));
        world.addStepListener(new PatrollerController(spike2, -7f, -3.5f));

        // Checks if a key is pressed.
        KeyboardListener k = new KeyboardListener(ball, world, frame, view);
        frame.addKeyListener(k);
    }

    @Override
    public void start_level(JFrame frame) {
        start_level_1(frame);
    }
}