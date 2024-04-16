package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;


public class PatrollerController implements StepListener {
    private StaticBody patroller;
    private Vec2 startPosition;
    private float left, right;
    private float upper, lower;
    private float delta;


    PatrollerController(StaticBody patroller, float left, float right) {
        this.startPosition = patroller.getPosition();
        this.patroller = patroller;
        this.left = left;
        this.right = right;
        delta=0.008f;
        Game.getLevel().getLevelWorld().addStepListener(this);
    }

    @Override
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
    public void postStep(StepEvent stepEvent) {
    }
}
