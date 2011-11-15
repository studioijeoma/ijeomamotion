/**
	  Author: Ekene Ijeoma / Bloom
	  Date: March 2010  
	  Description: An object follows a complex path 
 */

package ijeoma.motion.tween.test;

import processing.core.PApplet;
import processing.core.PFont;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;

public class TweenSequence_3DPath_Box_2 extends PApplet {
	PFont f;

	TweenSequence ts;

	int HALF_SIZE = 100;
float[][] path = { { HALF_SIZE, HALF_SIZE, HALF_SIZE },
		{ HALF_SIZE * 1.5f, 0, 0 }, { HALF_SIZE, -HALF_SIZE, -HALF_SIZE },
		{ 0, -HALF_SIZE * 1.5f, 0 }, { -HALF_SIZE, -HALF_SIZE, HALF_SIZE },
		{ -HALF_SIZE * 1.5f, 0, 0 }, { -HALF_SIZE, HALF_SIZE, -HALF_SIZE },
		{ 0, HALF_SIZE * 1.5f, 0 } };

	public float x, y, z;

	public void setup() {
		size(400, 400, P3D);

		f = createFont("Arial", 12);
		textFont(f);
		textMode(SCREEN);

		smooth();

		z = y = z = 0;

		Motion.setup(this);

		ts = new TweenSequence();

		for (int i = 0; i < path.length; i++) {
			int next = ((i + 1) == path.length) ? 0 : i + 1;

			Tween t = new Tween();
			t.addProperty(new Property(this, "x", path[i][0], path[next][0]));
			t.addProperty(new Property(this, "y", path[i][1], path[next][1]));
			t.addProperty(new Property(this, "z", path[i][2], path[next][2]));

			// This could also be written as
			// Tween t = new Tween(new String[] { "x:" + path[i][0] + "," +
			// path[next][0], "y:" + path[i][1] + "," + path[next][1], "z:" +
			// path[i][2] + "," + path[next][2] });

			t.setDuration(50f);

			ts.appendChild(t);
		}

		ts.repeat();
		ts.play();
	}

	public void draw() {
		background(255);

		pushMatrix();
		translate(width / 2, height / 2, 0);
		rotateY(frameCount / 100.0f);
		rotateX(2.0f);
		rotateZ(frameCount / 200.0f);

		noFill();
		stroke(200);
		box(HALF_SIZE * 2);

		noFill();
		stroke(100);
		beginShape();
		for (int i = 0; i < path.length; i++)
			vertex(path[i][0], path[i][1], path[i][2]);
		endShape(CLOSE);

		translate(x, y, z);

		// This could also be written as
		// Tween t = ts.getCurrentTween();
		// translate(t.getPosition("x"), t.getPosition("y"),
		// t.getPosition("z"));

		fill(0);
		box(20);
		popMatrix();

		String time = ts.getTime() + " / " + ts.getDuration();

		fill(lerpColor(0xFFFF0000, 0xFFFF0000, ts.getSeekPosition()));
		text(time, width - textWidth(time) - 10, height - 10);
	}
}
