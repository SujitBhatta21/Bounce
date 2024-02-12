package game;

import city.cs.engine.*;
import java.awt.*;

public class MyUserView extends UserView {
    public MyUserView(World world, int width, int height) {
        super(world, width, height);
    }

    @Override
    protected void paintBackground(Graphics2D g) {
        g.setColor(Color.cyan);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
