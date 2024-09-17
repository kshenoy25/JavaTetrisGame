package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.*;

public class Sound {
    Clip musicClip;
    URL url[] = new URL[10];

    public Sound() {
        // the sound here is supposed to be a wav but was downloaded as a mp3
        url[0] = getClass().getResource("/white-labyrinth-active.mp3");
        url[1] = getClass().getResource("/delete line.wav");
        url[2] = getClass().getResource("/gameover.wav");
        url[3] = getClass().getResource("/rotation.wav");
        url[4] = getClass().getResource("/touch floor.wav");

    }
    public void play(int i, boolean music) {

        try {
            // getting audio files and putting it in Clip object
            AudioInputStream ais = AudioSystem.getAudioInputStream(url[i]);
            Clip clip = AudioSystem.getClip();

            if (music){
                musicClip = clip;
            }
            clip.open(ais);
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                }
            });
            ais.close();
            clip.start();

        } catch (Exception e) {

        }
    }
    public void loop() {
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        musicClip.stop();
        musicClip.close();
    }
}
