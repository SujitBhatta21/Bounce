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

    public Level2() {
        super();

        world = new World();
        this.view = getView();

        // Setting up Walker ball start position in this level.
        ball = new Ball(world, 5, 10);
        // New start coordinates.
        ball.setPosition(new Vec2(ball.getXPos(), ball.getYPos()));
        ball.setBallFriction(10);
    }


    private void start_level_2(JFrame frame) {
        System.out.println("Level 2 is initialised...");

        making_world_border(world);
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
