/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jb2dtest;

import java.io.Serializable;


/**
 *
 * @author mikaelbrevik
 */
public class ClientInfo implements Serializable {

    private double x;
    private double y;
    private int pfx;
    private int pfy;
    private String playerState;
    private int score;


    public ClientInfo(double x, double y, int pfx, int pfy, String playerState, int score) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
