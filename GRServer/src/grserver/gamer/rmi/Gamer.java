package grserver.gamer.rmi;

import grserver.gamer.Color;
import java.rmi.Remote;

/**
 *
 * @author mikaelbrevik
 */
public interface Gamer extends Remote {
    public Gamer getApponent();
    public String getUsername();
    public int getScore();
    public Color getColor();
    public void write(String msg);
}
