package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Level1  extends GameLevel{
    private static MyUserView view;
    private static World world;
    private Ball ball;
    private List<Collectable> collectableList = new ArrayList<Collectable>();
    private final static String platformImagePath = "assets/images/platform1.gif";
    private final String suppportBoxImagePath = "assets/images/physics/fallingBox.png";
    private static Lever lever;
    private static Portal[] portal_pair;
    private static StaticBody levelEndFinalTouch;

    public Level1(){
        //base class will create the student, professor
        super();

        world = new World();
        this.view = getView();
    }


    private void start_level_1(JFrame frame) {
        world.start();

        System.out.println("level_1 method called");

        // Setting up walker ball for Level1.
        ball = new Ball(world, -18, 0);
        ball.setPosition(new Vec2(ball.getXPos(), ball.getYPos()));
        ball.setBallFriction(10);

        // Making the moving spikes
        float x_pos = -15;
        for (int i = 0; i < 27; i++) {
            StaticSpike spike1 = new StaticSpike(world, x_pos, -9.5f);
            spike1.setPosition(new Vec2(spike1.getXPos(), spike1.getYPos()));
            x_pos = x_pos + 1.2f;
        }


        /* Making platform where character can walk in */
        // Left bottom platform
        drawBoxShape(world, 4f, 0.5f, 15, -4.5f, "visible", platformImagePath, 8*0.5f);
        // Bottom right platform.
        drawBoxShape(world, 3, 0.5f, -12, -1.5f, "visible", platformImagePath, 6*0.5f);
        // Support box on support platform.
        drawBoxShape(world, 1.5f, 1.5f, 1, 3.5f, "visible", suppportBoxImagePath, 2*1.5f);
        // Middle support platform.
        drawBoxShape(world, 4f, 0.5f, 0, 2, "visible", platformImagePath, 8*0.5f);

        // Drawing game exit home...
        Shape rockballShape = new CircleShape(1);
        StaticBody exit_home = new StaticBody(world, rockballShape);
        exit_home.setPosition(new Vec2(11,8.3f));
        exit_home.addImage(new BodyImage("assets/images/character/rockball.png", 2));

        // Setting end game final touch as rockball for Level1;
        levelEndFinalTouch = exit_home;

//        // making lever.
//        lever = new Lever(world);
//        lever.setPosition(new Vec2(15f, -9.5f));

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

        // Checks if a key is pressed.
        KeyboardListener k = new KeyboardListener(ball, world, frame, view);
        frame.addKeyListener(k);
    }

    public static Lever getLever() {
        return lever;
    }

    public static Portal[] getPortal() {
        return portal_pair;
    }

    public static StaticBody getLevelEndFinalTouch() {
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
    public World getLevelWorld() {
        return world;
    }

    @Override
    public Ball getBall() {
        return ball;
    }
}
