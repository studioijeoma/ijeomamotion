package ijeoma.motion.tween.test;

import ijeoma.geom.Path;
import ijeoma.motion.Motion;
import ijeoma.motion.tween.ArcProperty;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;

public class Tween_Basic_2 extends PApplet {
	Tween t;

	float w = 0;
	float x = 0;

	ArcProperty ap;

	public void setup() {
		size(400, 400);
		smooth();

		Motion.setup(this);
	}

	public void setupTween() {
		// t = new Tween(100).add(this, "x", width).play();
	}

	public void draw() {
		// background(255);
		//
		// int x1 = width / 2;
		// int y1 = height / 2;
		// int x2 = mouseX;
		// int y2 = mouseY;
		// int x0 = (x1 + x2) / 2;
		// int y0 = (y1 + y2) / 2;
		//
		// int r = (int) Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 -
		// y0));
		// int x = x0;
		// int y = y0;
		// int width = 2 * r;
		// int height = 2 * r;
		// float startAngle = atan2(y1 - y0, x1 - x0);
		// float endAngle = atan2(y2 - y0, x2 - x0);
		//
		// stroke(0);
		// noFill();
		// ellipse(x1, y1, 10, 10);
		// ellipse(x2, y2, 10, 10);
		// fill(0);
		// ellipse(x0, y0, 10, 10);
		// noFill();
		// ellipse(x0, y0, width, height);
		//
		// stroke(255, 0, 0);
		// noFill();
		// arc(x, y, width, height, startAngle, endAngle);
		//
		// for (int i = 0; i < 100; i++) {
		// float t = (float) i / 100;
		// float a = lerpDegrees(startAngle, endAngle, t);
		// float x3 = x0 + cos(a) * r;
		// float y3 = y0 + sin(a) * r;
		// fill(255, 0, 0);
		// ellipse(x3, y3, 10, 10);
		// }
		//
		// noFill();

	}

	// https://github.com/openframeworks/openFrameworks/issues/105
	float lerpDegrees(float begin, float end, float position) {
		float change = end - begin;
		while (change > 180.0f)
			change -= 360.0f;
		while (change < -180.0f)
			change += 360.0f;

		return begin + change * position;
	}

	float lerpRadians(float begin, float end, float position) {
		float change = end - begin;
		while (change > PI)
			change -= TWO_PI;
		while (change < -PI)
			change += TWO_PI;

		return begin + change * position;
	}

	public void keyPressed() {
		t.play();
	}

	public void mousePressed() {
		t.pause();
	}

	public void mouseReleased() {
		t.resume();
	}

	public void mouseDragged() {
		t.seek((float) mouseX / width);
	}
}
