package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.StaticBody;
import city.cs.engine.DynamicBody;

import java.util.List;
import org.jbox2d.common.Vec2;

public class BallCollisions implements CollisionListener {
    private Ball ball;
    private Lever lever;
    private List<Collectable> collectables;
    private Portal[] portal_pair;
    // private List<Spikes> spike;   // This will store array of body as per the requirements.

    public BallCollisions(Ball ball, Lever lever, List<Collectable> collectables, Portal[] portal_pair) {
        this.ball = ball;
        this.lever = lever;
        this.collectables = collectables;
        this.portal_pair = portal_pair;
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
        else if (e.getOtherBody() instanceof Portal && e.getReportingBody() instanceof Ball) {
            System.out.println("Touched Portal");
            Portal touchedPortal = (Portal) e.getOtherBody();
            Portal otherPortal;
            if (touchedPortal == portal_pair[0]) {
                otherPortal = portal_pair[1];
            } else {
                otherPortal = portal_pair[0];
            }
            Ball ball = (Ball) e.getReportingBody();
            // Code is still buggy.
            if (ball.getLastPortal() != null) {
                otherPortal = ball.getLastPortal();
            }
            ball.setPosition(new Vec2(otherPortal.getXPos(), otherPortal.getYPos()));
            ball.setLastPortal(touchedPortal);
        }
    }
}
