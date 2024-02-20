package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button extends JButton {
    private JButton b;
    private String action_command;

    public Button(String text, String action_command) {
        super(text);
        b = new JButton(text);
        b.setVerticalTextPosition(AbstractButton.CENTER);
        b.setHorizontalTextPosition(AbstractButton.CENTER);
        b.setActionCommand(action_command);
        this.action_command = action_command;
    }

    public Button(String text, Icon icon) {
        super(text, icon);
        b = new JButton(text, icon);
        b.setVerticalTextPosition(AbstractButton.CENTER);
    }
}
/*
Use this in the game class. This can be used to detect which button is pressed and enable and disable that button...
        addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Code to execute when the button is clicked
                 if ("disable".equals(e.getActionCommand())) {
                    b2.setEnabled(false);
                    b1.setEnabled(false);
                    b3.setEnabled(true);
                } else {
                    b2.setEnabled(true);
                    b1.setEnabled(true);
                    b3.setEnabled(false);
                }
            }
        });
 */

/*
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
*/
