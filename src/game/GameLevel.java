package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GameLevel extends World {
    private static Ball ball;
    private static MyUserView view;
    private static World world;
    private JFrame frame;
    private final static Color transparent_colour = new Color(0, 0, 0, 0);
    private final static List<Collectable> collectableList = new ArrayList<Collectable>();

    public GameLevel(World world, MyUserView view){
        ball = new Ball(world, 0, 0);
        this.world = world;
        this.view = view;
    }

    public static World getWorld() {
        return world;
    }
    public static MyUserView getView() {
        return view;
    }

    public static Ball getBall() {
        return ball;
    }

    public static void drawBoxShape(World world, float halfWidth, float halfHeight, float x, float y, String state, String imagePath,  float imageHeight) {
        if (state.equals("invisible")) {
            Shape platformShape = new BoxShape(halfWidth, halfHeight);
            StaticBody platform = new StaticBody(world, platformShape);
            platform.setFillColor(transparent_colour);
            platform.setLineColor(transparent_colour);
            platform.setPosition(new Vec2(x, y));
        } else {
            Shape platformShape = new BoxShape(halfWidth, halfHeight);
            StaticBody platform = new StaticBody(world, platformShape);
            platform.setPosition(new Vec2(x, y));
            platform.addImage(new BodyImage(imagePath, imageHeight));
        }
    }

    public static void making_world_border(World world) {
        // making left border
        Shape left = new BoxShape(0.5f, 12.5f);
        StaticBody left_border = new StaticBody(world, left);
        left_border.setFillColor(new Color(0, 10,10));
        left_border.setPosition(new Vec2(-13, 0));

        // making a ground platform
        Shape down = new BoxShape(13f, 0.5f);
        StaticBody ground = new StaticBody(world, down);
        ground.setFillColor(transparent_colour);
        ground.setLineColor(transparent_colour);
        ground.setPosition(new Vec2(0f, -9.5f));

        // making right border
        Shape right = new BoxShape(0.5f, 12.5f);
        StaticBody right_border = new StaticBody(world, right);
        right_border.setPosition(new Vec2(13f, 0));

        // making top border
        /*
        Shape top = new BoxShape(WIDTH / 30, 0.5f);
        StaticBody top_border = new StaticBody(world, top);
        top_border.setPosition(new Vec2(0 , HEIGHT/37));
         */
    }

    public static List<Collectable> getCollectableList() {
        return collectableList;
    }

    public abstract boolean isComplete();
}