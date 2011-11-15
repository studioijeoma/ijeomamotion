package ijeoma.motion.tween.test;

import processing.core.*;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;

public class Tween_Bezier extends PApplet {

	Tween t;

	float x1, y1, cx1, cy1, cx2, cy2, x2, y2;

	public void setup() {
		size(100, 100);
		smooth();

		// bezier(x1, y1, cx1, cy1, cx2, cy2, x2, y2);
		// bezier(85, 20, 10, 10, 90, 90, 15, 80);

		x1 = 85;
		y1 = 20;
		cx1 = 10;
		cy1 = 10;
		cx2 = 90;
		cy2 = 90;
		x2 = 15;
		y2 = 80;

		Motion.setup(this);
		t = new Tween("t", 0f, 1f, 100);
		// t.repeat(Tween.REVERSE);
		t.play();
	}

	public void draw() {
		background(255);

		noFill();
		bezier(x1, y1, cx1, cy1, cx2, cy2, x2, y2);

		float x = bezierPoint(x1, cx1, cx2, x2, t.getPosition());
		float y = bezierPoint(y1, cy1, cy2, y2, t.getPosition());

		fill(0);
		ellipse(x, y, 10, 10);
	}

	public void keyPressed() {
		t.play();
	}
}
