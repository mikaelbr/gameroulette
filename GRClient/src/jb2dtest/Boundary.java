/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jb2dtest;

// The Nature of Code
import org.jbox2d.collision.shapes.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import pbox2d.PBox2D;
import processing.core.PApplet;

// <http://www.shiffman.net/teaching/nature>
// Spring 2010
// PBox2D example
// A fixed boundary class
public class Boundary {

    // A boundary is a simple rectangle with x,y,width,and height
    float x;
    float y;
    float w;
    float h;
    // But we also have to make a body for box2d to know about it
    Body b;
    PApplet parent;
    int color;

    public Boundary(float x_, float y_, float w_, float h_, PBox2D box2d, PApplet p, int rgb) {
        x = x_;
        y = y_;
        w = w_;
        h = h_;

        color = rgb;

        parent = p;

        // Figure out the box2d coordinates
        float box2dW = box2d.scaleScreenToWorld(w / 2);
        float box2dH = box2d.scaleScreenToWorld(h / 2);
        Vec2 center = new Vec2(x, y);

        // Define the polygon
        PolygonDef sd = new PolygonDef();
        sd.setAsBox(box2dW, box2dH);
        sd.density = 0;    // No density means it won't move!
        sd.friction = 0.3f;

        // Create the body
        BodyDef bd = new BodyDef();
        bd.position.set(box2d.screenToWorld(center));
        b = box2d.createBody(bd);
        b.createShape(sd);

    }

    public Body getBody () {
        return b;
    }

    // Draw the boundary, if it were at an angle we'd have to do something fancier
    public void display() {
        parent.fill(color);
        parent.stroke(0);
        parent.rectMode(parent.CENTER);
        parent.rect(x, y, w, h);
    }
}
