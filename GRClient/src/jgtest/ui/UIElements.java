/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgtest.ui;

import javax.swing.JLabel;

/**
 *
 * @author mikaelbrevik
 */
public class UIElements {

    private JLabel p2ScoreLabel = new JLabel("Score: 0");
    private JLabel p1ScoreLabel = new JLabel("Score: 0");

    private static UIElements _inst = null;

    private UIElements() {
    }

    private static class SingletonHolder {
        public static final UIElements INSTANCE = new UIElements();
    }

    public static UIElements getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public JLabel getP2ScoreLabel () {
        return p2ScoreLabel;
    }

    public void setP2Score (int score) {
        p2ScoreLabel.setText("P2 Score: " + score);
    }

    public JLabel getP1ScoreLabel () {
        return p1ScoreLabel;
    }

    public void setP1Score (int score) {
        p1ScoreLabel.setText("P1 Score: " + score);
    }
}
