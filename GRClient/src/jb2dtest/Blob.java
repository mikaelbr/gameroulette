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
// A blob skeleton
// Could be used to create blobbly characters a la Nokia Friends
// http://postspectacular.com/work/nokia/friends/start
public class Blob {

    PApplet parent;
    PBox2D box2d;
    // We need to keep track of a Body and a width and height
    Body body;
    float w;
    float h;
    float x;
    float y;
    private float maxSpeed = 20;

    // We should modify this constructor to receive arguments
    // So that we can make many different types of blobs
    public Blob(PBox2D b, PApplet p) {
        parent = p;
        box2d = b;
        w = 30;
        h = 80;

        this.x = parent.width / 2 - w / 2;
        this.y = parent.height / 2 - h / 2;

        // Define a polygon (this is what we use for a rectangle)
        PolygonDef sd = new PolygonDef();
        float box2dW = box2d.scaleScreenToWorld(w / 2);
        float box2dH = box2d.scaleScreenToWorld(h / 2);
        sd.setAsBox(box2dW, box2dH);

        // Parameters that affect physics
        sd.density = 1.0f;
        sd.friction = 0.1f;
        sd.restitution = 0.5f;

        // Define the body and make it from the shape
        BodyDef bd = new BodyDef();
        Vec2 center = new Vec2(x, y);
        bd.position.set(box2d.screenToWorld(center));

        body = box2d.createBody(bd);
        body.createShape(sd);
        body.setMassFromShapes();
    }

    // Time to draw the blob!
    // Can you make it a cute character, a la http://postspectacular.com/work/nokia/friends/start
    public void display() {
        // We look at each body and get its screen position
        Vec2 pos = box2d.getScreenPos(body);
        // Get its angle of rotation
        float a = body.getAngle();
        parent.pushMatrix();
        parent.translate(pos.x, pos.y);
        parent.rotate(a);
        parent.fill(175);
        parent.stroke(0);
        parent.strokeWeight(1);
        parent.rect(0, 0, w, h);
        parent.popMatrix();
        body.m_angularVelocity = 0;
    }

    public void moveLeft() {
        body.m_linearVelocity.x -= 0.5f;

        if (body.m_linearVelocity.x < -maxSpeed) {
            body.m_linearVelocity.x = -maxSpeed;
        }
    }

    public void moveRight() {
        body.m_linearVelocity.x += 0.5f;

        if (body.m_linearVelocity.x > maxSpeed) {
            body.m_linearVelocity.x = maxSpeed;
        }
    }

    public void jump() {
        body.m_linearVelocity.y = 10;

    }
}
