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

        making_world_border(world);

        // Setting up walker ball for Level2.
        ball = new Ball(world, 0, 0);
        ball.setPosition(new Vec2(ball.getXPos(), ball.getYPos()));
        ball.setBallFriction(10);

        // Drawing game exit home...
        Shape rockballShape = new BoxShape(0.60f, 1.2f);
        StaticBody exit_home = new StaticBody(world, rockballShape);
        exit_home.setPosition(new Vec2(5,-9.5f));
        exit_home.addImage(new BodyImage("assets/images/dungeonDoor.png", 2.5f));

        // Setting end game final touch as rockball for Level1;
        levelEndFinalTouch = exit_home;

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
