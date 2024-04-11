package game;

import city.cs.engine.*;

public class MyWorld extends World {
    private boolean isStopped;

    public MyWorld() {
        super();
        isStopped = false;
    }

    @Override
    public void stop() {
        super.stop();
        isStopped = true;
    }

    @Override
    public void start() {
        super.start();
        isStopped = false;
    }

    public boolean isStopped() {
        return isStopped;
    }
}
