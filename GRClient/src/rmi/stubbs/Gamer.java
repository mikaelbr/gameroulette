package rmi.stubbs;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author mikaelbrevik
 */
public interface Gamer extends Remote {

    public String[] getLevelDesign() throws RemoteException;

    public void setLevelDesign(String[] level) throws RemoteException;

    public GamerStatus getStatus() throws RemoteException;

    public void setStatus(GamerStatus status) throws RemoteException;

    public int getPort() throws RemoteException;

    public void setPort(int port) throws RemoteException;

    public String getIP() throws RemoteException;

    public void setIP(String ip) throws RemoteException;

    public void setOpponent(Gamer g) throws RemoteException;

    public Gamer getOpponent() throws RemoteException;

    public String getUsername() throws RemoteException;

    public int getScore() throws RemoteException;

    public void setScore(int score) throws RemoteException;

    public Color getColor() throws RemoteException;

    public ArrayList<Gamer> getPreviousGamers() throws RemoteException;

    public boolean isPreviousGamer(Gamer g) throws RemoteException;

    public int getGamerId() throws RemoteException;

    public boolean getUseLocalIP() throws RemoteException;

    public void setUseLocalIP(boolean b) throws RemoteException;
}
