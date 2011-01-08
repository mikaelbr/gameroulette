/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grclient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mikaelbrevik
 */
public class Main {

    private static InetAddress ip;

    public static void main(String[] args) throws SocketException, IOException {

//        BufferedReader buffer = null;
//        try {
//            URL url = new URL("http://whatismyip.com/automation/n09230945.asp");
//            InputStreamReader in = new InputStreamReader(url.openStream());
//            buffer = new BufferedReader(in);
//
//            String line = buffer.readLine();
//            System.out.println(line);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (buffer != null) {
//                    buffer.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

//        Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
//        while (netInterfaces.hasMoreElements()) {
//            NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
//            System.out.println(ni.getName());
//            ip = (InetAddress) ni.getInetAddresses().nextElement();
//            System.out.println("IP: " + ip);
//            if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
//                System.out.println("Interface " + ni.getName() + " seems to be InternetInterface. I'll take it...");
//                break;
//            } else {
//                ip = null;
//            }

        try {
            InetAddress aa = InetAddress.getLocalHost();
            System.out.println("My IP: " + aa.getHostAddress());
        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }




    }
}


//    static int socketPort = 4815;
//    static int rmiPort = 4783;
//    static String serverip = "localhost";
//    static String myIP = "localhost";
//    static String username = "Mariusk1";
//    static Socket serverConnection = null;
//    static BufferedReader clientReader;
//    static BufferedReader serverReader;
//
//    public synchronized static void startSocket() {
//        Runnable scktRun = new Runnable() {
//
//            public void run() {
//                try {
//                    ServerSocket server = new ServerSocket(socketPort);
//                    serverConnection = server.accept();
//                } catch (IOException ex) {
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        };
//
//        Thread scktThread = new Thread(scktRun);
//        scktThread.start();
//    }
//
//    public synchronized static void chatMessages() {
//        Runnable scktRun = new Runnable() {
//
//            public void run() {
//                try {
//                    if (serverReader == null) {
//                        return;
//                    }
//                    String line = serverReader.readLine(); // mottar en linje med tekst
//                    while (line != null) {
//                        // forbindelsen pÂ klientsiden er lukket
//                        System.out.println("En klient skrev: " + line);
//                        line = serverReader.readLine();
//                    }
//                } catch (IOException ex) {
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        };
//
//        Thread scktThread = new Thread(scktRun);
//        scktThread.start();
//    }
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        startSocket();
//        try {
//            Registry registry = LocateRegistry.getRegistry(serverip, rmiPort);
//            GameHost gameHost = (GameHost) registry.lookup("GameHost");
//            Gamer thisIsMe = gameHost.createGamer(username, myIP, socketPort);
//            thisIsMe.setStatus(GamerStatus.SEARCHING);
//
//            while (thisIsMe.getOpponent() == null) {
//                System.out.println("Waiting for opponent");
//            }
//
//            System.out.println(thisIsMe.getOpponent().getUsername());
//
//            Socket clientConnection = new Socket(thisIsMe.getOpponent().getIP(), thisIsMe.getOpponent().getPort());
//
//            while (serverConnection == null) {
//            }
//            // Socket-server starts here.
//            InputStreamReader ServerReaderconnection = new InputStreamReader(serverConnection.getInputStream());
//
//            serverReader = new BufferedReader(ServerReaderconnection);
//
//            PrintWriter serverWriter = new PrintWriter(serverConnection.getOutputStream(), true);
//
//            // Socket-server ends here.
//
//            // Socket-client starts here.
//
//            InputStreamReader clientReaderConnection = new InputStreamReader(System.in);
//
//            clientReader = new BufferedReader(clientReaderConnection);
//
//            PrintWriter clientWriter = new PrintWriter(clientConnection.getOutputStream(), true);
//
//            chatMessages();
//
//            /* ≈pner forbindelse slik at inndata kan leses fra konsollet */
//            InputStreamReader leseforbTilKonsoll = new InputStreamReader(System.in);
//            BufferedReader leserFraKonsoll = new BufferedReader(leseforbTilKonsoll);
//            String enLinje = leserFraKonsoll.readLine();
//            while (!enLinje.equals("")) {
//                clientWriter.println(enLinje);  // sender teksten til tjeneren
//                enLinje = leserFraKonsoll.readLine();
//            }
//
//            // Socket-client ends here.
//        } catch (Exception ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//}

