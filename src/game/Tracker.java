package game;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import org.jbox2d.common.Vec2;

public class Tracker implements StepListener {
    private MyUserView view;
    private Hypnotiser hypnotiser;

    public Tracker(MyUserView view, Hypnotiser hypnotiser) {
        this.hypnotiser = hypnotiser;
        this.view = view;
    }
    public void preStep(StepEvent e) {}
    public void postStep(StepEvent e) {

        Vec2 position = hypnotiser.getPosition();
        Vec2 camera = new Vec2(position.x - 4f,position.y-8f);

        view.setCentre(camera);
    }
}