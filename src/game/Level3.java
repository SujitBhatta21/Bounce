package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Level3  extends GameLevel{
    private static MyUserView view;
    private static World world;
    private Ball ball;
    private List<Collectable> collectableList = new ArrayList<Collectable>();
    private final static String platformImagePath = "assets/images/platform1.gif";
    private final String suppportBoxImagePath = "assets/images/physics/fallingBox.png";
    private static Lever lever;
    private static Portal[] portal_pair;
    private static StaticBody levelEndFinalTouch;

    public Level3(){
        //base class will create the student, professor
        super();

        world = new World();
        this.view = getView();
    }


    private void start_level_1(JFrame frame) {
        world.start();

        System.out.println("level_3 method called");

        making_world_border(world);

        // Setting up walker ball for Level2.
        ball = new Ball(world, 5, 5);
        ball.setPosition(new Vec2(ball.getXPos(), ball.getYPos()));
        ball.setBallFriction(10);
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
    public World getLevelWorld() {
        return world;
    }

    @Override
    public Ball getBall() {
        return ball;
    }
}
