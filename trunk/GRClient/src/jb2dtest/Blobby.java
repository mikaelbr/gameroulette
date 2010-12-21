/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jb2dtest;

// The Nature of Code
// <http://www.shiffman.net/teaching/nature>
// Spring 2010
// PBox2D example
// A blob skeleton
// Could be used to create blobbly characters a la Nokia Friends
// http://postspectacular.com/work/nokia/friends/start
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import pbox2d.*;
import processing.core.PApplet;

public class Blobby extends PApplet {
// A reference to our box2d world

    PBox2D box2d;
// A list we'll use to track fixed objects
    ArrayList boundaries;
// Our "blob" object
    Blob blob;

    public void setup() {
        size(700, 300);
        smooth();

        // Initialize box2d physics and create the world
        box2d = new PBox2D(this);
        box2d.createWorld();
        box2d.setGravity(0, -9.81f);


        // Add some boundaries
        boundaries = new ArrayList();
        boundaries.add(new Boundary(width / 2, height - 5, width, 10, box2d, this));
        boundaries.add(new Boundary(width / 2, 5, width, 10, box2d, this));
        boundaries.add(new Boundary(width - 5, height / 2, 10, height, box2d, this));
        boundaries.add(new Boundary(5, height / 2, 10, height, box2d, this));

        // Make a new blob
        blob = new Blob(box2d, this);
    }

    public void draw() {
        background(255);

        // We must always step through time!
        box2d.step();

        // Show the blob!
        blob.display();

        // Show the boundaries!
        for (int i = 0; i < boundaries.size(); i++) {
            Boundary wall = (Boundary) boundaries.get(i);
            wall.display();
        }

        if (keyPressed) {
            if (keyCode == KeyEvent.VK_LEFT) {
                blob.moveLeft();
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                blob.moveRight();
            } else if (keyCode == KeyEvent.VK_UP) {
                blob.jump();
            }
        }

    }
}
