package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import javax.swing.JFrame;

import java.awt.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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

        // Make a functionality that checks whether the start button is clicked or not...


        // populate it with bodies (ex: platforms, collectibles, characters)
        if (start_pressed) {
            level_1(world);
        }

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
         // JFrame debugView = new DebugViewer(world, 500, 500);

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
        Shape studentShape = new BoxShape(1,2);
        DynamicBody student = new DynamicBody(world, studentShape);
        // optional: Below line causes the dynamic body type student to not have gravity attribute.
        // Remember that there is gravity as default=1 and student stops at ground because ground is static.
        //student.setGravityScale(0);
        student.setPosition(new Vec2(4,-5));
        student.addImage(new BodyImage("assets/images/character/doodle_left.png", 4));
    }
}


