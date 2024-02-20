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

    /** Initialise a new Game. */
    public Game() {
        //1. make an empty game world
        World world = new World();

        //3. make a view to look into the game world
        int WIDTH = 900, HEIGHT = 750;
        MyUserView view = new MyUserView(world, WIDTH, HEIGHT);

        //4. create a Java window (frame) and add the game
        //   view to it
        final JFrame frame = new JFrame("City Game");
        frame.setSize(WIDTH, HEIGHT); // Set the size of the JFrame

        // Testing if the order of code matters.
        level_1(world, frame);

        // Set the layout manager to null::: Personally I did not like default layer manager.
        frame.setLayout(null);

        view.setBounds(0, 0, WIDTH, HEIGHT);
        frame.add(view); // Add the game view to the frame

        // enable the frame to quit the application
        // when the x button is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        // don't let the frame be resized
        frame.setResizable(false);

        // finally, make the frame visible
        frame.setVisible(true);

        // request focus in window. Allows keyListener to work.
        frame.requestFocusInWindow();

        // Making a debugging view. Used for debugging.
        JFrame debugView = new DebugViewer(world, 500, 500);

        // start our game world simulation!
        world.start();
    }



    /** Run the game. */
    public static void main(String[] args) {

        new Game();
    }


    public static void level_1(World world, JFrame frame) {

        //make a ground platform
        Shape shape = new BoxShape(30, 0.5f);
        StaticBody ground = new StaticBody(world, shape);
        ground.setPosition(new Vec2(0f, -11.5f));

        // make a suspended platform
        Shape platformShape = new BoxShape(3, 0.5f);
        StaticBody platform1 = new StaticBody(world, platformShape);
        platform1.setPosition(new Vec2(-8, -4f));
        // platform1.addImage(new BodyImage("assets/images/Platform.png")); (Find a good background after milestone completed.....)

        ball = new Ball(world, -4, 5);
        ball.setPosition(new Vec2(Ball.getXPos(), Ball.getYPos()));
        System.out.println(ball.getGravityScale());

        // Checks if a key is pressed.
        KeyboardListener k = new KeyboardListener(ball);
        frame.addKeyListener(k);



        // Testing button
        // Color BLACK = new Color(255, 100, 255);
        Button start_button = new Button("Start Button", "start");
        frame.add(start_button); // Add the button to the frame

    }
}
