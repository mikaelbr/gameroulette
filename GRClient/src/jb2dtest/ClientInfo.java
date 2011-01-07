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


    public ClientInfo() {
        
    }

    public ClientInfo(double x, double y, int pfx, int pfy, String playerState, int score) {
        this.x = x;
        this.y = y;
        this.pfx = pfx;
        this.pfy = pfy;
        this.playerState = playerState;
        this.score = score;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int getPfx() {
        return this.pfx;
    }

    public int getPfy() {
        return this.pfy;
    }

    public String getPlayerState() {
       return this.playerState;
    }

    public int getScore() {
        return this.score;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setPfx(int pfx) {
        this.pfx = pfx;
    }

    public void setPfy(int pfy) {
        this.pfy = pfy;
    }

    public void setPlayerState(String playerState) {
        this.playerState = playerState;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")\n";
    }
}
