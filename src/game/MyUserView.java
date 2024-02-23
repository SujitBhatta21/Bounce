package game;

import city.cs.engine.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.File;

public class MyUserView extends UserView {
    private Image background;

    public MyUserView(World world, int width, int height) {
        super(world, width, height);
        try {
            background = ImageIO.read(new File("assets/images/background/background.jpg")); // replace with your image path
            background = background.getScaledInstance(width, height, Image.SCALE_SMOOTH); // resizing image to screen size
        } catch (IOException e) {
            System.out.println("Error: failed to load the background image.");
        }
    }

    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(background, 0, 0, this);
    }
}
