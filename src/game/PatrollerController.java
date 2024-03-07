package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;


public class PatrollerController implements StepListener {
    private Walker patroller;
    private float left, right;
    PatrollerController(Walker patroller, float left, float right) {
        this.patroller = patroller;
        this.left = left;
        this.right = right;
    }

    @Override
    public void preStep(StepEvent stepEvent) {
        Vec2 pos = patroller.getPosition();
        if (pos.x <= left) {
            patroller.startWalking(1);
        } else if (pos.x >= right) {
            patroller.startWalking(-1);
        }
    }

    @Override
    public void postStep(StepEvent stepEvent) {
    }
}
