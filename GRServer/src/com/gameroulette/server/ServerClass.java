/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameroulette.server;

import com.gameroulette.server.ui.Main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.stubbs.GameHost;

/**
 *
 * @author Mariusk
 */
public class ServerClass {

    ServerClass() {
    }

    public static String getGlobalIP() {
        BufferedReader buffer = null;
        String ip;
        try {
            URL url = new URL("http://whatismyip.com/automation/n09230945.asp");
            InputStreamReader in = new InputStreamReader(url.openStream());
            buffer = new BufferedReader(in);

            ip = buffer.readLine();
            return ip;

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            try {
                if (buffer != null) {
                    buffer.close();
                }
            } catch (IOException e) {
                System.out.println("Exception: " + e);
            }
        }
        return null;
    }

    public static String getLocalIP() {
        try {
            InetAddress localIP = InetAddress.getLocalHost();
            return localIP.getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void startServer(final int portnr) {
        Runnable server = new Runnable() {

            public void run() {
                Registry registry = null;
                try {
                    LocateRegistry.createRegistry(portnr);
                    registry = LocateRegistry.getRegistry(portnr);
                    GameHost gamehost = new GameHostImpl();
                    registry.rebind("GameHost", gamehost);

                } catch (RemoteException ex) {
                    if (registry != null) {
                        try {
                            registry.unbind("GameHost");
                        } catch (RemoteException ex1) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex1);
                        } catch (NotBoundException ex1) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        Thread serverThread = new Thread(server);
        serverThread.start();
    }
}
