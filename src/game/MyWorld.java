package game;

import city.cs.engine.*;

/**
 * This class extends World and represents world created in every level/frames.
 *
 * @author      Sujit Bhatta, sujit.bhatta@city.ac.uk
 * @version     1.0
 * @since       1.0
 */
public class MyWorld extends World {
    /**
     * A boolean variable to check if the world is stopped.
     */
    private boolean isStopped;

    /**
     * Constructor for MyWorld.
     */
    public MyWorld() {
        super();
        isStopped = false;
    }

    /**
     * Stops the world and sets the isStopped variable to true.
     */
    @Override
    public void stop() {
        super.stop();
        isStopped = true;
    }

    /**
     * Starts the world and sets the isStopped variable to false.
     */
    @Override
    public void start() {
        super.start();
        isStopped = false;
    }

    /**
     * Returns the state of the world, whether it is stopped or not.
     *
     * @return The state of the world.
     */
    public boolean isStopped() {
        return isStopped;
    }
}
