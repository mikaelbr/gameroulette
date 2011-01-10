/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameroulette.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import rmi.stubbs.HighscoreEntry;

/**
 *
 * @author mikaelbrevik
 */
public class HighscoreList extends JFrame {

    private ArrayList<HighscoreEntry> highscores;
    private JFrame parent = this;

    public HighscoreList(ArrayList<HighscoreEntry> highscores) {
        this.highscores = highscores;

        addMouseListener(new MouseOperations());

        setTitle("Highscore");
        setLayout(new BorderLayout());

        add(new HighscorePanel(), BorderLayout.CENTER);

        setSize(500, 700);
        setResizable(false);
        setUndecorated(true);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class MouseOperations implements MouseListener {

        public void mouseClicked(MouseEvent e) {
            System.out.println("Her.");
            parent.setVisible(false);
            parent.removeAll();
            parent.dispose();
//            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void mousePressed(MouseEvent e) {
//            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void mouseReleased(MouseEvent e) {
//            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void mouseEntered(MouseEvent e) {
//            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void mouseExited(MouseEvent e) {
//            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    private class HighscorePanel extends JPanel {

        private Image bgImg = new ImageIcon(this.getClass().getClassLoader().getResource("com/gameroulette/client/gfx/highscore_bg.png")).getImage();
        private Font textFont = new Font(Font.DIALOG, Font.PLAIN, 20);
        private Font headerFont = new Font(Font.DIALOG, Font.BOLD, 30);

        public HighscorePanel() {
            Collections.sort(highscores, new Comparator<HighscoreEntry>() {

                public int compare(HighscoreEntry e1, HighscoreEntry e2) {
                    try {
                        if (e2.getScore() > e1.getScore()) {
                            return 1;
                        }
                        if (e2.getScore() < e1.getScore()) {
                            return -1;
                        }
                        return e1.getName().compareTo(e2.getName());
                    } catch (RemoteException ex) {
                        return 0;
                    }
                }
            });

            Dimension size = new Dimension(bgImg.getWidth(null), bgImg.getHeight(null));
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setSize(size);
            setLayout(null);
        }

        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(bgImg, 0, 0, null);

            FontMetrics fmText = getFontMetrics(textFont);
            FontMetrics fmHeader = getFontMetrics(headerFont);

            int width = bgImg.getWidth(null);
            int scoreStartY = 190;
            int marginBottom = 20;

            g.setFont(headerFont);
            g.setColor(Color.white);

            // Draw header
            String title = "Highscores";
            g.drawString(title, width / 2 - fmHeader.stringWidth(title) / 2, 150);

            g.setFont(textFont);

            int limit = (highscores.size() >= 10) ? 10 : highscores.size();

            for (int i = 0; i < limit; i++) {
                try {
                    HighscoreEntry entry = highscores.get(i);
                    if (entry != null) {
                        int y = scoreStartY + i * (textFont.getSize() + marginBottom);

                        // Text to write
                        String score = entry.getScore() + "";
                        String name = "#" + (i + 1) + "  " + entry.getName();

                        if (name.length() > 35) {
                            name = name.substring(0, 35);
                        }

                        // Set color
                        g.setColor(Color.WHITE);

                        g.drawString(name, marginBottom, y);
                        g.drawString(score, width - marginBottom - fmText.stringWidth(score), y);

                        // Draw line (seperator)
                        g.setColor(new Color(53, 53, 53));
                        g.drawLine(20, y + textFont.getSize() - 10, width - marginBottom, y + textFont.getSize() - 10);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
