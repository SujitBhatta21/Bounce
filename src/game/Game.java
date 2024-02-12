package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import javax.swing.JFrame;

import java.awt.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Your main game entry point
 */


public class Game {


    /** Initialise a new Game. */
    public Game() {

        //1. make an empty game world
        World world = new World();

        // Setting key variables...
        boolean start_pressed;
        float x_pos = 4;
        float y_pos = -5;

        // Make a functionality that checks whether the start button is clicked or not...


        // populate it with bodies (ex: platforms, collectibles, characters)
//        if (start_pressed) {
//            level_1(world);
//        }

        //3. make a view to look into the game world
        int WIDTH = 600, HEIGHT = 750;
        MyUserView view = new MyUserView(world, WIDTH, HEIGHT);

        //optional: draw a 1- metre grid over the view
        view.setGridResolution(1);


        //4. create a Java window (frame) and add the game
        //   view to it
        final JFrame frame = new JFrame("City Game");
        frame.add(view);

        // enable the frame to quit the application
        // when the x button is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        // don't let the frame be resized
        frame.setResizable(false);
        // size the frame to fit the world view
        frame.pack();
        // finally, make the frame visible
        frame.setVisible(true);

        //optional: uncomment this to make a debugging view
        JFrame debugView = new DebugViewer(world, 500, 500);

        KeyboardListener(frame);
        level_1(world);

        // start our game world simulation!
        world.start();
    }

    /** Run the game. */
    public static void main(String[] args) {

        new Game();
    }


    public static void level_1(World world) {

        //make a ground platform
        Shape shape = new BoxShape(30, 0.5f);
        StaticBody ground = new StaticBody(world, shape);
        ground.setPosition(new Vec2(0f, -11.5f));

        // make a suspended platform
        Shape platformShape = new BoxShape(3, 0.5f);
        StaticBody platform1 = new StaticBody(world, platformShape);
        platform1.setPosition(new Vec2(-8, -4f));
        platform1.addImage(new BodyImage("assets/images/Platform.png"));

        //make a character (with an overlaid image)

        Ball ball = new Ball(world);
        ball.setPosition(new Vec2(GlobalVariables.getXPos(), GlobalVariables.getYPos()));
    }

    public static void KeyboardListener(JFrame myFrame) {
        myFrame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_UP) {
                    System.out.println("Up Arrow-Key is pressed!");
                    GlobalVariables.setYPos(GlobalVariables.getYPos() + 1);
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    System.out.println("Down Arrow-Key is pressed!");
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    System.out.println("Left Arrow-Key is pressed!");
                    GlobalVariables.setXPos(GlobalVariables.getXPos() - 1);
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    System.out.println("Right Arrow-Key is pressed!");
                    GlobalVariables.setXPos(GlobalVariables.getXPos() + 1);
                }
            }
        });
    }
}


