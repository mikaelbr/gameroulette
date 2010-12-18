/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grserver;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mikaelbrevik
 */
public class Main {

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(2007);
            Registry registry = LocateRegistry.getRegistry(2007);
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
