/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgtest.ui;

import javax.swing.JFrame;
import jgtest.StartScreen;

/**
 *
 * @author Mariusk
 */
public class MainUI {

    

    public static void main(String[] args) {

        StartScreen scr = new StartScreen();

        scr.setSize(330, 360);
        scr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scr.setResizable(false);
        scr.setVisible(true);
    }

   
}
