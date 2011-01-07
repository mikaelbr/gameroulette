/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grserver;

import javax.swing.JFrame;

/**
 *
 * @author mikaelbrevik
 */
public class Main {

    public static void main(String[] args) {

        ServerStartScreen scr = new ServerStartScreen();

        scr.setSize(240, 85);
        scr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scr.setResizable(false);
        scr.setVisible(true);
//        Registry registry = null;
//        try {
//            LocateRegistry.createRegistry(4783);
//            registry = LocateRegistry.getRegistry(4783);
//            System.out.println("DSa?");
//            GameHost gamehost = new GameHostImpl();
//            registry.rebind("GameHost", gamehost);
//
//        } catch (RemoteException ex) {
//            if (registry != null) {
//                try {
//                    registry.unbind("GameHost");
//                } catch (RemoteException ex1) {
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex1);
//                } catch (NotBoundException ex1) {
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex1);
//                }
//            }
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}