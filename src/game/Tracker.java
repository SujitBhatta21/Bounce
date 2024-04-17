package game;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import org.jbox2d.common.Vec2;

/**
 * This class represents a Tracker in the game.
 *
 * @author      Sujit Bhatta, sujit.bhatta@city.ac.uk
 * @version     1.0
 * @since       1.0
 */
public class Tracker implements StepListener {
    /**
     * The view of the user.
     */
    private MyUserView view;

    /**
     * The hypnotiser in the game.
     */
    private Hypnotiser hypnotiser;

    /**
     * Constructor for the Tracker class.
     *
     * @param  view The view of the user.
     * @param  hypnotiser The hypnotiser in the game.
     */
    public Tracker(MyUserView view, Hypnotiser hypnotiser) {
        this.hypnotiser = hypnotiser;
        this.view = view;
    }

    /**
     * Method to be executed before each step.
     *
     * @param  e The step event.
     */
    public void preStep(StepEvent e) {}

    /**
     * Method to be executed after each step.
     *
     * @param  e The step event.
     */
    public void postStep(StepEvent e) {
        Vec2 position = hypnotiser.getPosition();
        Vec2 camera = new Vec2(position.x - 4f,position.y-8f);

        view.setCentre(camera);
    }
}
