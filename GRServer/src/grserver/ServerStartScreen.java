/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grserver;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
    private JTextField globalipField;
    private JLabel localipLabel;
    private JTextField localipField;
    private static JFrame main;
    private ServerClass nif;

    public ServerStartScreen() {

        portLabel = new JLabel();
        portLabel.setText("Portnumber:");
        portField = new JTextField(5);
        portField.setText("4783");
        portField.setMinimumSize(new Dimension(40, 40));

        button = new JButton("Start server!");

        mainPanel = new JPanel(new BorderLayout());
        statusPanel = new JPanel(new GridLayout(1, 2));
        statusPanel.add(portLabel);
        statusPanel.add(portField);
        mainPanel.add(button, BorderLayout.SOUTH);

        add(statusPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        button.addActionListener(this);
        setTitle("GameRouletteServer");
    }

    public JButton getButton() {
        return button;
    }

    public void actionPerformed(ActionEvent ae) {

        mainPanel.removeAll();
        statusPanel.removeAll();
        statusPanel.setLayout(new GridLayout(3, 2));
        String portnr = portField.getText();
        int intport = Integer.parseInt(portnr);
        portLabel.setText("Portnumber: ");
        portField.setText(portnr);

        globalipLabel = new JLabel();
        globalipField = new JTextField();
        globalipLabel.setText("Global IP: ");
        globalipField.setText(ServerClass.getGlobalIP());
        globalipField.setEditable(false);

        localipLabel = new JLabel();
        localipField = new JTextField();
        localipLabel.setText("Local IP: ");
        localipField.setText(ServerClass.getLocalIP());
        localipField.setEditable(false);

        button = new JButton("Quit Server!");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        ServerClass.startServer(intport);

        statusPanel.add(globalipLabel);
        statusPanel.add(globalipField);
        statusPanel.add(localipLabel);
        statusPanel.add(localipField);
        statusPanel.add(portLabel);
        statusPanel.add(portField);
        mainPanel.add(button, BorderLayout.SOUTH);
        this.setSize(250, 140);
        mainPanel.revalidate();
        statusPanel.revalidate();
    }
}
