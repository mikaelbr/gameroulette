/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.stubbs.GameHost;
import rmi.stubbs.Gamer;
import rmi.stubbs.GamerStatus;

/**
 *
 * @author mikaelbrevik
 */
public class Main {

    static int socketPort = 4815;
    static int rmiPort = 4783;
    static String serverip = "localhost";
    static String myIP = "localhost";
    static String username = "Mariusk1";
    static Socket serverConnection = null;
    static BufferedReader clientReader;
    static BufferedReader serverReader;

    public synchronized static void startSocket() {
        Runnable scktRun = new Runnable() {

            public void run() {
                try {
                    ServerSocket server = new ServerSocket(socketPort);
                    serverConnection = server.accept();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        Thread scktThread = new Thread(scktRun);
        scktThread.start();
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
    public static void main(String[] args) {
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

            Socket clientConnection = new Socket(thisIsMe.getOpponent().getIP(), thisIsMe.getOpponent().getPort());

            while (serverConnection == null) {
            }
            // Socket-server starts here.
            InputStreamReader ServerReaderconnection = new InputStreamReader(serverConnection.getInputStream());

            serverReader = new BufferedReader(ServerReaderconnection);

            PrintWriter serverWriter = new PrintWriter(serverConnection.getOutputStream(), true);

            // Socket-server ends here.

            // Socket-client starts here.

            InputStreamReader clientReaderConnection = new InputStreamReader(System.in);

            clientReader = new BufferedReader(clientReaderConnection);

            PrintWriter clientWriter = new PrintWriter(clientConnection.getOutputStream(), true);

            chatMessages();

            /* ≈pner forbindelse slik at inndata kan leses fra konsollet */
            InputStreamReader leseforbTilKonsoll = new InputStreamReader(System.in);
            BufferedReader leserFraKonsoll = new BufferedReader(leseforbTilKonsoll);
            String enLinje = leserFraKonsoll.readLine();
            while (!enLinje.equals("")) {
                clientWriter.println(enLinje);  // sender teksten til tjeneren
                enLinje = leserFraKonsoll.readLine();
            }

            // Socket-client ends here.
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
