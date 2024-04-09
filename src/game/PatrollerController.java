package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;


public class PatrollerController implements StepListener {
    private Walker patroller;
    private float left, right;
    private float originalY; // stores the original y-coordinate

    PatrollerController(Walker patroller, float left, float right) {
        this.patroller = patroller;
        this.left = left;
        this.right = right;
        this.originalY = patroller.getPosition().y; // get the original y-coordinate
    }

    @Override
    public void preStep(StepEvent stepEvent) {
        Vec2 pos = patroller.getPosition();
        if (pos.x <= left) {
            patroller.startWalking(1);
        } else if (pos.x >= right) {
            patroller.startWalking(-1);
        }
        // reset the y-coordinate to its original value. For moving platform especially.
        patroller.setPosition(new Vec2(pos.x, originalY));
    }

    @Override
    public void postStep(StepEvent stepEvent) {
    }
}
