/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgtest.sound;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author mikaelbrevik
 */
public class SoundEffects {

    private static ArrayList<Player> allSounds = new ArrayList<Player>();
    private static boolean enabled = true;
    public static final URL SOUND_JUMP = SoundEffects.class.getResource("jump.mp3");
    public static final URL MUSIC_AXXO = SoundEffects.class.getResource("music/binaerpilot-axxo.mp3");
    public static final URL MUSIC_CORNERED = SoundEffects.class.getResource("music/binaerpilot-cornered_promo.mp3");
    public static final URL MUSIC_GOOF = SoundEffects.class.getResource("music/binaerpilot-goof.mp3");
    public static final URL MUSIC_UNDERGROUND = SoundEffects.class.getResource("music/binaerpilot-underground.mp3");

    public static void playSound(final URL file) throws IOException, JavaLayerException {
        if (!enabled) {
            return;
        }
        
        final int id = allSounds.size();
        Thread t = new Thread(new Runnable() {

            public void run() {
                try {
                    try {
                        Player p = new Player(file.openStream());
                        allSounds.add(p);
                        p.play();
                        allSounds.remove(p);
                    } catch (IOException ex) {
                        Logger.getLogger(SoundEffects.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (JavaLayerException ex) {
                    Logger.getLogger(SoundEffects.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();

    }

    public static void disableSound() {
        enabled = false;
        stopAllMusic();
    }

    public static void enableSound() {
        enabled = true;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void stopAllMusic() {
        for (Player p : allSounds) {
            p.close();
        }
        allSounds.clear();
    }

    public static void main(String[] args) throws InterruptedException {
        playRandomMusic();

        Thread.sleep(10 * 1000);

        stopAllMusic();
    }

    public static void jump() {
        try {
            playSound(SOUND_JUMP);
        } catch (IOException ex) {
            Logger.getLogger(SoundEffects.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JavaLayerException ex) {
            Logger.getLogger(SoundEffects.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void playRandomMusic() {
        try {
            URL[] allMusic = {MUSIC_AXXO, MUSIC_CORNERED, MUSIC_GOOF, MUSIC_UNDERGROUND};
            URL randomMusic = allMusic[new Random().nextInt(3)];
            playSound(randomMusic);
        } catch (IOException ex) {
            Logger.getLogger(SoundEffects.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JavaLayerException ex) {
            Logger.getLogger(SoundEffects.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
