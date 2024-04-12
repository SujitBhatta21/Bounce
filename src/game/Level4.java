// Demo code.

package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Level4  extends GameLevel{
    private static MyUserView view;
    private static MyWorld world;
    private Ball ball;
    private List<Collectable> collectableList = new ArrayList<Collectable>();
    private final static String platformImagePath = "assets/images/platform1.gif";
    private final String suppportBoxImagePath = "assets/images/physics/fallingBox.png";
    private static Lever lever;
    private static Portal[] portal_pair;
    private static StaticBody levelEndFinalTouch;

    public Level4(){
        //base class will create the student, professor
        super();

        world = new MyWorld();
        this.view = getView();
    }


    private void start_level_4(JFrame frame) {
        world.start();

        System.out.println("level_4 method called");

        making_world_border(world);

        // Setting up walker ball for Level2.
        ball = new Ball(world, 0, 10);
        ball.setPosition(new Vec2(ball.getXPos(), ball.getYPos()));
        ball.setBallFriction(10);

        // Drawing game exit home...
        Shape rockballShape = new BoxShape(0.60f, 1.2f);
        StaticBody exit_home = new StaticBody(world, rockballShape);
        exit_home.setPosition(new Vec2(0,-9.5f));
        exit_home.addImage(new BodyImage("assets/images/dungeonDoor.png", 2.5f));

        // Setting end game final touch as rockball for Level1;
        levelEndFinalTouch = exit_home;

        Collectable key2 = new Collectable(world, 0, -7);
        collectableList.add(key2);

        key2.setMax_coin_count(collectableList.size());

        /* Initialising CollisionListener with ball */
        BallCollisions ballCollisions = new BallCollisions();
        ball.addCollisionListener(ballCollisions);

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
        start_level_4(frame);
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


