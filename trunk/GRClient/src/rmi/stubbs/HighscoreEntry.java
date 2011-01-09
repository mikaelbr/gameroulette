/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.stubbs;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HighscoreEntry extends Remote {

    public int getScore() throws RemoteException;

    public String getName() throws RemoteException;
}
