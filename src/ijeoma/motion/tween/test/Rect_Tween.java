package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PVector;

public class Rect_Tween extends PApplet {
	Rect[] rects;

	float rx, ry, rz;
	Tween ryt;

	public void setup() {
		size(500, 500, P3D);
		colorMode(HSB);

		Motion.setup(this);

		ryt = new Tween(400).add(this, "ry", TWO_PI).repeat().play();

		setupRects();
	}

	public void setupRects() {
		rects = new Rect[100];

		ryt = new Tween(50).add(this, "ry", TWO_PI).repeat().play();

		for (int i = 0; i < rects.length; i++)
			rects[i] = new Rect();
	}

	public void draw() {
		background(0);
		lights();
		// smooth();

		pushMatrix();
		translate(width / 2, 0);
		// translate(mouseX, mouseY);
		// translate(p.x, p.y);
		// rotateZ(ry);
		// rotateX(TWO_PI - ry);

		for (Rect r : rects)
			r.draw();
		popMatrix();
	}

	class Rect {
		float x, y, z;
		Tween yt;

		float r;

		float s;

		float a;
		Tween at;

		Rect() {
			x = 0;
			y = height;

			yt = new Tween(random(25, 100)).add(this, "y", 0f).delay(0)
					.repeat().play();

			r = random(50, 200);
			s = random(5, 50);

			a = random(TWO_PI);
			at = new Tween(100).add(this, "a", TWO_PI).repeat().play();
		}

		void draw() {
			x = (cos(a) * r);
			z = (sin(a) * r);

			pushMatrix();
			noStroke();
			fill(360 * yt.getPosition(), 360, 360);
			translate(x, y, z);
			// rect(0, 0, 10, 10);
			box(s);
			popMatrix();
		}
	}

	public void keyPressed() {
		setupRects();
	}
}
