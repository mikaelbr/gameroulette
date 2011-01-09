/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgtest.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author mikaelbrevik
 */
public class StyledJPanel extends JPanel {

    private Image bgImg = new ImageIcon(HighscoreList.class.getResource("../gfx/highscore_bg.png")).getImage();
    private Font textFont = new Font(Font.DIALOG, Font.PLAIN, 20);
    private Font headerFont = new Font(Font.DIALOG, Font.BOLD, 30);

    private String title;

    public StyledJPanel(String gameTitle, LayoutManager layout) {
        super(layout);
        this.title = gameTitle;
        setBorder(BorderFactory.createEmptyBorder(130, 0, 0, 0));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(bgImg, 0, 0, null);

        FontMetrics fmText = getFontMetrics(textFont);
        FontMetrics fmHeader = getFontMetrics(headerFont);

        int width = getWidth();
        
        g.setFont(headerFont);
        g.setColor(Color.white);

        // Draw header
        g.drawString(title, width / 2 - fmHeader.stringWidth(title) / 2, 140+headerFont.getSize());
    }
}
