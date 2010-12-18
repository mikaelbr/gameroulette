/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grserver;

import grserver.host.GameHostImpl;
import java.net.MalformedURLException;
import java.rmi.Naming;
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
        try {
            LocateRegistry.createRegistry(2007);
            Registry registry = LocateRegistry.getRegistry(2007);

            GameHost gamehost = new GameHostImpl();
            Naming.rebind("GameHost", gamehost);
            

        } catch (MalformedURLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
