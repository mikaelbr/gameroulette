/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameroulette.ui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import rmi.stubbs.Gamer;

/**
 *
 * @author mikaelbrevik
 */
public class UIElements {

    private Font textFont = new Font(Font.DIALOG, Font.BOLD, 12);
    private JLabel p2ScoreLabel = new JLabel("Score: 0");
    private JLabel p1ScoreLabel = new JLabel("Score: 0");
    private JLabel totScoreLabel = new JLabel("Total score: 0");
    private JLabel timeLabel = new JLabel("Time: ");

    private JProgressBar progBar = new JProgressBar();

    private UIElements() {

        progBar.setIndeterminate(true);
        progBar.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        p2ScoreLabel.setVerticalAlignment(JLabel.CENTER);
        p1ScoreLabel.setVerticalAlignment(JLabel.CENTER);
        timeLabel.setVerticalAlignment(JLabel.CENTER);

        timeLabel.setForeground(Color.white);
        p1ScoreLabel.setForeground(Color.white);
        p2ScoreLabel.setForeground(Color.white);
        totScoreLabel.setForeground(Color.white);

        timeLabel.setFont(textFont);
        p1ScoreLabel.setFont(textFont);
        p2ScoreLabel.setFont(textFont);
        totScoreLabel.setFont(textFont);

        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        totScoreLabel.setHorizontalAlignment(JLabel.CENTER);
        p1ScoreLabel.setHorizontalAlignment(JLabel.LEFT);
        p2ScoreLabel.setHorizontalAlignment(JLabel.RIGHT);
    }

    private static class SingletonHolder {

        public static final UIElements INSTANCE = new UIElements();
    }

    public static UIElements getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public JLabel getP2ScoreLabel() {
        return p2ScoreLabel;
    }

    public JLabel getTotalScoreLabel () {
        return totScoreLabel;
    }

    public void setTotalScore (int totScore) {
        totScoreLabel.setText("Total score: " + totScore);
    }

    public JProgressBar getProgress() {
        return progBar;
    }

    public void showProgressBar (boolean show) {
        progBar.setVisible(show);
    }

    public void setP2Score(Gamer player, int score, int totScore) {
        try {
            p2ScoreLabel.setText(player.getUsername() + ": " + score + " pts (" + totScore + " pts)");
        } catch (Exception ex) {
            p2ScoreLabel.setText("P2 Score: " + score + " pts (" + totScore + " pts)");
        }
    }

    public JLabel getP1ScoreLabel() {
        return p1ScoreLabel;
    }

    public void setP1Score(Gamer player, int score, int totScore) {
        try {
            p1ScoreLabel.setText(player.getUsername() + ": " + score + " pts (" + totScore + " pts)");
        } catch (Exception ex) {
            p1ScoreLabel.setText("P1 Score: " + score + " pts (" + totScore + " pts)");
        }
    }

    public JLabel getTimerLabel() {
        return timeLabel;
    }

    public void setTime(int seconds) {
        timeLabel.setText("Time left: " + seconds + " seconds");
    }
}
