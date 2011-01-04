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

/**
 *
 * @author mikaelbrevik
 */
public class TestTwo {

    private static JFrame main;
    private static SpaceRunIIIOpponent opponent;
    private static SpaceRunIII p1;

    public static void main(String[] args) {
// JGame is already running on this VM
//        new SpaceRunIII(new JGPoint(700, 300));
//        new SpaceRunIII(new JGPoint(700, 300));

        MultiplayerConnect.connect();

        main = new JFrame("Tester");
        main.setLayout(new BorderLayout());

        main.add(getScorePanel(), BorderLayout.NORTH);

        opponent = new SpaceRunIIIOpponent(new JGPoint(700, 300));
        p1 = new SpaceRunIII(new JGPoint(700, 300));

        MultiplayerConnect.setPlayer(p1);
        MultiplayerConnect.setOpponent(opponent);
        MultiplayerConnect.sendPosition();
        MultiplayerConnect.getPosition();


        main.add(opponent, BorderLayout.CENTER);
        main.add(p1, BorderLayout.SOUTH);

        main.setSize(700, 600);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setVisible(true);
    }

    public static void writeCoordinatesPlayer() {
        new Thread(new Runnable() {

            public void run() {
                System.out.println("HER??");
                while (p1.getPlayer() == null) {
                    System.out.println("Heeer11");
                }
                while (true) {
                    System.out.println("(" + p1.getPlayer().x + ", " + p1.getPlayer().y + ")");
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(TestTwo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }

    public static void getCoordinatesOpponent() {
        new Thread(new Runnable() {

            public void run() {
                System.out.println("HER222??");

                while (opponent.getPlayer() == null) {
                    System.out.println("Heeer22");
                }
                while (true) {
                    System.out.println("(" + opponent.getPlayer().x + ", " + opponent.getPlayer().y + ")");
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(TestTwo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }

    public static JPanel getScorePanel() {
        JPanel scorePanel = new JPanel(new BorderLayout());

        scorePanel.add(UIElements.getInstance().getP1ScoreLabel(), BorderLayout.WEST);
        scorePanel.add(UIElements.getInstance().getP2ScoreLabel(), BorderLayout.EAST);

        return scorePanel;
    }
}
