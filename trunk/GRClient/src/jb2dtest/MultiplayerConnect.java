/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jb2dtest;

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

    public static void createMySelf(String serverip, int rmiPort, String username) throws Exception {
        BufferedReader buffer = null;
        String ip;
        URL url = new URL("http://whatismyip.com/automation/n09230945.asp");
        InputStreamReader in = new InputStreamReader(url.openStream());
        buffer = new BufferedReader(in);

        ip = buffer.readLine();
        System.out.println(ip);
        System.out.println(serverip);
        System.out.println(rmiPort);
        System.out.println(username);
        MultiplayerConnect.serverip = serverip;
        Registry registry = LocateRegistry.getRegistry(serverip, rmiPort);
        gameHost = (GameHost) registry.lookup("GameHost");
        thisIsMe = gameHost.createGamer(username, ip, socketPort);

    }

    public static void saveGame() throws RemoteException {
        if (gameHost == null || thisIsMe == null) {
            return;
        }
        gameHost.addHighscore(thisIsMe);
    }

    public static ArrayList<HighscoreEntry> getHighscoreList() throws RemoteException {
        return gameHost.getHighscoreList();
    }

    public static String getLocalIP() {
        try {
            System.out.println("Kjører denne metoden for å få lokal ip");
            InetAddress localIP = InetAddress.getLocalHost();
            System.out.println("My IP: " + localIP.getHostAddress());
            return localIP.getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(MultiplayerConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public synchronized static void startSocket() {
        Runnable scktRun = new Runnable() {

            public void run() {
                try {
                    ssChannel = ServerSocketChannel.open();
                    ssChannel.configureBlocking(true);
                    ssChannel.socket().bind(new InetSocketAddress(socketPort));

                    serversChannel = ssChannel.accept();

//                    ServerSocket server = new ServerSocket(socketPort);
//                    serverConnection = server.accept();
                } catch (Exception ex) {
                    Logger.getLogger(MultiplayerConnect.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        Thread scktThread = new Thread(scktRun);
        scktThread.start();
    }

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
//                        System.out.println("Your client info: " + clientInfo);

                            int[] send = {
                                (int) clientInfo.getX(),
                                (int) clientInfo.getY(),
                                clientInfo.getPfx(),
                                clientInfo.getPfy(),
                                clientInfo.getPlayerState(),
                                clientInfo.getScore()
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
                                ClientInfo clientInfo = new ClientInfo((double) get[0], (double) get[1], get[2], get[3], get[4], get[5]);
//                            System.out.println("Opponent coordinates: " + opponentPosition);
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

    public static void closeConnection() {
        try {
            sChannel.close();
            serversChannel.close();
            ssChannel.close();
            thisIsMe.setOpponent(null);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void connect() {
        startSocket();


        try {
            thisIsMe.setStatus(GamerStatus.SEARCHING);



            while (thisIsMe.getOpponent() == null) {
                System.out.println("Waiting for opponent");
            }

            if (thisIsMe.getUseLocalIP()) {
                thisIsMe.setIP(getLocalIP());
                if (thisIsMe.getIP().equals(serverip)) {
                    MultiplayerConnect.socketPort = 4915;
                }
            }

            System.out.println("Opponent IP: " + thisIsMe.getOpponent().getIP());

            System.out.println(thisIsMe.getOpponent().getUsername());

            sChannel = SocketChannel.open();
            sChannel.configureBlocking(true);

            sChannel.connect(new InetSocketAddress(thisIsMe.getOpponent().getIP(), thisIsMe.getOpponent().getPort()));






            while (!sChannel.finishConnect()) {
                // Wait.
            }


//            Socket clientConnection = new Socket(thisIsMe.getOpponent().getIP(), thisIsMe.getOpponent().getPort());


            // Socket-server starts here.

//
//            InputStreamReader ServerReaderconnection = new InputStreamReader(serverConnection.getInputStream());
//
//            serverReader = new BufferedReader(ServerReaderconnection);
//
//            PrintWriter serverWriter = new PrintWriter(serverConnection.getOutputStream(), true);

            // Socket-server ends here.

            // Socket-client starts here.


//
//            InputStreamReader clientReaderConnection = new InputStreamReader(System.in);
//
//            clientReader = new BufferedReader(clientReaderConnection);
//
//            clientWriter = new PrintWriter(clientConnection.getOutputStream(), true);
//
//            chatMessages();

//            /* ≈pner forbindelse slik at inndata kan leses fra konsollet */
//            InputStreamReader leseforbTilKonsoll = new InputStreamReader(System.in);
//            BufferedReader leserFraKonsoll = new BufferedReader(leseforbTilKonsoll);
//            String enLinje = leserFraKonsoll.readLine();
//            while (!enLinje.equals("")) {
//                clientWriter.println(enLinje);  // sender teksten til tjeneren
//                enLinje = leserFraKonsoll.readLine();
//            }

            // Socket-client ends here.
        } catch (Exception ex) {
            Logger.getLogger(MultiplayerConnect.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}
