/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grserver.host;

import rmi.stubbs.GameHost;
import grserver.gamer.GamerImpl;
import rmi.stubbs.GamerStatus;
import rmi.stubbs.Gamer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mikaelbrevik
 */
public class GameHostImpl extends UnicastRemoteObject implements GameHost {

    private ArrayList<Gamer> gamers = new ArrayList<Gamer>();
    private Gamer[] pairingTemp = new Gamer[2];
    private Thread thThread;

    public GameHostImpl () throws RemoteException {
        pairPlayers();
    }

    private synchronized void pairPlayers() {
        Runnable th = new Runnable() {

            public void run() {
                while (true) {
                    
                    for (Gamer g : gamers) {
                        try {
                            if (g.getStatus() == GamerStatus.SEARCHING) {
                                if(pairingTemp[0] == null) {
                                    pairingTemp[0] = g;
                                } else if (pairingTemp[1] == null && !pairingTemp[0].equals(g)) {
                                    pairingTemp[1] = g;
                                }
                                if (pairingTemp.length == 2) {
                                    if(pairingTemp[0] == null || pairingTemp[1] == null) {
                                        continue;
                                    }
                                    // pair Players.
                                    if(pairingTemp[0].getIP().equals(pairingTemp[0].getIP())) {
                                        pairingTemp[0].setUseLocalIP(true);
                                        pairingTemp[1].setUseLocalIP(true);
                                    }
                                    pairingTemp[0].setStatus(GamerStatus.IN_GAME);
                                    pairingTemp[0].setOpponent(pairingTemp[1]);
                                    pairingTemp[1].setStatus(GamerStatus.IN_GAME);
                                    pairingTemp[1].setOpponent(pairingTemp[0]);
                                    pairingTemp = new Gamer[2];
                                }
                            }
                        } catch (RemoteException ex) {
                            
                        }
                    }
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameHostImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };

        thThread = new Thread(th);
        thThread.start();
    }


    public synchronized Gamer createGamer(String name, String IP, int port) throws RemoteException {
        Gamer newGamer = new GamerImpl(name, IP, port);
        gamers.add(newGamer);
        return newGamer;
    }

}
