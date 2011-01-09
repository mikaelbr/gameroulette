/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgtest.sound;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    private static List<Player> allSounds = Collections.synchronizedList(new ArrayList<Player>());

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
    public static void playMusic(final URL file) throws IOException, JavaLayerException {
        if (!enabled) {
            return;
        }

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

    public static void playSound(final URL file) throws IOException, JavaLayerException {
        if (!enabled) {
            return;
        }

        Thread t = new Thread(new Runnable() {

            public void run() {
                try {
                    try {
                        Player p = new Player(file.openStream());
                        p.play();
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
        new Thread(new Runnable() {

            public void run() {
                try {
                    for (Player p : allSounds) {
                        p.close();
                    }
                    allSounds.clear();
                } catch (Exception ex) {
                    System.out.println("Exception SOUND: " + ex);
                }
            }
        }).start();
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
            playMusic(randomMusic);
        } catch (IOException ex) {
            Logger.getLogger(SoundEffects.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JavaLayerException ex) {
            Logger.getLogger(SoundEffects.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
