/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgtest.ui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author mikaelbrevik
 */
public class GameInfoPanel extends JPanel {

    private Image bgImg = new ImageIcon(GameInfoPanel.class.getResource("../gfx/timepanel_bg.png")).getImage();
    

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
