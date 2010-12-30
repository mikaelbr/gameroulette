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
public class Vec2Serializable extends Vec2 implements Serializable {

    public Vec2Serializable() {
        super(0, 0);
    }

    public Vec2Serializable(float x, float y) {

        super(x, y);
    }

    public Vec2Serializable(Vec2 toCopy) {
        super(toCopy.x, toCopy.y);
    }
}
