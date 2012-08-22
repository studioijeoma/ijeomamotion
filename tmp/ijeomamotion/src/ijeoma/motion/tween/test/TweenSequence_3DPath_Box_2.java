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

		x = path[0][0];
		y = path[0][1];
		z = path[0][2];

		Motion.setup(this);

		ts = new TweenSequence();

		for (int i = 0; i < path.length; i++) {
			int next = ((i + 1) == path.length) ? 0 : i + 1;

			ts.add(new Tween(50).add(this, "x", path[next][0])
					.add(this, "y", path[next][1])
					.add(this, "z", path[next][2]));
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

		fill(0);
		box(20);
		popMatrix();

		String time = ts.getTime() + " / " + ts.getDuration();

		fill(lerpColor(0xFFFF0000, 0xFFFF0000, ts.getPosition()));
		text(time, width - textWidth(time) - 10, height - 10);
	}
}
