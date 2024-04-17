package game;

import city.cs.engine.*;

import java.util.List;
import org.jbox2d.common.Vec2;

/**
 * This class handles the collisions in the game.
 * @author      Sujit Bhatta, sujit.bhatta@city.ac.uk
 * @version     1.0
 * @since       1.0
 */
public class BallCollisions implements CollisionListener {
    /**
     * The world in which the game is played.
     */
    private MyWorld world;

    /**
     * The view of the user.
     */
    private MyUserView view;

    /**
     * The current level of the game.
     */
    private GameLevel level;

    /**
     * The ball in the game.
     */
    private Ball ball;

    /**
     * The lever in the game.
     */
    private Lever lever;

    /**
     * The list of collectables in the game.
     */
    private List<Collectable> collectables;

    /**
     * The pair of portals in the game.
     */
    private Portal[] portal_pair;

    /**
     * The final touch of the level end.
     */
    private StaticBody levelEndFinalTouch;

    // private List<Spikes> spike;   // This will store array of body as per the requirements.

    // Sound clips for the game.
    /**
     * The sound of the lever.
     */
    private Sound leverSound = new Sound("assets/sounds/lever_on.wav");

    /**
     * The sound when the level is completed.
     */
    private Sound levelComplete = new Sound("assets/sounds/bt_Level_Complete.wav");

    /**
     * The sound when a key is collected.
     */
    private Sound keyCollect = new Sound("assets/sounds/key_collect.wav");

    /**
     * Constructor for the BallCollisions class.
     */
    public BallCollisions() {
        this.level = Game.getLevel();
        this.world = Game.getLevel().getLevelWorld();
        this.view = MyUserView.getView();
        this.ball = Game.getLevel().getBall();
        this.collectables = level.getCollectableList();
        this.lever = level.getLever();
        this.portal_pair = level.getPortal();
        this.levelEndFinalTouch = level.getLevelEndFinalTouch();
    }

    /**
     * This method is called when a collision occurs.
     * @param  e The collision event.
     */
    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Lever && e.getReportingBody() instanceof Ball) {
            System.out.println("Ball collided with lever");

            // Adding lever on clip.
            leverSound.play();
            lever.setLeverState("on");
        }

        else if (e.getOtherBody() instanceof Collectable && e.getReportingBody() instanceof Ball) {
            System.out.println("Ball collided with coin.");
            for (Collectable collectable: collectables) {
                if (collectable == e.getOtherBody()){
                    // Adding collectable sound when touched.
                    keyCollect.play();

                    collectable.destroy();
                    collectable.setCoin_count(collectable.getCoin_count() + 1);
                }
            }
        }

        else if (e.getOtherBody() == levelEndFinalTouch && e.getReportingBody() instanceof Ball) {
            // Testing code.
            // Uncomment below line for actual level1 gameplay.
            if (!(level instanceof Level4)) {
                if (collectables.get(0).getCoin_count() == collectables.get(0).getMax_coin_count()) {
                    if (level instanceof Level1) {
                        view.setWonTheGame(true);
                        // Update the sound in the game.
                        Game.updateSound();
                        System.out.println("You have fred rock ball. Congratulations!!!");
                    } else if (level instanceof Level2) {
                        view.setWonTheGame(true);
                        // Update the sound in the game.
                        Game.updateSound();
                        System.out.println("You can enter the dungeon...");
                    } else if (level instanceof Level3) {
                        view.setWonTheGame(true);
                        // Update the sound in the game.
                        Game.updateSound();
                        System.out.println("Final Boss Hypnotiser is ready...");
                    }
                }
                else {
                    view.setLostTheGame(true);

                    // Update the sound in the game.
                    Game.updateSound();

                    System.out.println("You did not collect all collectable.");
                }

                this.world.stop();
            }
            else {
                if (e.getOtherBody() instanceof Hypnotiser) {
                    Hypnotiser lastboss = ((Hypnotiser) e.getOtherBody());
                    if (lastboss.getBallHits() == 2 * 3) {
                        System.out.println("Congratulations you beat hypnotiser.");
                        view.setWonTheGame(true);
                        Game.updateSound();
                        level.getLevelWorld().stop();
                    } else {
                        lastboss.setBallHits(Hypnotiser.getBallHits() + 1);
                        System.out.println("Hypnotiser hits: " + lastboss.getBallHits());
                        lastboss.updateImage();
                    }
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
            // Code below is still buggy.
            if (ball.getLastPortal() != null) {
                otherPortal = ball.getLastPortal();
            }
            ball.setPosition(new Vec2(otherPortal.getXPos(), otherPortal.getYPos()));
            ball.setLastPortal(touchedPortal);
        }

        else if ((e.getOtherBody() instanceof Spike) && e.getReportingBody() instanceof Ball)  {
            if (ball.getBallHealth() == 0) {
                System.out.println("You lost the game...");
                // e.getReportingBody().destroy();

                // Displaying you lost text.
                view.setLostTheGame(true);

                // Updating the sounds.
                Game.updateSound();

                // Stopping the world. Change this for level mech later.
                world.stop();
            }
            else {
                System.out.println("Ball Health: " + ball.getBallHealth());
                ball.setBallHealth(ball.getBallHealth() - 1);
            }
        }

        else if (e.getOtherBody() instanceof Spring && e.getReportingBody() instanceof Ball) {
            ball.applyForce(new Vec2(0, 12000));
        }

        else if (e.getOtherBody() instanceof Mole && e.getReportingBody() instanceof Ball) { // replace Ball with your ball class
            if (((Mole) e.getOtherBody()).isUnderground()) {
                // Apply upward force to the ball
                ball.applyForce(new Vec2(0, 10000));
            } else {
                // Kill the mole
                e.getOtherBody().destroy();
                // Create a new key on top coordinate of the mole.
                Vec2 pos = e.getOtherBody().getPosition();
                Collectable key3 = new Collectable(world, pos.x, pos.y);
                level.getCollectableList().add(key3);
            }
        }



        if (level instanceof Level3) {
            for (StaticBody s: ((Level3) level).getIceBlockageCollection()) {
                if (e.getOtherBody() == s && ball.getBallMode() == Ball.Mode.ROCKY) {
                    s.destroy();
                }
            }

            if (e.getOtherBody() instanceof FallingPlatform && e.getReportingBody() instanceof Ball) {
                System.out.println("Ball collided with FallingPlatform.");
                ((FallingPlatform) e.getOtherBody()).ballTouched();
            }
        }
    }
}
