package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PVector;

public class Spiral_Tween extends PApplet {
	float rx, ry, rz;
	Tween ryt;

	PVector p;
	Tween pt;

	Spiral[] spirals;

	public void setup() {
		size(500, 500, P3D);
		smooth();

		Motion.setup(this);

		setupSpirals();

		ryt = new Tween(400).add(this, "ry", TWO_PI).repeat().play();
	}

	public void setupSpirals() {
		background(0);

		spirals = new Spiral[1];

		for (int i = 0; i < spirals.length; i++)
			spirals[i] = new Spiral();
	}

	public void draw() {
		background(0);
		lights();
		smooth();

		pushMatrix();
		translate(width / 2, height / 2);
		// rotateZ(ry);
		// rotateX(TWO_PI - ry);

		for (Spiral s : spirals)
			s.draw();
		popMatrix();

		// String time = pt.getTime() + " / " + pt.getDuration();
		//
		// fill(255);
		// text(time, width - textWidth(time) - 10, height - 10);
	}

	public void keyPressed() {
		setupSpirals();
	}

	class Spiral {
		float begin;
		int count;
		int spacing;
		float rate;
		int c;
		float r;
		float d;

		Tween dt, rt;

		Spiral() {
			count = (int) random(10, 20);
			rate = random(5);
			c = color(random(255), random(255), random(255));
			d = -100;

			r = 0;

			dt = new Tween(200).add(this, "d", TWO_PI).delay(0).repeat().play();
			rt = new Tween(400).add(this, "r", width / 2).delay(0).repeat()
					.play();
		}

		void draw() {
			float x = cos(d) * r;
			float y = sin(d) * r;

			pushMatrix();
			translate(x, y, 0);
			stroke(255);
			noFill();
			// box(10);
			ellipse(0, 0, 10, 10);
			popMatrix();

			stroke(255);
			noFill();
			ellipse(0, 0, r, r);
		}
	}
}
