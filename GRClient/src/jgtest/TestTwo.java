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
import jgtest.ui.GameInfoPanel;

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

//        MultiplayerConnect.createMySelf();
//        MultiplayerConnect.connect();

        main = new JFrame("Tester");
        main.setLayout(new BorderLayout());

        main.add(new GameInfoPanel(), BorderLayout.CENTER);

        opponent = new SpaceRunIIIOpponent(new JGPoint(700, 400));
        p1 = new SpaceRunIII(new JGPoint(700, 400), opponent);
        opponent.setEnabled(false);

//        MultiplayerConnect.setPlayer(p1);
//        MultiplayerConnect.setOpponent(opponent);
//        MultiplayerConnect.sendPosition();
//        MultiplayerConnect.getPosition();


        main.add(opponent, BorderLayout.NORTH);
        main.add(p1, BorderLayout.SOUTH);

        main.setSize(690, 850);
        main.setResizable(false);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setVisible(true);
    }

}
