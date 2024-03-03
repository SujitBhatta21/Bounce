package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 * Your main game entry point
 */


public class Game {

    private static Ball ball;  // Making ball a static field.
    private static int WIDTH = 500, HEIGHT = 500;
    private static Color transparent_colour = new Color(0, 0, 0, 0);
    private static List<Collectable> collectableList = new ArrayList<Collectable>();

    private static String platformImagePath = "assets/images/platform1.gif";

    /** Initialise a new Game. */
    public Game() {
        // make an empty game world
        World world = new World();

        // make a view to look into the game world
        MyUserView view = new MyUserView(world, WIDTH, HEIGHT);

        // create a Java window (frame) and add the game view to it
        JFrame frame = new JFrame("City Game");
        frame.setSize(WIDTH, HEIGHT);
        // frame.setLayout(null);  // Set the layout manager to null.

        view.setBounds(0, 0, WIDTH, HEIGHT);
        frame.add(view);  // Add the view to the frame.

        // Testing if the order of code matters.
        initializeGame(world, frame);

        // enable the frame to quit the application when the x button is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.requestFocusInWindow();

        //optional: draw a 1-metre grid over the view
        view.setGridResolution(1);

        // start our game world simulation!
        world.start();
    }


    // Method to initialize game components
    private void initializeGame(World world, JFrame frame) {
        level_1(world, frame);

        // Making a debugging view. Used for debugging.
        new DebugViewer(world, 500, 500);
    }



    public static void level_1(World world, JFrame frame) {

        System.out.println("level_1 method called");

        // Making a Walker ball.
        ball = new Ball(world, 0, 0);
        // ball.setGravityScale(0); Uncomment this for testing character coordinates.
        ball.setPosition(new Vec2(Ball.getXPos(), Ball.getYPos()));

        // Making suspended platform
        drawBoxShape(world, 3, 0.5f, -8, -2, "visible", platformImagePath, 6*0.5f);
        // Making invisible platform
        drawBoxShape(world, 3, 0.5f, 8, 3, "invisible", platformImagePath, 6*0.5f);

        // Drawing cage on cage hodling platform.
        drawBoxShape(world, 1, 1, -8, 7, "visible", "assets/images/cage.gif", 2);
//        Shape cage_shape = new BoxShape(1, 1);
//        StaticBody cage = new StaticBody(world, cage_shape);
//        cage.setPosition(new Vec2(-8 , 7));
//        cage.addImage(new BodyImage("assets/images/cage.gif", 2));

        // Drawing rock ball inside a cage.


        // Making cage holding platform.
        drawBoxShape(world, 2, 0.5f, -8, 6, "visible", platformImagePath,2);

        // making lever.
        Lever lever = new Lever(world);
        lever.setPosition(new Vec2(10, -8));

        /* making a border. */
        making_world_border(world);

        Collectable coin1 = new Collectable(world);  // Max coin 4 for level 1.
        coin1.setPosition(new Vec2(-10, -8));
        collectableList.add(coin1);

        Collectable coin2 = new Collectable(world);
        coin2.setPosition(new Vec2(10, 0));
        collectableList.add(coin2);

        Collectable coin3 = new Collectable(world);
        coin3.setPosition(new Vec2(-8, 10));
        collectableList.add(coin3);


        BallCollisions ballCollisions = new BallCollisions(ball, lever, collectableList);
    //    BallCollisions ballWithCoin = new BallCollisions(ball, collectableList);

        ball.addCollisionListener(ballCollisions);


        // Checks if a key is pressed.
        KeyboardListener k = new KeyboardListener(ball);
        frame.addKeyListener(k);
    }


    public static void making_world_border(World world) {
        // making left border
        Shape left = new BoxShape(0.5f, HEIGHT / 30);
        StaticBody left_border = new StaticBody(world, left);
        left_border.setPosition(new Vec2(-WIDTH / 37, 0));

        // making a ground platform
        Shape down = new BoxShape(WIDTH / 30, 0.5f);
        StaticBody ground = new StaticBody(world, down);
        ground.setFillColor(transparent_colour);
        ground.setLineColor(transparent_colour);
        ground.setPosition(new Vec2(0f, - HEIGHT / 56 - 1.5f));

        // making right border
        Shape right = new BoxShape(0.5f, HEIGHT / 30);
        StaticBody right_border = new StaticBody(world, right);
        right_border.setPosition(new Vec2(WIDTH / 37, 0));

        // making top border
        /*
        Shape top = new BoxShape(WIDTH / 30, 0.5f);
        StaticBody top_border = new StaticBody(world, top);
        top_border.setPosition(new Vec2(0 , HEIGHT/37));
         */
    }

    public static void drawBoxShape(World world, float halfWidth, float halfHeight, float x, float y, String state) {
        if (state == "invisible")  {
            Shape platformShape = new BoxShape(halfWidth, halfHeight);
            StaticBody platform = new StaticBody(world, platformShape);
            platform.setFillColor(transparent_colour);
            platform.setLineColor(transparent_colour);
            platform.setPosition(new Vec2(x, y));
        }
        else {
            Shape platformShape = new BoxShape(halfWidth, halfHeight);
            StaticBody platform = new StaticBody(world, platformShape);
            platform.setPosition(new Vec2(x, y));
            // platform.addImage(new BodyImage("assets/images/platform1.gif", imageHeight));
        }
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

    public static List<Collectable> getCollectableList() {
        return collectableList;
    }

    /** Run the game. */
    public static void main(String[] args) {

        new Game();
    }
}
