package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * This class implements StepListener to allow a StaticBody to patrol between two coordinates.
 *
 * @author      Sujit Bhatta, sujit.bhatta@city.ac.uk
 * @version     1.0
 * @since       1.0
 */
public class PatrollerController implements StepListener {
    /**
     * The patroller object.
     */
    private StaticBody patroller;
    /**
     * The start position of the patroller.
     */
    private Vec2 startPosition;
    /**
     * The left and right bounds for the patroller's movement.
     */
    private float left, right;
    /**
     * The upper and lower bounds for the patroller's vertical movement.
     */
    private float upper, lower;
    /**
     * The delta value for the patroller's movement.
     */
    private float delta;

    /**
     * Constructor for PatrollerController.
     *
     * @param  patroller The patroller object.
     * @param  left The left bound for the patroller's movement.
     * @param  right The right bound for the patroller's movement.
     */
    PatrollerController(StaticBody patroller, float left, float right) {
        this.startPosition = patroller.getPosition();
        this.patroller = patroller;
        this.left = left;
        this.right = right;
        delta=0.008f;
        Game.getLevel().getLevelWorld().addStepListener(this);
    }

    @Override
    /**
     * Pre-step event for the patroller.
     *
     * @param  stepEvent The step event.
     */
    public void preStep(StepEvent stepEvent) {
        if (Game.getLevel() instanceof Level4 && patroller instanceof MovingPlatform) {
            // Define the upper and lower bounds for vertical movement
            upper = startPosition.y + 5;
            lower = startPosition.y - 5;

            // Reverse direction when hitting the bounds
            if (patroller.getPosition().y < lower || patroller.getPosition().y > upper){
                delta *= -1;
            }

            // Updating position of patroller.
            patroller.setPosition(new Vec2(patroller.getPosition().x, patroller.getPosition().y + delta));

        } else {
            // Reverse direction when hitting the bounds
            if (patroller.getPosition().x < left || patroller.getPosition().x > right){
                if (patroller instanceof Mole) {
                    ((Mole) patroller).getMoleImage().flipHorizontal();
                }
                delta *= -1;
            }
            patroller.setPosition(new Vec2(patroller.getPosition().x + delta, patroller.getPosition().y));
        }

        if (patroller instanceof Mole) {
            ((Mole) patroller).updateMoleTimer();
        }
    }

    @Override
    /**
     * Post-step event for the patroller.
     *
     * @param  stepEvent The step event.
     */
    public void postStep(StepEvent stepEvent) {
    }
}
