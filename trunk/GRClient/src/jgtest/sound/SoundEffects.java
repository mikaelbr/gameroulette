/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgtest.sound;

import java.io.IOException;
import java.net.URL;
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

    public static final URL SOUND_JUMP = SoundEffects.class.getResource("jump.mp3");
    public static final URL MUSIC_AXXO = SoundEffects.class.getResource("music/binaerpilot-axxo.mp3");
    public static final URL MUSIC_CORNERED = SoundEffects.class.getResource("music/binaerpilot-cornered_promo.mp3");
    public static final URL MUSIC_GOOF = SoundEffects.class.getResource("music/binaerpilot-goof.mp3");
    public static final URL MUSIC_UNDERGROUND = SoundEffects.class.getResource("music/binaerpilot-underground.mp3");

    public static void playSound(final URL file) throws IOException, JavaLayerException {

        new Thread(new Runnable() {

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
        }).start();
    }
    
    public static void jump () {
        try {
            playSound(SOUND_JUMP);
        } catch (IOException ex) {
            Logger.getLogger(SoundEffects.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JavaLayerException ex) {
            Logger.getLogger(SoundEffects.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void playRandomMusic () {
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
