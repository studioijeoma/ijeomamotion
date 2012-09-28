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
		rects = new Rect[50];

		ryt = new Tween(100).add(this, "ry", TWO_PI).repeat().play();

		for (int i = 0; i < rects.length; i++)
			rects[i] = new Rect();
	}

	public void draw() {
		background(0);
		// lights();
		// smooth();

		pushMatrix();
		translate(width / 2, 0);
		// translate(mouseX, mouseY);
		// translate(p.x, p.y);
		// rotateY(ry);
		rotateX(TWO_PI - ry);

		for (Rect r : rects)
			r.draw();
		popMatrix();
	}

	class Rect {
		float x, y, z;
		Tween yt;

		int offset;
		float scaleVal;

		float a;
		Tween at;

		Rect() {
			x = 0;
			y = height;

			yt = new Tween(100).add(this, "y", 0f).delay(random(100))
					.repeat().play();

			a = 0;
			at = new Tween(100).add(this, "a", TWO_PI).repeat().play();

			offset = (int) random(-50, 50);

			scaleVal = random(100);
		}

		void draw() {
			x = offset - sin(a) * scaleVal;
			z = sin(TWO_PI - a) * scaleVal;
			// z = 0;

			pushMatrix();
			fill(360 * yt.getPosition(), 360, 360);
			translate(x, y, z);
			// rect(0, 0, 10, 10);
			box(10);
			popMatrix();
		}
	}

	public void keyPressed() {
		setupRects();
	}
}
