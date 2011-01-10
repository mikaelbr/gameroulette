/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameroulette.server;

import rmi.stubbs.HighscoreEntry;
import rmi.stubbs.GameHost;
import com.gameroulette.server.gamer.GamerImpl;
import rmi.stubbs.GamerStatus;
import rmi.stubbs.Gamer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mikaelbrevik
 */
public class GameHostImpl extends UnicastRemoteObject implements GameHost {

    private List<Gamer> gamers = Collections.synchronizedList(new ArrayList<Gamer>());
    private Gamer[] pairingTemp = new Gamer[2];
    private Thread thThread;
    private ArrayList<HighscoreEntry> highscore = new ArrayList<HighscoreEntry>();

    public GameHostImpl() throws RemoteException {
        pairPlayers();
    }

    public void addHighscore(Gamer player) throws RemoteException {
        highscore.add(new HighscoreEntryImpl(player.getUsername(), player.getScore()));
    }

    public ArrayList<HighscoreEntry> getHighscoreList () {
        return highscore;
    }

    private synchronized void pairPlayers() {
        Runnable th = new Runnable() {

            public void run() {
                while (true) {

                    for (Gamer g : gamers) {
                        try {
                            if (g.getStatus() == GamerStatus.SEARCHING) {
                                if (pairingTemp[0] == null) {
                                    pairingTemp[0] = g;
                                } else if (pairingTemp[1] == null && !pairingTemp[0].equals(g)) {
                                    pairingTemp[1] = g;
                                }
                                if (pairingTemp.length == 2) {
                                    if (pairingTemp[0] == null || pairingTemp[1] == null) {
                                        continue;
                                    }
                                    // pair Players.
                                    if (pairingTemp[0].getIP().equals(pairingTemp[1].getIP())) {
                                        pairingTemp[0].setUseLocalIP(true);
                                        pairingTemp[1].setUseLocalIP(true);
                                    }
                                    String[] randLevel = LevelDesign.getRandomMap();
                                    pairingTemp[0].setStatus(GamerStatus.IN_GAME);
                                    pairingTemp[0].setOpponent(pairingTemp[1]);
                                    pairingTemp[0].setLevelDesign(randLevel);
                                    pairingTemp[1].setStatus(GamerStatus.IN_GAME);
                                    pairingTemp[1].setOpponent(pairingTemp[0]);
                                    pairingTemp[1].setLevelDesign(randLevel);
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

    public synchronized void removeGamer(Gamer g) {
        gamers.remove(g);
    }

    public synchronized Gamer createGamer(String name, String IP, int port) throws RemoteException {
        Gamer newGamer = new GamerImpl(name, IP, port);
        gamers.add(newGamer);
        return newGamer;
    }
}
