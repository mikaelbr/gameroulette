/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package grserver.host;

import grserver.gamer.rmi.Gamer;
import java.rmi.Remote;

/**
 *
 * @author mikaelbrevik
 */
public interface GameHost extends Remote {

    public Gamer createGamer(String name, int port);

}
