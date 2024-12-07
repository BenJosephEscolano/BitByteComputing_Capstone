package GameEngine;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SoundEffect {
    private static SoundEffect instance;
    private Map<String, Clip> soundEffects;
    private boolean areSoundEffectsEnabled = true;

    private SoundEffect() {
        soundEffects = new HashMap<>();
    }

    public static SoundEffect getInstance() {
        if (instance == null) {
            instance = new SoundEffect();
        }
        return instance;
    }

    public void loadSoundEffect(String key, String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            soundEffects.put(key, clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading sound effect: " + e.getMessage());
        }
    }

    public void playSoundEffect(String key) {
        if (!areSoundEffectsEnabled) return;

        Clip clip = soundEffects.get(key);
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }


}