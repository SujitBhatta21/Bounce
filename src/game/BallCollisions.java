package game;

import city.cs.engine.*;

import java.util.List;
import org.jbox2d.common.Vec2;

public class BallCollisions implements CollisionListener {
    private World world;
    private MyUserView view;
    private Ball ball;
    private Lever lever;
    private List<Collectable> collectables;
    private Portal[] portal_pair;
    private StaticBody levelEndFinalTouch;
    // private List<Spikes> spike;   // This will store array of body as per the requirements.

    public BallCollisions(World world, MyUserView view, Ball ball, Lever lever, List<Collectable> collectables, Portal[] portal_pair, StaticBody levelEndFinalTouch) {
        this.world = world;
        this.view = view;
        this.ball = ball;
        this.lever = lever;
        this.collectables = collectables;
        this.portal_pair = portal_pair;
        this.levelEndFinalTouch = levelEndFinalTouch;
    }


    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Lever && e.getReportingBody() instanceof Ball) {
            System.out.println("Ball collided with lever");
          //  MyGameButton youLost = new MyGameButton(world, 2, 2, 2, 2, "YOU LOST");

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

        else if (e.getOtherBody() == levelEndFinalTouch && e.getReportingBody() instanceof Ball) {
            if (collectables.get(0).getCoin_count() == collectables.get(0).getMax_coin_count()) {
                System.out.println("You have fred rock ball. Congratulations!!!");
                view.setWonTheGame(true);

                world.stop();
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
            // Code below is still buggy.
            if (ball.getLastPortal() != null) {
                otherPortal = ball.getLastPortal();
            }
            ball.setPosition(new Vec2(otherPortal.getXPos(), otherPortal.getYPos()));
            ball.setLastPortal(touchedPortal);
        }

        else if (e.getOtherBody() instanceof Spike && e.getReportingBody() instanceof Ball)  {
            if (ball.getBallHealth() == 0) {
                System.out.println("You lost the game...");
                e.getReportingBody().destroy();

                // Displaying you lost text.
                view.setLostTheGame(true);

                world.stop();
            }
            else {
                System.out.println("Ball Health: " + ball.getBallHealth());
                ball.setBallHealth(ball.getBallHealth() - 1);
            }
        }
    }
}
