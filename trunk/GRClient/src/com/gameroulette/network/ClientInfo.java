/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameroulette.network;

import java.io.Serializable;


/**
 *
 * @author mikaelbrevik
 */
public class ClientInfo implements Serializable {

    private double x = 0;
    private double y = 0;
    private int pfx = 0;
    private int pfy = 0;
    private int playerState = 0;
    private int score = 0;
    private int resetGame = 0;


    public ClientInfo() {
        
    }

    public ClientInfo(double x, double y, int pfx, int pfy, int playerState, int score, int resetGame) {
        this.x = x;
        this.y = y;
        this.pfx = pfx;
        this.pfy = pfy;
        this.playerState = playerState;
        this.score = score;
        this.resetGame = resetGame;
    }

    public int isResetGame() {
        return resetGame;
    }

    public void setResetGame(int resetGame) {
        this.resetGame = resetGame;
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

    public int getPlayerState() {
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

    public void setPlayerState(int playerState) {
        this.playerState = playerState;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "x: " + x +
                " y: " + y +
                " pfx: " + pfx +
                " pfy: " + pfy +
                " playerState: " + playerState +
                " score: " + score + "\n";
    }
}
