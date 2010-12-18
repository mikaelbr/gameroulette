/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grserver;

import grserver.host.GameHostImpl;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.stubbs.GameHost;

/**
 *
 * @author mikaelbrevik
 */
public class Main {

    public static void main(String[] args) {
        Registry registry = null;
        try {
            LocateRegistry.createRegistry(4783);
            registry = LocateRegistry.getRegistry(4783);
            System.out.println("DSa?");
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
}
