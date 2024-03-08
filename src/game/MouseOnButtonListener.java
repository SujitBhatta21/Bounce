package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MouseOnButtonListener extends MouseAdapter {
    private World world;
    private MyUserView view;

    public MouseOnButtonListener(World world, MyUserView view) {
        this.world = world;
        this.view = view;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Vec2 p = view.viewToWorld(e.getPoint());
        for (MyGameButton button: Game.getAllButtons()) {
            if (button.getButtonBody().contains(p)) {
                if ((button.getKeyCode() == "HELP")) {
                    System.out.println("HELP button is pressed.");
                    // JFrame helpFrame = new JFrame()
                }
            }
        }
    }

}
