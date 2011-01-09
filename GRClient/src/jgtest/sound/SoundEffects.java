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
 * Play different tracks or sound effects for GameRoulette. Uses JavaZoom's JL Player. 
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

    /**
     * Play a sound given by URL.
     *
     * @param file
     * @throws IOException
     * @throws JavaLayerException
     */
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
                        if (allSounds.size() > 0) {
                            allSounds.remove(p);
                        }
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

    /**
     * Disable all sound. If there's music playing, this will stop.
     */
    public static void disableSound() {
        enabled = false;
        stopAllMusic();
    }

    /**
     * Enable sound (default, sound is enabled.)
     */
    public static void enableSound() {
        enabled = true;
    }

    /**
     * Check if sound is enabled or disabled.
     *
     * @return boolean
     */
    public static boolean isEnabled() {
        return enabled;
    }

    /**
     * Stop all sound or music from playing. Will not disable sound. 
     */
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

    /**
     * Play jump sound effect.
     */
    public static void jump() {
        try {
            playSound(SOUND_JUMP);
        } catch (IOException ex) {
            Logger.getLogger(SoundEffects.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JavaLayerException ex) {
            Logger.getLogger(SoundEffects.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Play a random track.
     */
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
