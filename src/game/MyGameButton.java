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

public class MyGameButton {
    private MyWorld world;
    private String text;
    private float x, y, width, height;
    private Shape buttonShape;
    private StaticBody buttonBody;
    private Image buttonImage;
    private BodyImage image;
    private String keyCode;

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

    public void drawOnPaintForeground(Graphics2D g) {
        // Get the position of the button
        float scale = 1f;
        Vec2 position = buttonBody.getPosition();

        // Calculate the screen position
        int screenX = (int) (position.x * scale);
        int screenY = (int) (position.y * scale);

        // Draw the image onto the Graphics2D object
        /* This function is incomplete. */
        // g.drawImage(this.image, screenX, screenY, null);
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
