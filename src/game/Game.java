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
    private static JFrame frame, introFrame;
    private static Ball ball;  // Making ball a static field.
    private static int WIDTH = 500, HEIGHT = 500;
    private final static Color transparent_colour = new Color(0, 0, 0, 0);
    private final static List<Collectable> collectableList = new ArrayList<Collectable>();
    private final static List<MyGameButton> allButtons = new ArrayList<>();

    private final static String platformImagePath = "assets/images/platform1.gif";

    /** Initialise a new Game. */
    public Game() {
        // make an empty game world
        World world = new World();

        // create a Java window (frame) and add the game view to it
        frame = new JFrame("Bounce Game");
        frame.setSize(WIDTH, HEIGHT);

        introFrame = new JFrame("Intro Game");
        introFrame.setSize(WIDTH, HEIGHT);

        // Making a debugging view. Used for debugging.
        new DebugViewer(world, 500, 500);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.requestFocusInWindow();

        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        introFrame.setLocationByPlatform(true);  // Set location by platform before making it visible
        introFrame.setResizable(false);
        introFrame.requestFocusInWindow();

        if (view.getGameState().equals("intro")) {
            frame.setVisible(false);
            introScene(world, introFrame);
            introFrame.setVisible(true);  // Make it visible after setting the location
        }

        // start our game world simulation!
        world.start();
    }


    // Method to initialize game components
    public static void initialiseGame(World world, JFrame frame) {

        // make a view to look into the game world
        view = new MyUserView(world, WIDTH, HEIGHT);
        view.setBounds(0, 0, WIDTH, HEIGHT);

        // Making a debugging view. Used for debugging.
        new DebugViewer(world, 500, 500);

        frame.add(view);  // Add the view to the frame.
        //optional: draw a 1-metre grid over the view
        // view.setGridResolution(1);

        for (MyGameButton button: allButtons) {
            button.getButtonBody().destroy();
        }

        // If reset R is clicked clear existing items.
        allButtons.clear();
        collectableList.clear();

        MyGameButton helpButton = new MyGameButton(world, 0, 10.5f, 2, 1f, "HELP","assets/images/texts/help_button.png");
        allButtons.add(helpButton);

        view.addMouseListener(new MouseOnButtonListener(world, view));

        level_1(world, frame);
    }


    public static void introScene(World world, JFrame introFrame) {
        // make a view to look into the game world
        view = new MyUserView(world, WIDTH, HEIGHT);
        view.setBounds(0, 0, WIDTH, HEIGHT);

        introFrame.add(view);  // Add the view to the frame.
        // optional: draw a 1-metre grid over the view
        // view.setGridResolution(1);

        MyGameButton playButton = new MyGameButton(world, -1, 2, 3, 1, "PLAY", "assets/images/texts/play.png");
        MyGameButton optionButton = new MyGameButton(world, -1, -1, 3, 1, "OPTION", "assets/images/texts/option.png");
        MyGameButton exitButton = new MyGameButton(world, -1, -4, 1.5f, 1, "EXIT", "assets/images/texts/Exit_icon.png");
        allButtons.add(playButton);
        allButtons.add(optionButton);
        allButtons.add(exitButton);

        view.addMouseListener(new MouseOnButtonListener(world, view));
    }


    public static void level_1(World world, JFrame frame) {

        System.out.println("level_1 method called");

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
        key3.setPosition(new Vec2(10, 7));
        collectableList.add(key3);

        /* Initialising CollisionListener with ball */
        BallCollisions ballCollisions = new BallCollisions(world, view, ball, lever, collectableList, portal_pair, rockball);
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

    public static List<MyGameButton> getAllButtons() {
        return allButtons;
    }

    public static void resetGame(World world, JFrame frame) {
        world.start();

        // Clear the current world bodies.
        for (DynamicBody dynamicBody: world.getDynamicBodies()) {
            dynamicBody.destroy();
        }
        for (StaticBody staticBody: world.getStaticBodies()) {
            staticBody.destroy();
        }

        // Clear the previous frame
        frame.getContentPane().removeAll();
        frame.repaint();

        initialiseGame(world, frame);
    }

    public static JFrame getFrame() {
        return frame;
    }
    public static JFrame getIntroFrame() {
        return introFrame;
    }

    /** Run the game. */
    public static void main(String[] args) {

        new Game();
    }
}
