package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import java.awt.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FallingPlatform extends StaticBody {
    private static float width = 4f ,height = 0.5f;
    private static float x_pos, y_pos;
    private static final Shape platformShape = new BoxShape(width, height);
    private Timer timer;
    private Timer rotationTimer;
    private int rotationSpeed = 1; // degrees per tick
    private boolean isVertical = false;

    public FallingPlatform(MyWorld world, float x_pos, float y_pos) {
        super(world, platformShape);
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        setPosition(new Vec2(x_pos, y_pos));
        this.setFillColor(Color.orange);

        rotationTimer = new Timer(50, new ActionListener() { // adjust delay for smoother/faster rotation
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isVertical) {
                    if (getAngleDegrees() < 90) {
                        rotateDegrees(rotationSpeed);
                    } else {
                        rotationTimer.stop();
                    }
                } else {
                    if (getAngleDegrees() > 0) {
                        rotateDegrees(-rotationSpeed);
                    } else {
                        rotationTimer.stop();
                    }
                }
            }
        });

        timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isVertical) {
                    isVertical = true;
                    timer.setDelay(3000);
                } else {
                    isVertical = false;
                }
                rotationTimer.start();
            }
        });
        timer.setRepeats(false);
    }

    public void ballTouched() {
        timer.restart();
    }

    public static float getXPos() {
        return x_pos;
    }

    public static float getYPos() {
        return y_pos;
    }
}
