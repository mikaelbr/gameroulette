/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package grserver.gamer;

import grserver.gamer.rmi.Gamer;
import java.util.ArrayList;

/**
 *
 * @author mikaelbrevik
 */
public class GamerImpl implements Gamer {

    private static int gamerIdCount;

    private GamerStatus status;
    private String username;
    private int port;
    private Gamer opponent;
    private int gamerid;
    private ArrayList<Gamer> previousGamers;

    public GamerImpl(String username, int port) {
        this.username = username;
        this.port = port;
        gamerIdCount++;
        gamerid = gamerIdCount;
        status = GamerStatus.IDLE;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Color getColor() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void write(String msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<Gamer> getPreviousGamers() {
        return previousGamers;
    }

    public void addPreviousGamer (Gamer g) {
        previousGamers.add(g);
    }

    public void removePrevoiusGamer (Gamer g) {
        previousGamers.remove(g);
    }

    public boolean isPreviousGamer(Gamer g) {
        for (Gamer g2 : previousGamers) {
            if(g.equals(g2)) return true;
        }
        return false;
    }
    
    public int getGamerId () {
        return gamerid;
    }

    public boolean equals(Gamer g) {
        return (gamerid == g.getGamerId());
    }

    public GamerStatus getStatus() {
        return status;
    }

    public void setStatus(GamerStatus status) {
        this.status = status;
    }


    public Gamer getOpponent () {
        return opponent;
    }

    public void setOpponent (Gamer opponent) {
        this.opponent = opponent;
    }

}
