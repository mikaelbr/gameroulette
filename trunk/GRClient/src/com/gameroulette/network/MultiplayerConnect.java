package com.gameroulette.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jgame.impl.JGEngineInterface;
import rmi.stubbs.GameHost;
import rmi.stubbs.Gamer;
import rmi.stubbs.GamerStatus;
import rmi.stubbs.HighscoreEntry;

/**
 *
 * @author mikaelbrevik
 */
public class MultiplayerConnect {

    static int socketPort = 4815;
    static int rmiPort = 4783;
    private static String serverip = "";
    static String myIP = "192.168.1.7";
    static String username = "MariuskiMac";
    static Socket serverConnection = null;
    static BufferedReader clientReader;
    static BufferedReader serverReader;
    static PrintWriter clientWriter;
    static ServerSocketChannel ssChannel;
    static SocketChannel sChannel;
    static SocketChannel serversChannel;
    static ObjectOutputStream ooStream;
    static ObjectInputStream oiStream;
    private static JGEngineInterface p1;
    private static JGEngineInterface p2;
    static Gamer thisIsMe = null;
    static GameHost gameHost = null;

    public static void setPlayer(JGEngineInterface p) {
        p1 = p;
    }

    public static void setOpponent(JGEngineInterface p) {
        p2 = p;
    }

    public static Gamer getMySelf() {
        return thisIsMe;
    }

    public static Gamer getOpponent() {
        try {
            return thisIsMe.getOpponent();
        } catch (Exception ex) {
            return null;
        }
    }

    public static void removeMySelf() {
        if (gameHost == null) {
            return;
        }
        try {
            gameHost.removeGamer(thisIsMe);
        } catch (RemoteException ex) {
            Logger.getLogger(MultiplayerConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String[] getLevelDesign() {
        try {
            return thisIsMe.getLevelDesign();
        } catch (RemoteException ex) {
            return null;
        }
    }

    /**
     * Creates a player in the server.
     *
     * @param serverip
     * @param rmiPort
     * @param username
     * @throws Exception
     */
    public static void createMySelf(String serverip, int rmiPort, String username) throws Exception {
        BufferedReader buffer = null;
        String ip;
        URL url = new URL("http://whatismyip.com/automation/n09230945.asp");
        InputStreamReader in = new InputStreamReader(url.openStream());
        buffer = new BufferedReader(in);

        ip = buffer.readLine();
        MultiplayerConnect.serverip = serverip;
        Registry registry = LocateRegistry.getRegistry(serverip, rmiPort);
        gameHost = (GameHost) registry.lookup("GameHost");
        thisIsMe = gameHost.createGamer(username, ip, socketPort);
    }

    /**
     * Adds total score of player (myself) to highscore list.
     *
     * A highscore list is restricted to a server instance. Restarts along
     * with server restart. (no db)
     *
     * @throws RemoteException
     */
    public static void saveGame() throws RemoteException {
        if (gameHost == null || thisIsMe == null) {
            return;
        }
        gameHost.addHighscore(thisIsMe);
        thisIsMe.setScore(0);
    }

    /**
     * Get ArrayList with all highscore entries.
     *
     * @return ArrayList<HighscoreEntry>
     * @throws RemoteException
     */
    public static ArrayList<HighscoreEntry> getHighscoreList() throws RemoteException {
        return gameHost.getHighscoreList();
    }

    /**
     * Get local IP of client.
     *
     * @return String
     */
    public static String getLocalIP() {
        try {
            InetAddress localIP = InetAddress.getLocalHost();
            return localIP.getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(MultiplayerConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Open socket connection to opponent client.
     */
    public synchronized static void startSocket() {
        Runnable scktRun = new Runnable() {

            public void run() {
                try {
                    System.out.println("Her er en melding");
                    ssChannel = ServerSocketChannel.open();
                    ssChannel.configureBlocking(true);
                    ssChannel.socket().bind(new InetSocketAddress(socketPort));

                    while(!ssChannel.isOpen());

                    serversChannel = ssChannel.accept();
                } catch (Exception ex) {
                    Logger.getLogger(MultiplayerConnect.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        Thread scktThread = new Thread(scktRun);
        scktThread.start();
    }

    /**
     * Send ClientInfo to opponent client. (Holds info about player's x and y and view x, y. 
     */
    public synchronized static void sendPosition() {

        if (p1 == null) {
            return;
        }

        Runnable brains = new Runnable() {

            public void run() {
                JGEngineInterface player = p1;
                while (player.getPlayer() == null) {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MultiplayerConnect.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    ooStream = new ObjectOutputStream(sChannel.socket().getOutputStream());

                    while (true) {
                        try {
                            ClientInfo clientInfo = player.getClientInfo();

                            int[] send = {
                                (int) clientInfo.getX(),
                                (int) clientInfo.getY(),
                                clientInfo.getPfx(),
                                clientInfo.getPfy(),
                                clientInfo.getPlayerState(),
                                clientInfo.getScore(),
                                clientInfo.isResetGame()
                            };

                            ooStream.writeObject(send);
                            Thread.sleep(30);
                        } catch (Exception ex) {
                            break;
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MultiplayerConnect.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        Thread feed = new Thread(brains);
        feed.start();
    }

    /**
     * Get opponent clients client information.
     */
    public synchronized static void getPosition() {

        if (p2 == null) {
            return;
        }

        Runnable brains = new Runnable() {

            public void run() {
                JGEngineInterface player = p2;
                while (player.getPlayer() == null) {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MultiplayerConnect.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    if (serversChannel != null) {
                        oiStream = new ObjectInputStream(serversChannel.socket().getInputStream());
                    }

                    while (true) {
                        try {
                            if (oiStream != null) {
                                int[] get = (int[]) oiStream.readObject();
                                ClientInfo clientInfo = new ClientInfo((double) get[0], (double) get[1], get[2], get[3], get[4], get[5], get[6]);
                                player.setClientInfo(clientInfo);
                                Thread.sleep(30);
                            }
                        } catch (Exception ex) {
                            break;
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(MultiplayerConnect.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        Thread feed = new Thread(brains);
        feed.start();
    }

    public static Gamer getThisIsMe() {
        return thisIsMe;
    }

    /**
     * Close socket connection, set status to IDLE and remove opponent from myself. 
     */
    public static void closeConnection() {
        try {
            sChannel.close();
            serversChannel.close();
            ssChannel.close();
            
            thisIsMe.setStatus(GamerStatus.IDLE);
            thisIsMe.setOpponent(null);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }
    }

    /**
     * Connect to another player (pair players)
     */
    public static void connect() {
        try {
            thisIsMe.setStatus(GamerStatus.SEARCHING);
            while (thisIsMe.getOpponent() == null) {
            }

            if (thisIsMe.getUseLocalIP()) {
                thisIsMe.setIP(getLocalIP());
                if (thisIsMe.getIP().equals(serverip)) {
                    thisIsMe.setPort(4915);
                    MultiplayerConnect.socketPort = 4915;
                }
            }
            startSocket();

            Thread.sleep(200);

            sChannel = SocketChannel.open();
            sChannel.configureBlocking(true);

            Thread.sleep(200);

            System.out.println("Opponent IP: " + thisIsMe.getOpponent().getIP() + ", Opponent Port: " + thisIsMe.getOpponent().getPort());
            sChannel.connect(new InetSocketAddress(thisIsMe.getOpponent().getIP(), thisIsMe.getOpponent().getPort()));
            while (!sChannel.finishConnect()) {
                // Wait.
            }
            // Socket-client ends here.
        } catch (Exception ex) {
            Logger.getLogger(MultiplayerConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
