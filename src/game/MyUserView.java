package game;

import city.cs.engine.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.File;

import java.util.List;


public class MyUserView extends UserView {
    private Image background;
    public static final Font STATUS_FONT = new Font("Monospaced", Font.PLAIN, 20);
    public World world;

    public MyUserView(World world, int width, int height) {
        super(world, width, height);
        this.world = world;
        try {
            background = ImageIO.read(new File("assets/images/background/background.jpg")); // replace with your image path
            background = background.getScaledInstance(width, height, Image.SCALE_SMOOTH); // resizing image to screen size
        } catch (IOException e) {
            System.out.println("Error: failed to load the background image.");
        }
    }

    @Override
    protected void paintForeground(Graphics2D g) {
        int timer = 0;
        g.setColor(Color.RED);
        g.setFont(STATUS_FONT);
        List<Collectable> temp = Game.getCollectableList();
        //Collectable temp = new Collectable(world);  // creating temporary object to access Collectable non-static methods.
        g.drawString("Coin count: " + temp.get(0).getCoin_count() + "/" + temp.get(0).getMax_coin_count(), 10, 25); // Max coin different on different levels. Consider that.
        g.drawString("Timer:" + timer, 400, 25);
    }

    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(background, 0, 0, this);
    }
}
