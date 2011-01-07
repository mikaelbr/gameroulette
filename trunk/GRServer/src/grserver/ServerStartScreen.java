/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grserver;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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
    private JPanel statusPanel;
    private JLabel portLabel;
    private JTextField portField;
    private JLabel globalipLabel;
    private JLabel localipLabel;
    private static JFrame main;
    private ServerClass nif;

    public ServerStartScreen() {

        portLabel = new JLabel();
        portLabel.setText("Portnumber:");
        portField = new JTextField(5);
        portField.setText("4783");
        portField.setSize(40, 10);

        button = new JButton("Start server!");

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(portLabel, BorderLayout.WEST);
        mainPanel.add(portField, BorderLayout.EAST);
        mainPanel.add(button, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        button.addActionListener(this);
        setTitle("GameRouletteServer");
    }

    public JButton getButton() {
        return button;
    }

    public void actionPerformed(ActionEvent ae) {

        mainPanel.removeAll();
        mainPanel.setLayout(new GridLayout(4, 1));
        String portnr = portField.getText();
        int intport = Integer.parseInt(portnr);
        portLabel.setText("Portnumber: " + portnr);
        globalipLabel = new JLabel();
        globalipLabel.setText("Global IP: " + ServerClass.getGlobalIP());
        localipLabel = new JLabel();
        localipLabel.setText("Local IP: " + ServerClass.getLocalIP());

        button = new JButton("Quit Server!");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }

        });

        ServerClass.startServer(intport);

        mainPanel.add(globalipLabel);
        mainPanel.add(localipLabel);
        mainPanel.add(portLabel);
        mainPanel.add(button);
        mainPanel.revalidate();
    }
    
}
