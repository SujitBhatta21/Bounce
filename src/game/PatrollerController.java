package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;


public class PatrollerController implements StepListener {
    private StaticBody patroller;
    private Vec2 startPosition;
    private float left, right;
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
        if (patroller.getPosition().x < left || patroller.getPosition().x > right){
            delta*=-1;
        }
        patroller.setPosition(new Vec2(patroller.getPosition().x + delta, patroller.getPosition().y));
    }

    @Override
    public void postStep(StepEvent stepEvent) {
    }
}
