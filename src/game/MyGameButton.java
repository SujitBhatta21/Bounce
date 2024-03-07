package game;

import city.cs.engine.*;

import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyGameButton {
    private World world;
    private String text;
    private int x, y, width, height;
    public MyGameButton(World world, String text, int x, int y, int width, int height) {
        this.world = world;
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public void setActionListener(ActionListener actionListener) {
        // button.addActionListener(actionListener);
    }
}
