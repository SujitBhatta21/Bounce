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

    private static MyUserView view;
    private static Ball ball;  // Making ball a static field.
    private static int WIDTH = 500, HEIGHT = 500;
    private final static Color transparent_colour = new Color(0, 0, 0, 0);
    private final static List<Collectable> collectableList = new ArrayList<Collectable>();

    private final static String platformImagePath = "assets/images/platform1.gif";

    /** Initialise a new Game. */
    public Game() {
        // make an empty game world
        World world = new World();

        // make a view to look into the game world
        view = new MyUserView(world, WIDTH, HEIGHT);
        view.setBounds(0, 0, WIDTH, HEIGHT);

        // create a Java window (frame) and add the game view to it
        JFrame frame = new JFrame("City Game");
        frame.setSize(WIDTH, HEIGHT);
        // frame.setLayout(null);

        initialiseGame(world, frame);

        frame.add(view);  // Add the view to the frame.

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
    public static void initialiseGame(World world, JFrame frame) {
        // Making a debugging view. Used for debugging.
        new DebugViewer(world, 500, 500);

        level_1(world, frame);
    }



    public static void level_1(World world, JFrame frame) {

        System.out.println("level_1 method called");

//        JTextField lastText = createTextField("Congratulations", 20, Color.RED);
//        lastText.setBounds(5, 5, 200, 30);
//        frame.add(lastText);

        // Making the moving spikes
        Spike spike1 = new Spike(world, -11.5f, -7);
        spike1.setPosition(new Vec2(spike1.getXPos(), spike1.getYPos()));

        Spike spike2 = new Spike(world, -2.5f, -7);
        spike2.setPosition(new Vec2(spike2.getXPos(), spike2.getYPos()));

        // Making a Walker ball.
        ball = new Ball(world, 0, 0);
        ball.setPosition(new Vec2(ball.getXPos(), ball.getYPos()));
        ball.setBallFriction(10);

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
        drawBoxShape(world, 3, 0.5f, 8, 3, "visible", platformImagePath, 6*0.5f);

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
        collectableList.add(key1);

        Collectable key2 = new Collectable(world);
        key2.setPosition(new Vec2(4, -1));
        collectableList.add(key2);

        Collectable key3 = new Collectable(world);
        key3.setPosition(new Vec2(-7, 0));
        collectableList.add(key3);

        /* Initialising CollisionListener with ball */
        BallCollisions ballCollisions = new BallCollisions(ball, lever, collectableList, portal_pair, rockball);
        ball.addCollisionListener(ballCollisions);

        /* Initialising StepListener */
        world.addStepListener(new PatrollerController(spike1, -11.5f, -8f));
        world.addStepListener(new PatrollerController(spike2, -7f, -3.5f));

        // Checks if a key is pressed.
        KeyboardListener k = new KeyboardListener(ball, world, frame, view);
        frame.addKeyListener(k);
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


    public static JTextField createTextField(String text, int textSize, Color colour) {
        JTextField textField = new JTextField(text);
        textField.setFont(new Font("Arial", Font.PLAIN, textSize));
        textField.setForeground(colour);
        return textField;
    }


    public static List<Collectable> getCollectableList() {
        return collectableList;
    }

    public static void resetGame(World world, JFrame frame) {
        // Clear the current world bodies.
        for (DynamicBody dynamicBody: world.getDynamicBodies()) {
            dynamicBody.destroy();
        }
        for (StaticBody staticBody: world.getStaticBodies()) {
            staticBody.destroy();
        }

        initialiseGame(world, frame);
    }

    /** Run the game. */
    public static void main(String[] args) {

        new Game();
    }
}
