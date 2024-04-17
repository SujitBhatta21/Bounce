package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;
import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

/**
 * class that represents all the buttons created in the game.
 *
 * @author      Sujit Bhatta, sujit.bhatta@city.ac.uk
 * @version     1.0
 * @since       1.0
 */
public class MyGameButton {
    /**
     * The world in which the button exists.
     */
    private MyWorld world;

    /**
     * The text displayed on the button.
     */
    private String text;

    /**
     * The x and y coordinates of the button.
     */
    private float x, y;

    /**
     * The width and height of the button.
     */
    private float width, height;

    /**
     * The shape of the button.
     */
    private Shape buttonShape;

    /**
     * The body of the button.
     */
    private StaticBody buttonBody;

    /**
     * The image displayed on the button.
     */
    private Image buttonImage;

    /**
     * The body image of the button.
     */
    private BodyImage image;

    /**
     * The key code associated with the button.
     */
    private String keyCode;

    /**
     * Constructor for MyGameButton.
     *
     * @param  world The world in which the button exists.
     * @param  x The x coordinate of the button.
     * @param  y The y coordinate of the button.
     * @param  width The width of the button.
     * @param  height The height of the button.
     * @param  keyCode The key code associated with the button.
     * @param  imagePath The path to the image displayed on the button.
     */
    public MyGameButton(MyWorld world, float x, float y, float width, float height, String keyCode, String imagePath) {
        if (keyCode == "RESTART" || keyCode == "NEXT LEVEL") {
            this.buttonShape = new CircleShape(height);
            this.image = new BodyImage(imagePath,  2f*height);
        } else {
            this.buttonShape = new BoxShape(width, height);
            this.image = new BodyImage(imagePath, 3.5f*height);
        }
        this.world = world;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.keyCode = keyCode;
        this.buttonBody = new StaticBody(world);
        buttonBody.setPosition(new Vec2(this.getX(), this.getY()));
        GhostlyFixture myButton = new GhostlyFixture(buttonBody, buttonShape);

        buttonBody.addImage(image);
    }

    /**
     * Returns the body of the button.
     *
     * @return The body of the button.
     */
    public StaticBody getButtonBody() {
        return buttonBody;
    }

    /**
     * Returns the x coordinate of the button.
     *
     * @return The x coordinate of the button.
     */
    public float getX() {
        return x;
    }

    /**
     * Returns the y coordinate of the button.
     *
     * @return The y coordinate of the button.
     */
    public float getY() {
        return y;
    }

    /**
     * Returns the key code associated with the button.
     *
     * @return The key code associated with the button.
     */
    public String getKeyCode() {
        return keyCode;
    }
}