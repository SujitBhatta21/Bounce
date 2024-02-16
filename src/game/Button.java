package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Button {
    // Fields
    int x, y, width, height;
    Color colour;
    JButton button;

    public Button(int x, int y, int width, int height, Color colour, String text, ActionListener action) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.colour = colour;
        this.button = new JButton(text);
        button.setBounds(x, y, width, height); // Set the bounds of the button
        button.setBackground(this.colour); // Set the initial color of the button
        button.addActionListener(action); // Add the action listener to the button
    }

    public boolean button_hover() {
        Rectangle buttonBounds = new Rectangle(x, y, width, height);
        // Getting mouse cursor current position.
        Point point = MouseInfo.getPointerInfo().getLocation();
        int mouseX = (int) point.getX();
        int mouseY = (int) point.getY();

        return buttonBounds.contains(mouseX, mouseY);
    }

    public void hover_change_colour(Color hover_colour, Color default_colour) {
        if (button_hover()) {
            this.colour = hover_colour;
        }
        else {
            this.colour = default_colour;
        }
        button.setBackground(this.colour);
    }

    public JButton getButton() {
        return this.button;
    }
}
