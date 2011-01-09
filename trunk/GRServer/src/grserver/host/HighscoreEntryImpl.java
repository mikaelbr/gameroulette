/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grserver.host;

import rmi.stubbs.HighscoreEntry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author mikaelbrevik
 */
public class HighscoreEntryImpl extends UnicastRemoteObject implements HighscoreEntry {

    private String name;
    private int score;

    public HighscoreEntryImpl(String name, int score) throws RemoteException {
        this.name = name;
        this.score = score;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    public String toString() {
        return name + ": " + score;
    }
}