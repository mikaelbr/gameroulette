/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grserver.host;

import grserver.gamer.GamerImpl;
import grserver.gamer.GamerStatus;
import grserver.gamer.rmi.Gamer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mikaelbrevik
 */
public class GameHostImpl implements GameHost {

    private ArrayList<Gamer> gamers = new ArrayList<Gamer>();
    private Gamer[] pairingTemp = new Gamer[2];
    private Thread thThread;

    public GameHostImpl () {
        pairPlayers();
    }

    public final synchronized void pairPlayers() {
        Runnable th = new Runnable() {

            public void run() {
                while (true) {
                    for (Gamer g : gamers) {
                        if(g.getStatus() == GamerStatus.SEARCHING) {

                            pairingTemp[pairingTemp.length-1] = g;

                            if (pairingTemp.length == 2) {
                                // pair Players.
                                pairingTemp[0].setStatus(GamerStatus.IN_GAME);
                                pairingTemp[0].setOpponent(pairingTemp[1]);
                                pairingTemp[1].setStatus(GamerStatus.IN_GAME);
                                pairingTemp[1].setOpponent(pairingTemp[0]);
                                pairingTemp = new Gamer[2];
                            }
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

    public synchronized Gamer createGamer(String name, int port) {
        Gamer newGamer = new GamerImpl(name, port);
        gamers.add(newGamer);
        return newGamer;
    }

}
