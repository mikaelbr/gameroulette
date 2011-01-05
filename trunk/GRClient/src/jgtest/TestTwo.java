/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgtest;

import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import jgame.JGObject;
import jgame.JGPoint;
import jgtest.ui.UIElements;
import jb2dtest.MultiplayerConnect;
import jgame.impl.JGEngineInterface;

/**
 *
 * @author mikaelbrevik
 */
public class TestTwo {

    private static JFrame main;
    private static SpaceRunIIIOpponent opponent;
    private static SpaceRunIII p1;

    public static void main(String[] args) {
//        MultiplayerConnect.connect();

        MultiplayerConnect.createMySelf();
        MultiplayerConnect.connect();

        main = new JFrame("Tester");
        main.setLayout(new BorderLayout());

        main.add(getScorePanel(), BorderLayout.NORTH);

        opponent = new SpaceRunIIIOpponent(new JGPoint(700, 400));
        p1 = new SpaceRunIII(new JGPoint(700, 400));
        opponent.setEnabled(false);
        

        MultiplayerConnect.setPlayer(p1);
        MultiplayerConnect.setOpponent(opponent);
        MultiplayerConnect.sendPosition();
        MultiplayerConnect.getPosition();


        main.add(opponent, BorderLayout.CENTER);
        main.add(p1, BorderLayout.SOUTH);

        main.setSize(700, 800);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setVisible(true);
    }


    public static JPanel getScorePanel() {
        JPanel scorePanel = new JPanel(new BorderLayout());

        scorePanel.add(UIElements.getInstance().getP1ScoreLabel(), BorderLayout.WEST);
        scorePanel.add(UIElements.getInstance().getP2ScoreLabel(), BorderLayout.EAST);

        return scorePanel;
    }
}
