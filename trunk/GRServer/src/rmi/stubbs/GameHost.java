/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.stubbs;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author mikaelbrevik
 */
public interface GameHost extends Remote {

    public Gamer createGamer(String name, String IP, int port) throws RemoteException;

    public void addHighscore(Gamer player) throws RemoteException;

    public ArrayList<HighscoreEntry> getHighscoreList() throws RemoteException;
}
