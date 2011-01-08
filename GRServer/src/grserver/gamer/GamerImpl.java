/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package grserver.gamer;

import rmi.stubbs.GamerStatus;
import rmi.stubbs.Color;
import rmi.stubbs.Gamer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author mikaelbrevik
 */
public class GamerImpl extends UnicastRemoteObject implements Gamer {

    private static int gamerIdCount;

    private GamerStatus status;
    private String username;
    private int port;
    private Gamer opponent;
    private int gamerid;
    private ArrayList<Gamer> previousGamers;
    private String IP;
    private boolean useLocalIP = false;



    public GamerImpl(String username, String IP, int port) throws RemoteException {
        this.username = username;
        this.port = port;
        this.IP = IP;
        gamerIdCount++;
        gamerid = gamerIdCount;
        status = GamerStatus.IDLE;
    }

    public int getPort() {
        return port;
    }

    public synchronized String getUsername() {
        return username;
    }

    public int getScore() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Color getColor() {
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

    public boolean equals(Gamer g) throws RemoteException {
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

    /**
     * @return the IP
     */
    public String getIP() {
        return IP;
    }

    /**
     * @param IP the IP to set
     */
    public void setIP(String IP) {
        this.IP = IP;
    }

    public boolean getUseLocalIP() {
        return this.useLocalIP;
    }
    
    public void setUseLocalIP(boolean b) {
        this.useLocalIP = b;
    }

}
