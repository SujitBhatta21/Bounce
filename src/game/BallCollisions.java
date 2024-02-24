package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.StaticBody;
import city.cs.engine.DynamicBody;

import java.util.List;
import java.util.ArrayList;

public class BallCollisions implements CollisionListener {
    private Ball ball;
    private Lever lever;
    // private List<Spikes> spike;   // This will store array of body as per the requirements.

    public BallCollisions(Ball ball, Lever lever) {
        this.ball = ball;
        this.lever = lever;
    }



    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Lever) {
            System.out.println("Ball collided with lever");
            lever.setLeverState("on");
        }
    }
}
