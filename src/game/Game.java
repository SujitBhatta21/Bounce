package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import javax.swing.*;

import java.awt.*;
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
    private static int score = 0;  // Initialize score.
    private static int WIDTH = 500, HEIGHT = 500;
    private static JLabel scoreLabel;  // Score display.
    private static Color transparent_colour = new Color(0, 0, 0, 0);

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

        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setBounds(0, 0, 100, 20);  // Set position and size.
        frame.add(scoreLabel);  // Add the score display to the frame.

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

        updateScore(0); // You can pass 0 or any initial score value
    }


    // Method to initialize game components
    private void initializeGame(World world, JFrame frame) {
        level_1(world, frame);
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setBounds(10, 10, 100, 20);
        frame.add(scoreLabel);

        // Making a debugging view. Used for debugging.
        new DebugViewer(world, 500, 500);
    }



    public static void level_1(World world, JFrame frame) {

        System.out.println("level_1 method called"); // debugging

        // make a suspended platform
        Shape platformShape = new BoxShape(3, 0.5f);
        StaticBody platform1 = new StaticBody(world, platformShape);
        platform1.setPosition(new Vec2(-8, -2f));
        // platform1.addImage(new BodyImage("assets/images/Platform.png")); (Find a good background after milestone completed.....)

        // making lever.
        Lever lever = new Lever(world);
        lever.setPosition(new Vec2(10, -7));

        /* making a border. */
        making_world_border(world);

        // Making a Walker ball.
        ball = new Ball(world, 0, 0);
        // ball.setGravityScale(0); Uncomment this for testing character coordinates.
        ball.setPosition(new Vec2(Ball.getXPos(), Ball.getYPos()));

        BallCollisions ballCollisions = new BallCollisions(ball, lever);

        ball.addCollisionListener(ballCollisions);



        // Checks if a key is pressed.
        KeyboardListener k = new KeyboardListener(ball);
        frame.addKeyListener(k);
    }

    // Method to update the score.
    public static void updateScore(int increment) {
        if (scoreLabel != null) {
            score += increment;
            scoreLabel.setText("Score: " + score);  // Update the score display.
            System.out.println("not null");
        } else {
            System.out.println("scoreLabel is null!");
        }
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
        ground.setPosition(new Vec2(0f, - HEIGHT / 56 - 0.5f));

        // making right border
        Shape right = new BoxShape(0.5f, HEIGHT / 30);
        StaticBody right_border = new StaticBody(world, right);
        right_border.setPosition(new Vec2(WIDTH / 37, 0));

        // making top border
//        Shape top = new BoxShape(WIDTH / 30, 0.5f);
//        StaticBody top_border = new StaticBody(world, top);
//        top_border.setPosition(new Vec2(0 , HEIGHT/37));
    }

    /** Run the game. */
    public static void main(String[] args) {

        new Game();
    }
}
