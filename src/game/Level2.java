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
        System.out.println(world);
        this.view = getView();

    }


    private void start_level_2(JFrame frame) {
        System.out.println("Level 2 is initialised...");

        world.start();

        view.setTimeLeft(100);

        // Game.initialiseGame(frame);

        // Setting up Walker ball start position in this level.
        this.ball = new Ball(world, 5, 10);
        // New start coordinates.
        this.ball.setPosition(new Vec2(ball.getXPos(), ball.getYPos()));
        this.ball.setBallFriction(10);

        making_world_border(world);

        // Checks if a key is pressed.
        KeyboardListener k = new KeyboardListener(ball, world, frame, view);
        frame.addKeyListener(k);
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
