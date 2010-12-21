/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jb2dtest;

// The Nature of Code
import java.util.ArrayList;
import org.jbox2d.collision.shapes.CircleDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.joints.ConstantVolumeJointDef;
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

    // A list to keep track of all the points in our blob
    ArrayList skeleton;
    float bodyRadius;  // The radius of each body that makes up the skeleton
    float radius;      // The radius of the entire blob
    float totalPoints; // How many points make up the blob
    PBox2D box2d;

    // We should modify this constructor to receive arguments
    // So that we can make many different types of blobs
    public Blob(PBox2D box2d, PApplet p) {
        parent = p;
        this.box2d = box2d;
        // Create the empty
        skeleton = new ArrayList();

        // Let's make a volume of joints!
        ConstantVolumeJointDef cvjd = new ConstantVolumeJointDef();

        // Where and how big is the blob
        Vec2 center = new Vec2(parent.width / 2, parent.height / 2);
        radius = 100;
        totalPoints = 20;
        bodyRadius = 12;


        // Initialize all the points
        for (int i = 0; i < totalPoints; i++) {
            // Look polar to cartesian coordinate transformation!
            float theta = PApplet.map(i, 0, totalPoints, 0, parent.TWO_PI);
            float x = center.x + radius * parent.sin(theta);
            float y = center.y + radius * parent.cos(theta);

            // Make each individual body
            BodyDef bd = new BodyDef();
            bd.fixedRotation = true; // no rotation!
            bd.position.set(box2d.screenToWorld(x, y));
            Body body = box2d.createBody(bd);

            // The body is a circle
            CircleDef cd = new CircleDef();
            cd.radius = box2d.scaleScreenToWorld(bodyRadius);
            cd.density = 1.0f;
            // For filtering out collisions
            cd.filter.groupIndex = -2;

            // Finalize the body
            body.createShape(cd);
            // Add it to the volume
            cvjd.addBody(body);
            // We always do this at the end
            body.setMassFromShapes();

            // Store our own copy for later rendering
            skeleton.add(body);
        }

        // These parameters control how stiff vs. jiggly the blob is
        cvjd.frequencyHz = 10.0f;
        cvjd.dampingRatio = 1.0f;

        // Put the joint thing in our world!
        box2d.world.createJoint(cvjd);

    }

    // Time to draw the blob!
    // Can you make it a cute character, a la http://postspectacular.com/work/nokia/friends/start
    public void display() {

        // Draw the outline
        parent.beginShape();
        parent.noFill();
        parent.stroke(0);
        parent.strokeWeight(1);
        for (int i = 0; i < skeleton.size(); i++) {
            // We look at each body and get its screen position
            Body b = (Body) skeleton.get(i);
            Vec2 pos = box2d.getScreenPos(b);
            parent.vertex(pos.x, pos.y);
        }
        parent.endShape(parent.CLOSE);

        // Draw the individual circles
        for (int i = 0; i < skeleton.size(); i++) {
            Body b = (Body) skeleton.get(i);
            // We look at each body and get its screen position
            Vec2 pos = box2d.getScreenPos(b);
            // Get its angle of rotation
            float a = b.getAngle();
            parent.pushMatrix();
            parent.translate(pos.x, pos.y);
            parent.rotate(a);
            parent.fill(175);
            parent.stroke(0);
            parent.strokeWeight(1);
            parent.ellipse(0, 0, bodyRadius * 2, bodyRadius * 2);
            parent.popMatrix();
        }

    }
}
