package game;

import city.cs.engine.*;
import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Main class representing the game.
 *
 * @author      Sujit Bhatta
 * @version     1.0
 * @since       1.0
 */
public class Game {
    /** The game world. */
    private static MyWorld world;
    /** The view of the game. */
    private static MyUserView view;
    /** The current game level. */
    private static GameLevel level;
    /** The main JFrame for the game. */
    private static JFrame frame, introFrame;
    /** The game's ball. */
    private static Ball ball;  // Making ball a static field.
    /** The width of the game window. */
    private static int WIDTH = 800, HEIGHT = 600;
    /** List of all buttons in the current frame/level. */
    private final static List<MyGameButton> allButtons = new ArrayList<>();
    /** Sound played during the intro scene. */
    private final static Sound bg_intro_sound = new Sound("assets/sounds/bt_bg_MainMenu.wav");
    /** Sound played during normal gameplay. */
    private final static Sound bg_play_sound = new Sound("assets/sounds/bt_bg_play.wav");
    /** Sound played during final gameplay. */
    private final static Sound bg_play_final_sound = new Sound("assets/sounds/BossLevel.wav");
    /** Sound played when losing the game. */
    private final static Sound losingSound = new Sound("assets/sounds/bt_death.wav");
    /** Sound played when winning the game. */
    private final static Sound winningSound = new Sound("assets/sounds/bt_Level_Complete.wav");


    /** Initialise a new Game. */
    public Game() {
        // make an empty game world
        world = new MyWorld();

        // create a Java window (frame) and add the game view to it
        frame = new JFrame("Bounce Game");
        frame.setSize(WIDTH, HEIGHT);

        introFrame = new JFrame("Intro Game");
        introFrame.setSize(WIDTH, HEIGHT);

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


    /**
     * Method to initialize game components. For setting up view removing prev. buttons and clearing screen for another screen.
     *
     * @param frame The main JFrame of the game.
     */
    public static void initialiseGame(JFrame frame) {
        // Initialising 1st game level.
        level = new Level1();
        view.setCollectableList(level.getCollectableList());

        world = Game.getLevel().getLevelWorld();

        // make a view to look into the game world
        view = new MyUserView(world, WIDTH, HEIGHT);
        view.setBounds(0, 0, WIDTH, HEIGHT);

        frame.add(view);  // Add the view to the frame.
        //optional: draw a 1-metre grid over the view
        //view.setGridResolution(1);

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


    /**
     * Method for setting up the intro scene.
     *
     * @param world The game world.
     * @param introFrame The intro JFrame.
     */
    public static void introScene(MyWorld world, JFrame introFrame) {
        // make a view to look into the game world
        view = new MyUserView(world, WIDTH, HEIGHT);
        view.setBounds(0, 0, WIDTH, HEIGHT);

        introFrame.add(view);  // Add the view to the frame.
        // optional: draw a 1-metre grid over the view
        //view.setGridResolution(1);

        MyGameButton playButton = new MyGameButton(world, 0, -2, 3, 2f, "PLAY", "assets/images/texts/PlayButton.png");
        MyGameButton exitButton = new MyGameButton(world, 0, -6, 3, 2f, "EXIT", "assets/images/texts/QuitButton.png");
        allButtons.add(playButton);
        allButtons.add(exitButton);

        view.addMouseListener(new MouseOnButtonListener(world, view));
    }


    /**
     * Returns a list of all buttons in the game.
     * <p> Used for storing and updating buttons in each level <p/>
     * @return A list of MyGameButton objects.
     */
    public static List<MyGameButton> getAllButtons() {
        return allButtons;
    }


    /**
     * Resets the current level.
     * This method is called only when restart button is clicked.
     */
    public static void resetLevel() {
        world.stop();

        // Clear the current world bodies.
        world = Game.getLevel().getLevelWorld();
        for (DynamicBody dynamicBody: world.getDynamicBodies()) {
            dynamicBody.destroy();
        }
        for (StaticBody staticBody: world.getStaticBodies()) {
            staticBody.destroy();
        }

        // clear existing buttons and collectables.
        allButtons.clear();
        level.getCollectableList().clear();

        level.startLevel(frame);

        level.getBall().setBallHealth(level.getBall().getBallMaxHealth());

        if (!(level instanceof Level4)) {
            MyGameButton helpButton = new MyGameButton(level.getLevelWorld(), 0, 13f, 2, 1f, "HELP", "assets/images/texts/HelpButton.png");
            allButtons.add(helpButton);
            view.setTimeLeft(100);
        }
        else {
            // Removing timer aspect from this level.
            view.setTimeLeft(100000);
        }

        // Resetting timer and key/coin count to 0.

        view.getKeys().get(0).setCoin_count(0);
        view.setWonTheGame(false);
        view.setLostTheGame(false);

        updateSound();
    }


    /**
     * Updates the game sound based on the current game state/level.
     */
    public static void updateSound() {
        if (view.getGameState().equals("intro")) {
            bg_intro_sound.loopForever();
            System.out.println("Start sound");
        } else if (view.getGameState().equals("play")){
            bg_intro_sound.stop();
            bg_play_sound.stop();

            if (view.getWonTheGame()) {
                winningSound.play();
                bg_play_sound.stop();
                bg_play_final_sound.stop();
            } else if (view.isLostTheGame()) {
                losingSound.play();
                bg_play_sound.stop();
                bg_play_final_sound.stop();
            }
            else{
                if (!(level instanceof Level4)) {
                    bg_play_sound.loopForever();
                } else {
                    bg_play_final_sound.loopForever();
                }
            }
        }
    }


    /**
     * Moves the game to the next level.
     * This method is only called when next level button is clicked.
     */
    public static void goToNextLevel(){
        // Removing previous level collision listener and buttons.
        level.getBall().removeAllCollisionListeners();
        for (MyGameButton button: allButtons) {
            button.getButtonBody().destroy();
        }
        level.stopLevel();

        if (level instanceof Level1){
            //level now references to the new next level
            System.out.println("Level2 instantiated");
            level = new Level2();
        }
        else if (level instanceof Level2) {
            System.out.println("Level3 instantiated.");
            level = new Level3();
        }
        else if (level instanceof Level3) {
            System.out.println("Level4 instantiated.");
            level = new Level4();
        }


        // Setting up for new level.
        view.setWorld(level.getLevelWorld());
        view.setCollectableList(level.getCollectableList());
        view.setWonTheGame(false);
        view.setLostTheGame(false);

        // Initialising the new level.
        level.startLevel(frame);

        // Resetting timer and key/coin count to 0.
        view.setViewWorld(level.getLevelWorld());
        view.restartTimer();
        view.getKeys().get(0).setCoin_count(0);
        level.getCollectableList().get(0).setCoin_count(0);

        // Making help button for next level.
        if (!(level instanceof Level4)) {
            MyGameButton helpButton = new MyGameButton(level.getLevelWorld(), 0, 13f, 2, 1f, "HELP", "assets/images/texts/HelpButton.png");
            allButtons.add(helpButton);
        }
        else {
            view.setTimeLeft(100000);
        }

        updateSound();
    }

    /** Returns the main JFrame of the game. */
    public static JFrame getFrame() {
        return frame;
    }

    /** Returns the intro JFrame of the game. */
    public static JFrame getIntroFrame() {
        return introFrame;
    }

    /** Returns the view of the game. */
    public static MyUserView getView() {
        return view;
    }

    /** Returns the game world. */
    public static MyWorld getWorld() {
        return world;
    }

    /** Returns the current game level. */
    public static GameLevel getLevel() {
        return level;
    }

    /** Run the game. */
    public static void main(String[] args) {
        new Game();
    }
}
