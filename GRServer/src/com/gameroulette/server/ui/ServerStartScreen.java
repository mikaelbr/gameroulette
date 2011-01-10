/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameroulette.server.ui;

import com.gameroulette.server.ServerClass;
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

/**
 *
 * @author Mariusk
 */
public class ServerStartScreen extends JFrame implements ActionListener {

    private JButton button;
    private JPanel mainPanel;
    private JLabel portLabel;
    private JTextField portField;
    private JLabel globalipLabel;
    private JTextField globalipField;
    private JLabel localipLabel;
    private JTextField localipField;
    private static JFrame main;
    private ServerClass nif;
    private int intport = 0;

    public ServerStartScreen() {

        portLabel = new JLabel();
        portLabel.setText("Portnumber:");
        portField = new JTextField(5);
        portField.setText("4783");

        button = new JButton("Start server!");

        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(0, 10, 0, 0);
        mainPanel.add(portLabel, c);

        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(0, 10, 0, 10);
        mainPanel.add(portField, c);

        c.gridx = 1;
        c.weightx = 0.5;
        c.gridy = 1;
        c.gridwidth = 2;
        c.insets = new Insets(10, 60, 0, 10);
        mainPanel.add(button, c);
        add(mainPanel);
        button.addActionListener(this);
        setTitle("GameRouletteServer");
    }

    public JButton getButton() {
        return button;
    }

    public void actionPerformed(ActionEvent ae) {
        String portnr = portField.getText();
        try {
            intport = Integer.parseInt(portnr);
        } catch (Exception e) {
            portField.setForeground(Color.red);
            return;
        }
        portField.setForeground(Color.black);
        mainPanel.removeAll();

        ServerClass.startServer(intport);

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;

        // GlobalIPlabel style
        globalipLabel = new JLabel();
        globalipLabel.setText("Global IP: ");
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.insets = new Insets(0, 10, 0, 0);
        mainPanel.add(globalipLabel, c);

        //globalIPField style
        globalipField = new JTextField();
        globalipField.setText(ServerClass.getGlobalIP());
        globalipField.setEditable(false);
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 0.1;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 10);
        mainPanel.add(globalipField, c);

        // localipLabel style
        localipLabel = new JLabel();
        localipLabel.setText("Local IP: ");
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.insets = new Insets(0, 10, 0, 0);
        mainPanel.add(localipLabel, c);

        // localipField style
        localipField = new JTextField();
        localipField.setText(ServerClass.getLocalIP());
        localipField.setEditable(false);
        c.gridx = 2;
        c.gridy = 1;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 10);
        mainPanel.add(localipField, c);

        // PortLabel style
        portLabel.setText("Portnumber: ");
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.insets = new Insets(0, 10, 0, 0);
        mainPanel.add(portLabel, c);

        // Portfield style
        portField.setText(portnr);
        c.gridx = 2;
        c.gridy = 2;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 10);
        mainPanel.add(portField, c);

        // Button style
        button = new JButton("Quit Server!");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.RELATIVE;
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0;
        c.gridwidth = 4;
        c.insets = new Insets(10, 0, 0, 0);
        mainPanel.add(button, c);

        this.setSize(300, 160);
        mainPanel.revalidate();
    }
}
