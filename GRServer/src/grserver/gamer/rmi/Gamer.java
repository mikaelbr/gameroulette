package grserver.gamer.rmi;

import grserver.gamer.Color;
import grserver.gamer.GamerStatus;
import java.rmi.Remote;
import java.util.ArrayList;

/**
 *
 * @author mikaelbrevik
 */
public interface Gamer extends Remote {
    public GamerStatus getStatus();
    public void setStatus(GamerStatus status);
    public int getPort();

    public void setOpponent (Gamer g);
    public Gamer getOpponent ();

    public String getUsername();
    public int getScore();
    public Color getColor();
    public void write(String msg);

    public ArrayList<Gamer> getPreviousGamers ();
    public boolean isPreviousGamer(Gamer g);
    public int getGamerId ();
}
