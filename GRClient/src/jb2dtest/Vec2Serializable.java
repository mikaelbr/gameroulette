/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jb2dtest;

import java.io.Serializable;
import org.jbox2d.common.Vec2;

/**
 *
 * @author mikaelbrevik
 */
public class Vec2Serializable implements Serializable {

    private float x;
    private float y;

    public Vec2Serializable(Vec2 coordinate) {
        this.x = coordinate.x;
        this.y = coordinate.y;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
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
