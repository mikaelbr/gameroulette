

package com.gameroulette.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A JFrame with simulare properties as HighscoreList.
 * Will show results from previous match. Opponent/Player score, win/loss.
 *
 * Will auto show and dispose on click. 
 *
 * @see HighscoreList
 * @author mikaelbrevik
 */
public class MatchResultFrame extends JFrame {

    private JFrame parent = this;
    private int playerScore = 0;
    private int opponentScore = 0;

    public MatchResultFrame(int playerScore, int opponentScore) {

        addMouseListener(new MouseOperations());
        this.opponentScore = opponentScore;
        this.playerScore = playerScore;
        setTitle("Highscore");
        setLayout(new BorderLayout());

        setSize(500, 400);
        setResizable(false);
        setUndecorated(true);

        add(new MatchResultPanel("GameRoulette Match Results"));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class MouseOperations implements MouseListener {

        public void mouseClicked(MouseEvent e) {
            parent.setVisible(false);
            parent.removeAll();
            parent.dispose();
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }

    private class MatchResultPanel extends JPanel {

        private Image bgImg = new ImageIcon(this.getClass().getClassLoader().getResource("com/gameroulette/client/gfx/highscore_bg.png")).getImage();
        private Font textFont = new Font(Font.DIALOG, Font.PLAIN, 20);
        private Font headerFont = new Font(Font.DIALOG, Font.BOLD, 30);
        private String title;

        public MatchResultPanel(String gameTitle) {
            this.title = gameTitle;
            setBorder(BorderFactory.createEmptyBorder(130, 0, 0, 0));
        }

        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(bgImg, 0, 0, null);

            FontMetrics fmText = getFontMetrics(textFont);
            FontMetrics fmHeader = getFontMetrics(headerFont);

            int width = getWidth();
            int margin = 20;

            g.setFont(headerFont);
            g.setColor(Color.white);

            // Draw header
            g.drawString(title, width / 2 - fmHeader.stringWidth(title) / 2, 140 + headerFont.getSize());

            g.setFont(textFont);

            g.drawString("You " + ((playerScore >= opponentScore) ? "won" : "lost") + " this match.", margin, 140 + headerFont.getSize() + textFont.getSize() + margin);
            g.drawString("Your score ", margin, 140 + headerFont.getSize() + textFont.getSize() * 2 + margin * 2);
            g.drawString(playerScore + "", width - margin - fmText.stringWidth(playerScore + ""), 140 + headerFont.getSize() + textFont.getSize() * 2 + margin * 2);

            int y = 140 + headerFont.getSize() + textFont.getSize() * 2 + margin * 2 + margin / 2;
            g.setColor(new Color(53, 53, 53));
            g.drawLine(margin, y, width - margin, y);

            g.setColor(Color.white);
            g.drawString("Opponent score ", margin, 140 + headerFont.getSize() + textFont.getSize() * 3 + margin * 3);
            g.drawString(opponentScore + "", width - margin - fmText.stringWidth(opponentScore + ""), 140 + headerFont.getSize() + textFont.getSize() * 3 + margin * 3);
            if (playerScore >= opponentScore) {
                g.drawString("Your score will be added to your total.", margin, 140 + headerFont.getSize() + textFont.getSize() * 5 + margin * 5);
            }
        }
    }
}
