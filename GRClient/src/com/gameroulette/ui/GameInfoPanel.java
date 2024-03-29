
package com.gameroulette.ui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This is the JPanel used as a seperator between the two
 * game engine windows.
 *
 * Holds information about time and score. Uses UIElements, a singleton
 * class for JLabels and JProgressBars used through out the project. 
 *
 * @author mikaelbrevik
 */
public class GameInfoPanel extends JPanel {

    // Load background image.
    private Image bgImg = new ImageIcon(this.getClass().getClassLoader().getResource("com/gameroulette/client/gfx/timepanel_bg.png")).getImage();

    public GameInfoPanel() {
        setLayout(new GridLayout(1, 3));
        add(UIElements.getInstance().getP1ScoreLabel());
        add(UIElements.getInstance().getTimerLabel());
        add(UIElements.getInstance().getP2ScoreLabel());
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(bgImg, 0, 0, null);
    }
}
