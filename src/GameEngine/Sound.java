package GameEngine;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private static GameEngine.Sound instance;
    private Clip backgroundMusic;
    private boolean isMusicEnabled = true;

    private Sound() {}

    public static GameEngine.Sound getInstance() {
        if (instance == null) {
            instance = new GameEngine.Sound();
        }
        return instance;
    }

    public void loadMusic(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading music: " + e.getMessage());
        }
    }

    public void playMusicLoop() {
        if (backgroundMusic != null && isMusicEnabled) {
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void playMusic() {
        if (backgroundMusic != null && isMusicEnabled) {
            backgroundMusic.start();
        }
    }

    public void stopMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

    public void toggleMusic() {
        if (backgroundMusic != null) {
            if (isMusicEnabled) {
                stopMusic();
                isMusicEnabled = false;
            } else {
                playMusicLoop();
                isMusicEnabled = true;
            }
        }
    }

    public void closeMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.close();
        }
    }
}

