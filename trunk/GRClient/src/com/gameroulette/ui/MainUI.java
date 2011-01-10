/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameroulette.ui;

import javax.swing.JFrame;

/**
 *
 * @author Mariusk
 */
public class MainUI {

    

    public static void main(String[] args) {

        StartScreen scr = new StartScreen();

        scr.setSize(330, 380);
        scr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scr.setResizable(false);
        scr.setVisible(true);
    }

   
}
