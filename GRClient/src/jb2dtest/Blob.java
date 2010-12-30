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
import processing.core.PImage;

// <http://www.shiffman.net/teaching/nature>
// Spring 2010
// PBox2D example
// A blob skeleton
// Could be used to create blobbly characters a la Nokia Friends
// http://postspectacular.com/work/nokia/friends/start
public class Blob {

    Blobby parent;
    PBox2D box2d;
    // We need to keep track of a Body and a width and height
    Body body;
    float w;
    float h;
    float x;
    float y;
    private float maxSpeed = 20;
    Vec2 pos;
    int rgb;
    PImage img;

    // We should modify this constructor to receive arguments
    // So that we can make many different types of blobs
    public Blob(PBox2D b, Blobby p, int rgb) {
        parent = p;
        box2d = b;
        w = 285 / 10;
        h = 297 / 10;

        this.rgb = rgb;

        this.x = parent.width / 2 - w / 2;
        this.y = parent.height / 2 - h / 2;

        // Define a polygon (this is what we use for a rectangle)
        PolygonDef sd = new PolygonDef();
        float box2dW = box2d.scaleScreenToWorld(w / 2);
        float box2dH = box2d.scaleScreenToWorld(h / 2);
        sd.setAsBox(box2dW, box2dH);

        // Parameters that affect physics
        sd.density = 1.0f;
        sd.friction = 0.5f;
        sd.restitution = 0.1f;

        // Define the body and make it from the shape
        BodyDef bd = new BodyDef();
        Vec2 center = new Vec2(x, y);
        bd.position.set(box2d.screenToWorld(center));

        body = box2d.createBody(bd);
        body.createShape(sd);
        body.setMassFromShapes();

        img = parent.loadImage(Blob.class.getResource("stickman2.png").toString());

        pos = box2d.getScreenPos(body);
    }

    // Time to draw the blob!
    // Can you make it a cute character, a la http://postspectacular.com/work/nokia/friends/start
    public void display() {

        // Get its angle of rotation
        parent.pushMatrix();
        parent.translate(pos.x, pos.y);

//        parent.lights();
//        parent.textureMode(PApplet.NORMALIZED);
        parent.beginShape(PApplet.TRIANGLE_STRIP);
//        parent.texture(img);
        parent.fill(rgb); parent.ellipse(0, 0, w, h);

        parent.popMatrix();
        body.m_angularVelocity = 0;
        parent.endShape();

        // We look at each body and get its screen position
        pos = box2d.getScreenPos(body);
    }

    public Vec2 getPosition() {
        return body.getPosition();
    }

    public void setPosition(Vec2 pos) {
        this.pos = pos;
        parent.translate(pos.x, pos.y);
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
        if (body.isTouching(parent.getFloor().getBody())) {
            body.m_linearVelocity.y = 10;
        }
    }
}
