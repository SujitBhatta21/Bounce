package game;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * This class represents sound and its controls in the game.
 *
 * @author      Sujit Bhatta, sujit.bhatta@city.ac.uk
 * @version     1.0
 * @since       1.0
 */
public class Sound {
    /**
     * The Clip that holds the audio data.
     */
    private Clip clip;

    /**
     * Constructor for Sound.
     *
     * @param  soundFilePath The path to the sound file.
     */
    public Sound(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            this.clip = AudioSystem.getClip();
            this.clip.open(audioIn);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays the sound from the beginning.
     */
    public void play() {
        // Below line make sure to reset the clip to start.
        this.clip.setFramePosition(0);
        this.clip.start();
    }

    /**
     * Stops the sound.
     */
    public void stop() {
        this.clip.stop();
    }

    /**
     * Loops the sound continuously.
     */
    public void loopForever() {
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}

