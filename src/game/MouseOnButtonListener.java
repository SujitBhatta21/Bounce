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

    public MouseOnButtonListener(World world, MyUserView view) {
        this.world = world;
        this.view = view;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Vec2 p = view.viewToWorld(e.getPoint());
        for (MyGameButton button: Game.getAllButtons()) {
            if (button.getButtonBody().contains(p)) {
                if ((button.getKeyCode() == "HELP" && view.getHelpClicked() == false && view.getWonTheGame() == false)) {
                    System.out.println("HELP button is pressed.");
                    view.setHelpClicked(true);
                    // PAUSE the world.
                    world.stop();
                }
                else if (button.getKeyCode() == "HELP" && view.getHelpClicked() && view.getWonTheGame() == false) {
                    view.setHelpClicked(false);
                    world.start();
                }
                else if (button.getKeyCode() == "PLAY") {
                    view.setGameState("play");
                    Game.getIntroFrame().setVisible(false);
                    Game.initialiseGame(world, Game.getFrame());
                    Game.getFrame().setVisible(true);
                }
                else if (button.getKeyCode().equals("EXIT")) {
                    // Exiting the program
                    System.exit(0);
                }
            }
        }
    }

}
