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
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.common.Vec2;
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
    static String myIP = "192.168.1.7";
    static String username = "Mariusk";
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

                    ssChannel.accept();
//                    ServerSocket server = new ServerSocket(socketPort);
//                    serverConnection = server.accept();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        Thread scktThread = new Thread(scktRun);
        scktThread.start();
    }

    public synchronized static void sendPosition(final Vec2 pos) {
        Runnable brains = new Runnable() {

            public void run() {
                try {
                    ooStream = new ObjectOutputStream(sChannel.socket().getOutputStream());
                } catch (IOException ex) {
                    Logger.getLogger(MultiplayerConnect.class.getName()).log(Level.SEVERE, null, ex);
                }
                while (true) {
                    try {
                        ooStream.writeObject(pos);
                        Thread.sleep(100);
                    } catch (Exception ex) {
                        Logger.getLogger(Blobby.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };

        Thread feed = new Thread(brains);
        feed.start();
    }

    public synchronized static void getPosition(final Blobby parent) {
        Runnable brains = new Runnable() {

            public void run() {
                try {
                    serversChannel = ssChannel.accept();
                    oiStream = new ObjectInputStream(serversChannel.socket().getInputStream());
                } catch (Exception ex) {
                    Logger.getLogger(MultiplayerConnect.class.getName()).log(Level.SEVERE, null, ex);
                }
                while (true) {
                    try {
                        Vec2 opponentPosition = (Vec2) oiStream.readObject();
                        parent.setOpponent(opponentPosition);
                        Thread.sleep(100);
                    } catch (Exception ex) {
                        Logger.getLogger(Blobby.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
