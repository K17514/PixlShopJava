package pixelshop;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {
    private static AudioPlayer instance;
    private Clip clip;
    private String currentTrack;
    private float currentVolume = 0.45f; // default 45%

    private AudioPlayer() {}

    public static AudioPlayer getInstance() {
        if (instance == null) {
            instance = new AudioPlayer();
        }
        return instance;
    }

    // Play a music file, replacing current if needed
    public void playMusic(String filepath) {
        System.out.println("➡️ Attempting to play: " + filepath);

        if (filepath.equals(currentTrack) && clip != null && clip.isRunning()) {
            System.out.println("🎵 Track already playing: " + filepath);
            return;
        }

        stop(); // stop previous track if any

        try {
            File audioFile = new File(filepath);
            if (!audioFile.exists()) {
                System.out.println("❌ File not found: " + audioFile.getAbsolutePath());
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            setVolume(currentVolume); // apply current volume

            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            currentTrack = filepath;

            System.out.println("✅ Music started: " + audioFile.getAbsolutePath());
        } catch (UnsupportedAudioFileException e) {
            System.out.println("❌ Unsupported audio file: " + filepath);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("❌ IO Error playing file: " + filepath);
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.out.println("❌ Audio line unavailable for file: " + filepath);
            e.printStackTrace();
        }
    }

    public void setVolume(float volume) {
        if (clip == null) {
            System.out.println("⚠️ No clip loaded to set volume.");
            return;
        }

        currentVolume = Math.max(0f, Math.min(volume, 1f)); // clamp 0..1

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        if (currentVolume == 0f) {
            gainControl.setValue(gainControl.getMinimum()); // mute
        } else {
            float dB = (float) (20.0 * Math.log10(currentVolume));
            dB = Math.max(gainControl.getMinimum(), Math.min(dB, gainControl.getMaximum()));
            gainControl.setValue(dB);
        }

        System.out.println("🔊 Volume set to " + (int)(currentVolume*100) + "% (dB: " + gainControl.getValue() + ")");
    }

    public float getVolume() {
        return currentVolume;
    }

    public void stop() {
        if (clip != null) {
            System.out.println("⏹ Stopping track: " + currentTrack);
            clip.stop();
            clip.close();
            clip = null;
            currentTrack = null;
        } else {
            System.out.println("⚠️ No track to stop.");
        }
    }

    // Convenience methods for your game
    public void playMainMusic() {
        System.out.println("🎮 Switching to MAIN music");
        playMusic("src/pixelshop/Untitled.wav");
    }

    public void playMiniRPGMusic() {
        System.out.println("🎮 Switching to MiniRPG music");
        playMusic("src/pixelshop/Welcoming_Tune.wav");
    }
}
