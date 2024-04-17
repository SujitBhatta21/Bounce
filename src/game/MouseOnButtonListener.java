package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * MouseOnButtonListener is a class that extends MouseAdapter and represents a mouse listener for buttons in the game.
 * @author Your Name
 * @version 1.0
 * @since 1.0
 */
public class MouseOnButtonListener extends MouseAdapter {
    /**
     * The world in which the game takes place.
     */
    private MyWorld world;

    /**
     * The view of the user.
     */
    private MyUserView view;

    /**
     * The state of the game.
     */
    private String gameState;

    /**
     * The sound played when a button is clicked.
     */
    private Sound buttonClick = new Sound("assets/sounds/button_click.wav");

    /**
     * The constructor for the MouseOnButtonListener class.
     * @param world The world in which the game takes place.
     * @param view The view of the user.
     */
    public MouseOnButtonListener(MyWorld world, MyUserView view) {
        this.world = world;
        this.view = MyUserView.getView();
    }

    /**
     * Handles the mousePressed event.
     * @param e The MouseEvent object.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Vec2 p = view.viewToWorld(e.getPoint());
        ArrayList<MyGameButton> buttonsCopy = new ArrayList<>(Game.getAllButtons());
        for (MyGameButton button: buttonsCopy) {
            if (button.getButtonBody().contains(p)) {
                // Play button clicked sound.
                buttonClick.play();

                if ((button.getKeyCode() == "HELP" && view.getHelpClicked() == false && view.getWonTheGame() == false)) {
                    System.out.println("HELP button is pressed.");
                    view.setHelpClicked(true);
                    // PAUSE the world.
                    world.stop();
                } else if (button.getKeyCode() == "HELP" && view.getHelpClicked() && view.getWonTheGame() == false) {
                    view.setHelpClicked(false);
                    world.start();
                } else if (button.getKeyCode() == "PLAY") {
                    view.setGameState("play");
                    Game.getIntroFrame().setVisible(false);
                    Game.initialiseGame(Game.getFrame());
                    Game.getFrame().setVisible(true);
                    // Update sound after play clicked.
                    Game.updateSound();
                } else if (button.getKeyCode() == "NEXT LEVEL") {
                    Game.goToNextLevel();
                    view.setWonTheGame(false);
                } else if (button.getKeyCode() == "RESTART") {
                    // Reset the game...
                    Game.resetLevel();
                }
                else if (button.getKeyCode().equals("EXIT")) {
                    // Exiting the program
                    System.exit(0);
                }
            }
        }
    }
}
