package grserver.gamer.rmi;

import grserver.gamer.Color;

/**
 *
 * @author mikaelbrevik
 */
public interface Gamer {
    public Gamer getApponent();
    public String getUsername();
    public int getScore();
    public Color getColor();
    public void write(String msg);
}
