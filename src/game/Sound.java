package game;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private Clip clip;

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

    public void play() {
        // Below line make sure to reset the clip to start.
        this.clip.setFramePosition(0);
        this.clip.start();
    }

    public void stop() {
        this.clip.stop();
    }

    public void loopForever() {
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}

