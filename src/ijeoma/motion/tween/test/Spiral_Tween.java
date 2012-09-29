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
		spirals = new Spiral[1];

		for (int i = 0; i < spirals.length; i++)
			spirals[i] = new Spiral();
	}

	public void draw() {
		background(0);
		// lights();
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

		Tween t;

		Spiral() {
			begin = random(1);
			count = (int) random(10, 20);
			spacing = (int) random(0, 20);
			rate = random(5);
			c = color(random(255), random(255), random(255));
			d = 0;

			t = new Tween(random(100, 200)).add(this, "d", 360 * count)
					.delay(random(100)).repeat().play();
		}

		void draw() {
			// float r = begin;

			// for (int i = 0; i < 360 * count; i += spacing) {
			float a = radians(d);
			float x = cos(a) * r;
			float y = sin(a) * r;

			noStroke();
			fill(c);
			pushMatrix();
			translate(x, y, 0);
			// ellipse(0, 0, 10, 10);
			box(10);
			popMatrix();

			r += .3;
			// r = r + .5f;
			// }
		}
	}
}
