package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyGameButton {
    private World world;
    private String text;
    private float x, y, width, height;
    private Shape buttonShape;
    private StaticBody buttonBody;
    private BodyImage image;
    private String keyCode;

    public MyGameButton(World world, float x, float y, float width, float height, String keyCode, String imagePath) {
        this.buttonShape = new BoxShape(width, height);
        this.world = world;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.keyCode = keyCode;
        this.buttonBody = new StaticBody(world);
        buttonBody.setPosition(new Vec2(this.getX(), this.getY()));
        GhostlyFixture myButton = new GhostlyFixture(buttonBody, buttonShape);

        // Load the image
        this.image = new BodyImage(imagePath, 3*height);
        buttonBody.addImage(image);

    }


    public StaticBody getButtonBody() {
        return buttonBody;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    public String getKeyCode() {
        return keyCode;
    }
}
