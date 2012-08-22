package ijeoma.motion.tween.test;

import processing.core.*;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

public class Tween_Basic extends PApplet {
	Tween t;

	float w = 0;

	public void setup() {
		size(400, 100);
		smooth();

		Motion.setup(this);

		t = new Tween(this, "w", width, 100).play();

		// The above could also be written as
		// t = new Tween(100).add(this, "w", width).play();
	}

	public void draw() {
		background(255);

		noStroke();

		fill(255 / 2f);
		rect(0, 0, w, height);

		String time = t.getTime() + " / " + t.getDuration();

		fill(0);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void keyPressed() {
		t.play();
	}
}
