package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MouseOnButtonListener extends MouseAdapter {
    private World world;
    private MyUserView view;
    private String gameState;
    private Sound buttonClick = new Sound("assets/sounds/button_click.wav");

    public MouseOnButtonListener(World world, MyUserView view) {
        this.world = world;
        this.view = MyUserView.getView();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Vec2 p = view.viewToWorld(e.getPoint());
        for (MyGameButton button: Game.getAllButtons()) {
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
