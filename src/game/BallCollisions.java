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
    private List<Collectable> collectables;
    // private List<Spikes> spike;   // This will store array of body as per the requirements.

    public BallCollisions(Ball ball, Lever lever, List<Collectable> collectables) {
        this.ball = ball;
        this.lever = lever;
        this.collectables = collectables;
    }


    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Lever && e.getReportingBody() instanceof Ball) {
            System.out.println("Ball collided with lever");
            lever.setLeverState("on");
        }
        else if (e.getOtherBody() instanceof Collectable && e.getReportingBody() instanceof Ball) {
            System.out.println("Ball collided with coin.");
            for (Collectable collectable: collectables) {
                if (collectable == e.getOtherBody()){
                    collectable.destroy();
                    collectable.setCoin_count(collectable.getCoin_count() + 1);
                }
            }
        }
    }
}
