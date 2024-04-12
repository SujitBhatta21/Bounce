package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Level3  extends GameLevel{
    private static MyUserView view;
    private static MyWorld world;
    private Ball ball;
    private List<Collectable> collectableList = new ArrayList<Collectable>();
    private final static String platformImagePath = "assets/images/platform1.gif";
    private final String suppportBoxImagePath = "assets/images/physics/fallingBox.png";
    private static Lever lever;
    private static Portal[] portal_pair;
    private static StaticBody levelEndFinalTouch;
    private static ArrayList<StaticBody> iceBlockageCollection = new ArrayList<>();

    public Level3(){
        super();

        world = new MyWorld();
        this.view = getView();
    }


    private void start_level_3(JFrame frame) {
        world.start();

        // Making a debugging view. Used for debugging.
        new DebugViewer(world, 800, 600);

        System.out.println("level_3 method called");

        making_world_border(world);

        // Setting up walker ball for Level2.
        ball = new Ball(world, -18f, 0);
        ball.setPosition(new Vec2(ball.getXPos(), ball.getYPos()));
        ball.setBallFriction(10);

        // Drawing rock ball on the platform.
        Shape rockballShape = new CircleShape(1);
        StaticBody volley = new StaticBody(world, rockballShape);
        volley.setPosition(new Vec2(18.5f,-9.5f));
        volley.addImage(new BodyImage("assets/images/character/beach_ball.png", 6));

        // Setting end game final touch as rockball for Level1;
        levelEndFinalTouch = volley;

        // Making box tower near volley.
        float x = 15.5f;
        float y = -9.5f;
        for (int i = 0; i < 10; i++) {
            drawBoxShape(world, 1f, 1f, x, y, "visible", "assets/images/physics/fallingBox.png",2*1f);
            y += 2;
        }
        // Making ice boxes...
        for (int i = 0; i < 3; i++) {
            StaticBody b = drawBoxShape(world, 1f, 1f, x, y, "visible", "assets/images/physics/IceBox.png",2*1f);
            iceBlockageCollection.add(b);
            y += 2;
        }

        Collectable key2 = new Collectable(world, 5, -7);
        collectableList.add(key2);

        Mole mole = new Mole(world, 0, -9.5f);

        /* Initialising CollisionListener with ball */
        BallCollisions ballCollisions = new BallCollisions();
        ball.addCollisionListener(ballCollisions);

        /* Initialising StepListener to mole */
        world.addStepListener(new PatrollerController(mole, -3f, 3f));

        // Checks if a key is pressed.
        KeyboardListener k = new KeyboardListener(ball, world, frame, view);
        frame.addKeyListener(k);
    }

    public ArrayList<StaticBody> getIceBlockageCollection() {
        return iceBlockageCollection;
    }

    public Lever getLever() {
        return lever;
    }

    public Portal[] getPortal() {
        return portal_pair;
    }

    @Override
    public StaticBody getLevelEndFinalTouch() {
        return levelEndFinalTouch;
    }

    @Override
    public List<Collectable> getCollectableList() {
        return collectableList;
    }

    @Override
    public void startLevel(JFrame frame) {
        start_level_3(frame);
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
