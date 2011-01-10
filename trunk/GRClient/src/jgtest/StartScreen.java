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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import jb2dtest.MultiplayerConnect;
import jgame.JGPoint;
import jgtest.sound.SoundEffects;
import jgtest.ui.GameInfoPanel;
import jgtest.ui.HighscoreList;
import jgtest.ui.MatchResultFrame;
import jgtest.ui.StyledJPanel;
import jgtest.ui.UIElements;
import rmi.stubbs.GamerStatus;

/**
 *
 * @author Mariusk
 */
public class StartScreen extends JFrame implements ActionListener {

    JButton startGame;
    private JButton highscoreButton;
    StyledJPanel mainPanel;
    JLabel usernameLabel;
    JLabel ipLabel;
    JLabel portLabel;
    JLabel binaerLabel;
    JTextField usernameField;
    JTextField ipField;
    JTextField portField;
    private int intPort;
    private static JFrame main;
    private static SpaceRunIIIOpponent opponent;
    private static SpaceRunIII p1;
    private HighscoreList highscore;

    public StartScreen() {

        mainPanel = new StyledJPanel("GameRoulette", new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        KeyListener enterKey = new KeyOperations();

        // usernameLabel style
        usernameLabel = new JLabel();
        usernameLabel.setText("Nickname:");
        usernameLabel.setForeground(Color.white);
        c.anchor = GridBagConstraints.SOUTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.insets = new Insets(10, 10, 0, 0);
        mainPanel.add(usernameLabel, c);

        // usernameField style
        usernameField = new JTextField(10);
        usernameField.addKeyListener(enterKey);
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 0.1;
        c.gridwidth = 2;
        c.insets = new Insets(10, 0, 0, 10);
        mainPanel.add(usernameField, c);

        // ipLabel style
        ipLabel = new JLabel();
        ipLabel.setText("Server IP:");
        ipLabel.setForeground(Color.white);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.insets = new Insets(0, 10, 0, 0);
        mainPanel.add(ipLabel, c);

        // ipField style
        ipField = new JTextField(15);
        ipField.setText("192.168.1.7");
        ipField.addKeyListener(enterKey);
        c.gridx = 2;
        c.gridy = 1;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 10);
        mainPanel.add(ipField, c);

        // portLabel style
        portLabel = new JLabel();
        portLabel.setText("Server port.:");
        portLabel.setForeground(Color.white);
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.insets = new Insets(0, 10, 0, 0);
        mainPanel.add(portLabel, c);

        // portField style
        portField = new JTextField(5);
        portField.setText("4783");
        portField.addKeyListener(enterKey);
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

        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0;
        c.weighty = 0;
        c.gridwidth = 4;
        c.insets = new Insets(10, 10, 0, 10);
        UIElements.getInstance().showProgressBar(false);
        mainPanel.add(UIElements.getInstance().getProgress(), c);

        add(mainPanel);

        // Add Window Listener to mainPanel for removing gamer from server on quit.
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    if (MultiplayerConnect.getMySelf() != null) {
                        MultiplayerConnect.getMySelf().setStatus(GamerStatus.IDLE);
                        MultiplayerConnect.removeMySelf();
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(StartScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    if (MultiplayerConnect.getMySelf() != null) {

                        MultiplayerConnect.getMySelf().setStatus(GamerStatus.IDLE);
                        MultiplayerConnect.removeMySelf();
                    }

                } catch (RemoteException ex) {
                    Logger.getLogger(StartScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        startGame.addActionListener(this);
        setTitle("GameRoulette");
    }

    private class KeyOperations implements KeyListener {

        public void keyTyped(KeyEvent e) {
//            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                startGame.doClick();
            }
        }

        public void keyReleased(KeyEvent e) {
//            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public JButton getStartGame() {
        return startGame;
    }

    public void actionPerformed(ActionEvent ae) {

        Thread t = new Thread(new Runnable() {

            public void run() {
                GridBagConstraints c = new GridBagConstraints();
                c.fill = GridBagConstraints.HORIZONTAL;

                String nickname = usernameField.getText();
                String serverIP = ipField.getText();
                String portnr = portField.getText();

                if (nickname.length() < 4 || nickname.length() > 35) {
                    usernameField.setForeground(Color.red);
                    usernameLabel.setForeground(Color.red);
                    return;
                }

                usernameField.setForeground(Color.black);
                usernameLabel.setForeground(Color.white);

                try {
                    if (serverIP.length() < 7 || portnr.length() < 4) {
                        throw new Exception();
                    }
                    intPort = Integer.parseInt(portnr);


                    UIElements.getInstance().showProgressBar(true);
                    startGame.setVisible(false);
                    MultiplayerConnect.createMySelf(serverIP, intPort, nickname);
                    UIElements.getInstance().showProgressBar(false);

                } catch (Exception ex) {
                    portField.setForeground(Color.red);
                    portLabel.setForeground(Color.red);
                    ipField.setForeground(Color.red);
                    ipLabel.setForeground(Color.red);
                    UIElements.getInstance().showProgressBar(false);
                    startGame.setVisible(true);

                    return;
                }

                mainPanel.removeAll();

//                int score = MultiplayerConnect.getMySelf().getScore();

                final JButton lookForOpponentButton = new JButton();
                lookForOpponentButton.setText("Look For Opponent");
                c.anchor = GridBagConstraints.CENTER;
                c.gridx = 0;
                c.gridy = 0;
                c.insets = new Insets(30, 0, 0, 0);
                mainPanel.add(lookForOpponentButton, c);

                c.anchor = GridBagConstraints.CENTER;
                c.gridx = 0;
                c.gridy = 0;
                c.insets = new Insets(10, 0, 0, 0);
                UIElements.getInstance().showProgressBar(false);
                mainPanel.add(UIElements.getInstance().getProgress(), c);

                highscoreButton = new JButton();
                highscoreButton.setText("View Highscores");
                c.gridx = 0;
                c.gridy = 1;
                c.insets = new Insets(10, 0, 0, 0);
                mainPanel.add(highscoreButton, c);

                final JButton saveGameButton = new JButton();
                saveGameButton.setText("Save Score");
                c.gridx = 0;
                c.gridy = 2;
                c.insets = new Insets(10, 0, 0, 0);
                mainPanel.add(saveGameButton, c);

                try {
                    if (MultiplayerConnect.getMySelf().getScore() == 0) {
                        saveGameButton.setVisible(false);
                    }
                } catch (Exception ex) {
                    System.out.println("Exception: " + ex);
                }

                c.gridx = 0;
                c.gridy = 3;
                c.insets = new Insets(10, 0, 0, 0);
                mainPanel.add(UIElements.getInstance().getTotalScoreLabel(), c);

                binaerLabel = new JLabel();
                binaerLabel.setText("Music by Binärpilot");
                binaerLabel.setForeground(Color.GRAY);
                c.anchor = GridBagConstraints.PAGE_END;
                c.fill = GridBagConstraints.RELATIVE;
                c.gridx = 0;
                c.gridy = 4;
                mainPanel.add(binaerLabel, c);

                mainPanel.repaint();
                mainPanel.revalidate();

                lookForOpponentButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent ae) {
                        Thread t = new Thread(new Runnable() {

                            public void run() {
                                lookForOpponentButton.setVisible(false);
                                UIElements.getInstance().showProgressBar(true);
                                MultiplayerConnect.connect();
                                UIElements.getInstance().showProgressBar(false);
                                lookForOpponentButton.setVisible(true);

                                lookForOpponentButton.setEnabled(false);

                                main = new JFrame("GameRoulette");
                                main.setLayout(new BorderLayout());

                                main.add(new GameInfoPanel(), BorderLayout.CENTER);

                                GamerScore score = new GamerScore(0);
                                GamerScore score2 = new GamerScore(0);
                                try {
                                    score = new GamerScore(MultiplayerConnect.getMySelf().getScore());
                                    score2 = new GamerScore(MultiplayerConnect.getMySelf().getOpponent().getScore());
                                } catch (RemoteException ex) {
                                    Logger.getLogger(StartScreen.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                opponent = new SpaceRunIIIOpponent(new JGPoint(700, 400), score2);
                                p1 = new SpaceRunIII(new JGPoint(700, 400), opponent, score, main);


                                WindowListener listm = new WindowAdapter() {

                                    public void windowClosing(WindowEvent e) {
                                        System.out.println("WindowClosing, Linje 349");
                                        windowClosed(e);
                                    }

                                    @Override
                                    public void windowClosed(WindowEvent e) {
                                        System.out.println("Linje 354");
                                        // Show splash screen with results from match.
                                        new MatchResultFrame(p1.getClientInfo().getScore(), opponent.getClientInfo().getScore());

                                        try {
                                            // Remove gamer from IN_GAME que at server. 
                                            MultiplayerConnect.getMySelf().setStatus(GamerStatus.IDLE);

                                        } catch (RemoteException ex) {
                                        }

                                        System.out.println("Linje 365");

                                        lookForOpponentButton.setEnabled(true);

                                        SoundEffects.stopAllMusic();
                                        opponent.destroyApp(true);
                                        p1.destroyApp(true);
                                        try {
                                            if (MultiplayerConnect.getMySelf().getScore() != 0) {
                                                saveGameButton.setVisible(true);
                                            }
                                        } catch (Exception ex) {
                                            System.out.println("Exception: " + ex);
                                        }
                                        UIElements.getInstance().setTotalScore(p1.getTotalScore().getTotalScore());
                                        UIElements.getInstance().getTotalScoreLabel().repaint();
                                        UIElements.getInstance().getTotalScoreLabel().revalidate();
                                        mainPanel.repaint();
                                        mainPanel.revalidate();

                                        System.out.println("Linje 385");
                                        MultiplayerConnect.closeConnection();
                                        System.out.println("Linje 387");
                                    }
                                };
                                main.addWindowListener(listm);

                                opponent.setEnabled(false);

                                MultiplayerConnect.setPlayer(p1);
                                MultiplayerConnect.setOpponent(opponent);
                                MultiplayerConnect.sendPosition();
                                MultiplayerConnect.getPosition();

                                main.add(opponent, BorderLayout.NORTH);
                                main.add(p1, BorderLayout.SOUTH);

                                main.setSize(690, 850);
                                main.setResizable(false);
//                                main.setDefaultCloseOperation();
                                main.setLocationRelativeTo(null);
                                main.setVisible(true);
                            }
                        });
                        t.start();
                    }
                });

                highscoreButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent ae) {
                        try {
                            if (highscore != null && !highscore.isVisible()) {
                                highscore = null;
                            }
                            if (highscore != null) {
                                highscore.setVisible(false);
                                highscore.removeAll();
                                highscore.dispose();
                                highscore = null;
                            } else {
                                highscore = new HighscoreList(MultiplayerConnect.getHighscoreList());
                            }
                        } catch (RemoteException ex) {
                            JOptionPane.showMessageDialog(mainPanel, "Error opening highscores.");
                        }
                    }
                });

                saveGameButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent ae) {
                        try {
                            MultiplayerConnect.saveGame();
                            UIElements.getInstance().setTotalScore(0);
                            saveGameButton.setVisible(false);
                            highscoreButton.doClick();
                            mainPanel.repaint();
                            mainPanel.revalidate();
                        } catch (RemoteException ex) {
                            Logger.getLogger(StartScreen.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });


            }
        });

        t.start();

    }
}
