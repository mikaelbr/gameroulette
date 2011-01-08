/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgtest;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import jb2dtest.MultiplayerConnect;
import jgame.JGPoint;
import jgtest.ui.GameInfoPanel;

/**
 *
 * @author Mariusk
 */
public class StartScreen extends JFrame implements ActionListener {

    JButton startGame;
    JPanel mainPanel;
    JLabel usernameLabel;
    JLabel ipLabel;
    JLabel portLabel;
    JTextField usernameField;
    JTextField ipField;
    JTextField portField;
    private static JFrame main;
    private static SpaceRunIIIOpponent opponent;
    private static SpaceRunIII p1;

    public StartScreen() {

        usernameLabel = new JLabel();
        usernameLabel.setText("Nickname:");
        usernameField = new JTextField(10);

        ipLabel = new JLabel();
        ipLabel.setText("Server IP:");
        ipField = new JTextField(15);
        ipField.setText("192.168.1.5");

        portLabel = new JLabel();
        portLabel.setText("Server port.:");
        portField = new JTextField(5);
        portField.setText("4783");

        startGame = new JButton("Look for opponent!");

        mainPanel = new JPanel(new GridLayout(4, 3));
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(ipLabel);
        mainPanel.add(ipField);
        mainPanel.add(portLabel);
        mainPanel.add(portField);
        mainPanel.add(startGame);

        add(mainPanel, BorderLayout.CENTER);

        startGame.addActionListener(this);
        setTitle("GameRoulette");
    }

    public JButton getStartGame() {
        return startGame;
    }

    public void actionPerformed(ActionEvent ae) {
        String nickname = usernameField.getText();
        String serverIP = ipField.getText();
        String portnr = portField.getText();
        int intPort = Integer.parseInt(portnr);
        MultiplayerConnect.createMySelf(serverIP, intPort, nickname);
        MultiplayerConnect.connect();

        main = new JFrame("Tester");
        main.setLayout(new BorderLayout());

        main.add(new GameInfoPanel(), BorderLayout.CENTER);

        opponent = new SpaceRunIIIOpponent(new JGPoint(700, 400));
        p1 = new SpaceRunIII(new JGPoint(700, 400));
        opponent.setEnabled(false);

        MultiplayerConnect.setPlayer(p1);
        MultiplayerConnect.setOpponent(opponent);
        MultiplayerConnect.sendPosition();
        MultiplayerConnect.getPosition();

        main.add(opponent, BorderLayout.NORTH);
        main.add(p1, BorderLayout.SOUTH);

        main.setSize(700, 800);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setVisible(true);
    }
}
