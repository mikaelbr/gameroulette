/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgtest.ui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import jb2dtest.MultiplayerConnect;
import rmi.stubbs.Gamer;

/**
 *
 * @author mikaelbrevik
 */
public class UIElements {

    private Font textFont = new Font(Font.DIALOG, Font.BOLD, 20);
    private JLabel p2ScoreLabel = new JLabel("Score: 0");
    private JLabel p1ScoreLabel = new JLabel("Score: 0");
    private JLabel timeLabel = new JLabel("Time: ");
    private static UIElements _inst = null;

    private Gamer opponent = MultiplayerConnect.getOpponent();
    private Gamer mySelf = MultiplayerConnect.getMySelf();

    private UIElements() {

        p2ScoreLabel.setVerticalAlignment(JLabel.CENTER);
        p1ScoreLabel.setVerticalAlignment(JLabel.CENTER);
        timeLabel.setVerticalAlignment(JLabel.CENTER);

        timeLabel.setForeground(Color.white);
        p1ScoreLabel.setForeground(Color.white);
        p2ScoreLabel.setForeground(Color.white);

        timeLabel.setFont(textFont);
        p1ScoreLabel.setFont(textFont);
        p2ScoreLabel.setFont(textFont);

        timeLabel.setHorizontalAlignment(JLabel.CENTER);
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

    public void setP2Score(int score, int totScore) {
        p2ScoreLabel.setText(opponent.getUsername() + ": " + score + " pts (" + totScore + " pts)");
    }

    public JLabel getP1ScoreLabel() {
        return p1ScoreLabel;
    }

    public void setP1Score(int score,  int totScore) {
        p1ScoreLabel.setText(mySelf.getUsername() + ": " + score + " pts (" + totScore + " pts)");
    }

    public JLabel getTimerLabel() {
        return timeLabel;
    }

    public void setTime(int seconds) {
        timeLabel.setText("Time left: " + seconds + " seconds");
    }
}
