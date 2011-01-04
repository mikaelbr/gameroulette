/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jb2dtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import jgtest.SpaceRunIII;
import jgtest.SpaceRunIIIOpponent;
import rmi.stubbs.GameHost;
import rmi.stubbs.Gamer;
import rmi.stubbs.GamerStatus;

/**
 *
 * @author mikaelbrevik
 */
public class MultiplayerConnect {

    static int socketPort = 4815;
    static int rmiPort = 4783;
    static String serverip = "192.168.1.5";
    static String myIP = "192.168.1.17";
    static String username = "MariuskBB";
    static Socket serverConnection = null;
    static BufferedReader clientReader;
    static BufferedReader serverReader;
    static PrintWriter clientWriter;
    static ServerSocketChannel ssChannel;
    static SocketChannel sChannel;
    static SocketChannel serversChannel;
    static ObjectOutputStream ooStream;
    static ObjectInputStream oiStream;

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

    public synchronized static void sendPosition(final SpaceRunIII player) {

        final Coordinates pos = new Coordinates(player.getPlayer().x, player.getPlayer().y);
        Runnable brains = new Runnable() {

            public void run() {
                try {
                    ooStream = new ObjectOutputStream(sChannel.socket().getOutputStream());
                } catch (IOException ex) {
                    Logger.getLogger(MultiplayerConnect.class.getName()).log(Level.SEVERE, null, ex);
                }
                while (true) {
                    try {
                        System.out.println("Your coordinates: " + pos);
                        ooStream.writeObject(pos);
                        Thread.sleep(100);
                    } catch (Exception ex) {
                        System.out.println("Exception: " + ex);
                    }
                }
            }
        };

        Thread feed = new Thread(brains);
        feed.start();
    }

    public synchronized static void getPosition(final SpaceRunIIIOpponent player) {
        Runnable brains = new Runnable() {

            public void run() {
                try {
                    if (serversChannel != null) {
                        oiStream = new ObjectInputStream(serversChannel.socket().getInputStream());
                    }
                } catch (Exception ex) {
                    Logger.getLogger(MultiplayerConnect.class.getName()).log(Level.SEVERE, null, ex);
                }
                while (true) {
                    try {
                        if (oiStream != null) {
                            Coordinates opponentPosition = (Coordinates) oiStream.readObject();
                            System.out.println("Opponent coordinates: " + opponentPosition);
                            player.getPlayer().x = opponentPosition.getX();
                            player.getPlayer().y = opponentPosition.getY();
                            Thread.sleep(100);
                        }
                    } catch (Exception ex) {
                        System.out.println("Exception: " + ex);
                    }
                }
            }
        };

        Thread feed = new Thread(brains);
        feed.start();


    }

    public synchronized static void chatMessages() {
        Runnable scktRun = new Runnable() {

            public void run() {
                try {
                    if (serverReader == null) {
                        return;


                    }
                    String line = serverReader.readLine(); // mottar en linje med tekst


                    while (line != null) {
                        // forbindelsen pÂ klientsiden er lukket
                        System.out.println("En klient skrev: " + line);
                        line = serverReader.readLine();



                    }

                } catch (IOException ex) {
                    Logger.getLogger(MultiplayerConnect.class.getName()).log(Level.SEVERE, null, ex);
                }


            }
        };

        Thread scktThread = new Thread(scktRun);
        scktThread.start();


    }

    /**
     * @param args the command line arguments
     */
    public static void connect() {
        startSocket();


        try {
            Registry registry = LocateRegistry.getRegistry(serverip, rmiPort);
            GameHost gameHost = (GameHost) registry.lookup("GameHost");
            Gamer thisIsMe = gameHost.createGamer(username, myIP, socketPort);
            thisIsMe.setStatus(GamerStatus.SEARCHING);



            while (thisIsMe.getOpponent() == null) {
                System.out.println("Waiting for opponent");


            }

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
