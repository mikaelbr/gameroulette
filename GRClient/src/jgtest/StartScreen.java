/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgtest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
    private int intPort;
    private static JFrame main;
    private static SpaceRunIIIOpponent opponent;
    private static SpaceRunIII p1;

    public StartScreen() {

        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        // usernameLabel style
        usernameLabel = new JLabel();
        usernameLabel.setText("Nickname:");
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.insets = new Insets(10, 10, 0, 0);
        mainPanel.add(usernameLabel, c);

        // usernameField style
        usernameField = new JTextField(10);
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 0.1;
        c.gridwidth = 2;
        c.insets = new Insets(10, 0, 0, 10);
        mainPanel.add(usernameField, c);

        // ipLabel style
        ipLabel = new JLabel();
        ipLabel.setText("Server IP:");
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.insets = new Insets(0, 10, 0, 0);
        mainPanel.add(ipLabel, c);

        // ipField style
        ipField = new JTextField(15);
        ipField.setText("192.168.1.5");
        c.gridx = 2;
        c.gridy = 1;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 10);
        mainPanel.add(ipField, c);

        // portLabel style
        portLabel = new JLabel();
        portLabel.setText("Server port.:");
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.insets = new Insets(0, 10, 0, 0);
        mainPanel.add(portLabel, c);

        // portField style
        portField = new JTextField(5);
        portField.setText("4783");
        c.gridx = 2;
        c.gridy = 2;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 10);
        mainPanel.add(portField, c);

        // startGame style
        startGame = new JButton("Proceed!");
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.RELATIVE;
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0;
        c.gridwidth = 4;
        c.insets = new Insets(10, 0, 0, 0);
        mainPanel.add(startGame, c);

        add(mainPanel);

        startGame.addActionListener(this);
        setTitle("GameRoulette");
    }

    public JButton getStartGame() {
        return startGame;
    }

    public void actionPerformed(ActionEvent ae) {

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        String nickname = usernameField.getText();
        String serverIP = ipField.getText();
        String portnr = portField.getText();

        if (nickname.length() == 0 || nickname.length() > 35) {
            usernameField.setForeground(Color.red);
            return;
        }
        try {
            intPort = Integer.parseInt(portnr);
        } catch (Exception ex) {
            portField.setForeground(Color.red);
            return;
        }

        try {
            MultiplayerConnect.createMySelf(serverIP, intPort, nickname);
        } catch (Exception ex) {
            ipField.setForeground(Color.red);
            return;
        }

        mainPanel.removeAll();

        JButton lookForOpponentButton = new JButton();
        lookForOpponentButton.setText("Look For Opponent");
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 0, 0, 0);
        mainPanel.add(lookForOpponentButton, c);

        JButton highscoreButton = new JButton();
        highscoreButton.setText("View Highscores");
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(10, 0, 0, 0);
        mainPanel.add(highscoreButton, c);
        mainPanel.repaint();
        mainPanel.revalidate();

        lookForOpponentButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                MultiplayerConnect.connect();

                main = new JFrame("GameName");
                main.setLayout(new BorderLayout());

                main.add(new GameInfoPanel(), BorderLayout.CENTER);

                opponent = new SpaceRunIIIOpponent(new JGPoint(700, 400));
                p1 = new SpaceRunIII(new JGPoint(700, 400), opponent);
                opponent.setEnabled(false);

                MultiplayerConnect.setPlayer(p1);
                MultiplayerConnect.setOpponent(opponent);
                MultiplayerConnect.sendPosition();
                MultiplayerConnect.getPosition();

                main.add(opponent, BorderLayout.NORTH);
                main.add(p1, BorderLayout.SOUTH);

                main.setSize(690, 850);
                main.setResizable(false);
                main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainPanel.setVisible(false);
                main.setVisible(true);
            }
        });

        highscoreButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                mainPanel.removeAll();
                ipLabel.setText("Skjer?");
                mainPanel.add(ipLabel);
                mainPanel.repaint();
                mainPanel.revalidate();

            }
        });





    }
}
