/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameroulette.server.ui;

import javax.swing.JFrame;

/**
 *
 * @author mikaelbrevik
 */
public class Main {

    public static void main(String[] args) {

        ServerStartScreen scr = new ServerStartScreen();

        scr.setSize(260, 105);
        scr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scr.setResizable(false);
        scr.setVisible(true);
    }
}