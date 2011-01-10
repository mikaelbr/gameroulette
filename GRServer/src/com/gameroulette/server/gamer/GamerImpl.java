
package com.gameroulette.server.gamer;

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
    private int score;
    private ArrayList<Gamer> previousGamers;
    private String IP;
    private boolean useLocalIP;
    private String[] levelDesign;

    public GamerImpl(String username, String IP, int port) throws RemoteException {
        this.username = username;
        this.port = port;
        this.IP = IP;
        gamerIdCount++;
        gamerid = gamerIdCount;
        status = GamerStatus.IDLE;
        useLocalIP = false;
    }

    public int getPort() {
        return port;
    }

    public synchronized String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public String[] getLevelDesign() {
        return levelDesign;
    }

    public void setLevelDesign(String[] level) {
        levelDesign = level;
    }

    public Color getColor() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<Gamer> getPreviousGamers() {
        return previousGamers;
    }

    public void addPreviousGamer(Gamer g) {
        previousGamers.add(g);
    }

    public void removePrevoiusGamer(Gamer g) {
        previousGamers.remove(g);
    }

    public boolean isPreviousGamer(Gamer g) throws RemoteException {
        for (Gamer g2 : previousGamers) {
            if (g.equals(g2)) {
                return true;
            }
        }
        return false;
    }

    public int getGamerId() {
        return gamerid;
    }

    public boolean equals(Gamer g) throws RemoteException {
        return (gamerid == g.getGamerId());
    }

    public GamerStatus getStatus() {
        return status;
    }

    public void setStatus(GamerStatus status) throws RemoteException {
        this.status = status;
    }

    public Gamer getOpponent() {
        return opponent;
    }

    public void setOpponent(Gamer opponent) throws RemoteException {
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
    public void setIP(String IP) throws RemoteException {
        this.IP = IP;
    }

    public boolean getUseLocalIP() throws RemoteException {
        return useLocalIP;
    }

    public void setUseLocalIP(boolean b) throws RemoteException {
        this.useLocalIP = b;
    }

    public void setScore(int score) throws RemoteException {
        this.score = score;
    }
}
