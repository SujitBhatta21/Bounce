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

    private static World world;
    private static MyUserView view;
    private static GameLevel level;
    private static JFrame frame, introFrame;
    private static Ball ball;  // Making ball a static field.
    private static int WIDTH = 800, HEIGHT = 600;

    private final static List<MyGameButton> allButtons = new ArrayList<>();
    private final static Sound bg_intro_sound = new Sound("assets/sounds/bt_bg_MainMenu.wav");
    private final static Sound bg_play_sound = new Sound("assets/sounds/bt_bg_play.wav");
    private final static Sound losingSound = new Sound("assets/sounds/bt_death.wav");
    private final static Sound winningSound = new Sound("assets/sounds/bt_Level_Complete.wav");


    /** Initialise a new Game. */
    public Game() {
        // make an empty game world
        world = new World();

        // create a Java window (frame) and add the game view to it
        frame = new JFrame("Bounce Game");
        frame.setSize(WIDTH, HEIGHT);

        introFrame = new JFrame("Intro Game");
        introFrame.setSize(WIDTH, HEIGHT);

        // Making a debugging view. Used for debugging.
        new DebugViewer(world, 800, 600);

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
            introFrame.setVisible(true);
        }

        // Play intro sound
        updateSound();

        // start our game world simulation!
        world.start();
    }


    // Method to initialize game components. For setting up view removing prev. buttons and clearing screen for another screen.
    public static void initialiseGame(JFrame frame) {
        // Initialising 1st game level.
        level = new Level1();
        view.setCollectableList(level.getCollectableList());

        world = Game.getLevel().getLevelWorld();

        // make a view to look into the game world
        view = new MyUserView(world, WIDTH, HEIGHT);
        view.setBounds(0, 0, WIDTH, HEIGHT);

        // Making a debugging view. Used for debugging.
        new DebugViewer(world, 800, 600);

        frame.add(view);  // Add the view to the frame.
        //optional: draw a 1-metre grid over the view
        view.setGridResolution(1);

        for (MyGameButton button: allButtons) {
            button.getButtonBody().destroy();
        }

        level.startLevel(frame);

        // clear existing items.
        allButtons.clear();

        MyGameButton helpButton = new MyGameButton(world, 0, 13f, 2, 1f, "HELP","assets/images/texts/HelpButton.png");
        allButtons.add(helpButton);

        view.addMouseListener(new MouseOnButtonListener(Game.getLevel().getLevelWorld(), view));
    }


    public static void introScene(World world, JFrame introFrame) {
        // make a view to look into the game world
        view = new MyUserView(world, WIDTH, HEIGHT);
        view.setBounds(0, 0, WIDTH, HEIGHT);

        introFrame.add(view);  // Add the view to the frame.
        // optional: draw a 1-metre grid over the view
        view.setGridResolution(1);

        MyGameButton playButton = new MyGameButton(world, 0, 0, 3, 2f, "PLAY", "assets/images/texts/PlayButton.png");
        MyGameButton optionButton = new MyGameButton(world, 0, -4, 3, 2f, "OPTION", "assets/images/texts/HelpButton.png");
        MyGameButton exitButton = new MyGameButton(world, 0, -8, 3, 2f, "EXIT", "assets/images/texts/QuitButton.png");
        allButtons.add(playButton);
        allButtons.add(optionButton);
        allButtons.add(exitButton);

        view.addMouseListener(new MouseOnButtonListener(world, view));
    }



    public static List<MyGameButton> getAllButtons() {
        return allButtons;
    }


    public static void resetLevel() {

        // Clear the current world bodies.
        for (DynamicBody dynamicBody: world.getDynamicBodies()) {
            dynamicBody.destroy();
        }
        for (StaticBody staticBody: world.getStaticBodies()) {
            staticBody.destroy();
        }

        // Clear existing collectables
        level.getCollectableList().clear();

        level.startLevel(frame);

        // clear existing items.
        allButtons.clear();

        MyGameButton helpButton = new MyGameButton(world, 0, 13f, 2, 1f, "HELP","assets/images/texts/HelpButton.png");
        allButtons.add(helpButton);

        // Resetting timer and key/coin count to 0.
        view.setTimeLeft(100);
        view.getKeys().get(0).setCoin_count(0);

        // Adding mouse listener on current view.
        view.addMouseListener(new MouseOnButtonListener(Game.getLevel().getLevelWorld(), view));
    }



    public static void updateSound() {
        if (view.getGameState().equals("intro")) {
            bg_intro_sound.loopForever();
            System.out.println("Start sound");
        } else if (view.getGameState().equals("play")){
            bg_intro_sound.stop();
            bg_play_sound.stop();

            if (view.getWonTheGame()) {
                winningSound.play();
            } else if (view.isLostTheGame()) {
                losingSound.play();
            }
            else{
                bg_play_sound.loopForever();
            }
        }
    }


    public static void goToNextLevel(){
        if (level instanceof Level1){
            System.out.println(world);
            level.stopLevel();
            level = new Level2();
            //level now refer to the new level

            // Testing
            System.out.println(world);
            view.setWorld(level.getLevelWorld());
            // Testing
            System.out.println(view.getWorld());

            // controller.updateStudent(level.getStudent());
            level.startLevel(frame);
        }
        else if (level instanceof Level2) {
            System.out.println("Well done! Game complete.");
            // System.exit(0);
        }
    }

    public static JFrame getFrame() {
        return frame;
    }
    public static JFrame getIntroFrame() {
        return introFrame;
    }

    public static MyUserView getView() {
        return view;
    }

    public static World getWorld() {
        return world;
    }

    public static GameLevel getLevel() {
        return level;
    }

    /** Run the game. */
    public static void main(String[] args) {

        new Game();
    }
}
