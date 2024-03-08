package game;

import city.cs.engine.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.File;

import java.util.List;


public class MyUserView extends UserView {
    private Image background;
    private static int timer = 300;
    private static List<Collectable> keys = Game.getCollectableList();
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
        g.setColor(Color.RED);
        g.setFont(STATUS_FONT);
        g.drawString("Coin count: " + keys.get(0).getCoin_count() + "/" + keys.get(0).getMax_coin_count(), 10, 25); // Max coin different on different levels. Consider that.
        g.drawString("Timer:" + this.timer, 375, 25);
    }

    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(background, 0, 0, this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the original paintComponent method to do the default painting

        Image image = null;
        try {
            image = ImageIO.read(new File("assets/images/cage.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image != null) {
            g.drawImage(image, 60, 60, 65, 55, this);
        }
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public static List<Collectable> getKeys() {
        return keys;
    }
}
