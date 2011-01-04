/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jb2dtest;

import java.io.Serializable;


/**
 *
 * @author mikaelbrevik
 */
public class Coordinates implements Serializable {

    private double x;
    private double y;

    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

//    public Vec2Serializable(float x, float y) {
//
//        super(x, y);
//    }
//
//    public Vec2Serializable(Vec2 toCopy) {
//        super(toCopy.x, toCopy.y);
//    }
}
