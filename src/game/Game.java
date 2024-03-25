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
    private static GameLevel level;
    private static JFrame frame, introFrame;
    private static Ball ball;  // Making ball a static field.
    private static int WIDTH = 500, HEIGHT = 500;

    private final static List<MyGameButton> allButtons = new ArrayList<>();


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

        // Initialising 1st game level.
        level = new Level1(world, view);
        view.setCollectableList(level.getCollectableList());

        // Making a debugging view. Used for debugging.
        new DebugViewer(world, 500, 500);

        frame.add(view);  // Add the view to the frame.
        //optional: draw a 1-metre grid over the view
        // view.setGridResolution(1);

        for (MyGameButton button: allButtons) {
            button.getButtonBody().destroy();
        }

        level.start_level(frame);

        // clear existing items.
        allButtons.clear();

        MyGameButton helpButton = new MyGameButton(world, 0, 10.5f, 2, 1f, "HELP","assets/images/texts/help_button.png");
        allButtons.add(helpButton);

        view.addMouseListener(new MouseOnButtonListener(world, view));
    }


    public static void introScene(World world, JFrame introFrame) {
        // make a view to look into the game world
        view = new MyUserView(world, WIDTH, HEIGHT);
        view.setBounds(0, 0, WIDTH, HEIGHT);

        introFrame.add(view);  // Add the view to the frame.
        // optional: draw a 1-metre grid over the view
        // view.setGridResolution(1);

        MyGameButton playButton = new MyGameButton(world, -1, 2, 2, 1.5f, "PLAY", "assets/images/texts/PlayButton.png");
        MyGameButton optionButton = new MyGameButton(world, -1, -1, 2, 1.5f, "OPTION", "assets/images/texts/HelpButton.png");
        MyGameButton exitButton = new MyGameButton(world, -1, -4, 2, 1.5f, "EXIT", "assets/images/texts/QuitButton.png");
        allButtons.add(playButton);
        allButtons.add(optionButton);
        allButtons.add(exitButton);

        view.addMouseListener(new MouseOnButtonListener(world, view));
    }



    public static List<MyGameButton> getAllButtons() {
        return allButtons;
    }

    public static void resetLevel(World world, JFrame frame) {
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

        // GameLevel temp = getLevel();
        level = new Level1(world, view);
        view.setCollectableList(level.getCollectableList());
    }

    public static GameLevel getLevel() {
        return level;
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
