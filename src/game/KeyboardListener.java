package game;

import city.cs.engine.DynamicBody;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener{
    private World world;
    private JFrame frame;
    private MyUserView view;
    private Ball ball;
    private static float ball_speed = 8;

    KeyboardListener(Ball ball, World world, JFrame frame, MyUserView view) {
        this.ball = ball;
        this.world = world;
        this.frame = frame;
        this.view = view;
    }
    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            System.out.println("Up Arrow-Key is pressed!");
            ball.jump(ball_speed);
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            System.out.println("DOWN Arrow-Key is pressed!");
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            System.out.println("LEFT Arrow-Key is pressed!");
            ball.startWalking(-ball_speed);
            ball.rotate(0.2f);
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            System.out.println("Right Arrow-Key is pressed!");
            ball.startWalking(ball_speed);
            ball.rotate(-0.2f);
        }
        else if (e.getKeyCode() == KeyEvent.VK_R) {
            ball.setBallHealth(ball.getBallMaxHealth());
            Game.resetLevel();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            // Stop the ball's movement when left or right key is released
            ball.stopWalking();
        }
    }
}
