package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

public class Tween_Vector extends PApplet {

	Tween t;
	PFont f;

	PVector v1, v2;

	@Override
	public void setup() {
		size(400, 400);
		smooth();

		rectMode(CENTER);

		f = createFont("Arial", 12);

		v1 = new PVector(0, 0);
		v2 = new PVector(width, 0);

		Motion.setup(this);

		t = new Tween(100).addVector(v1, new PVector(width, height))
				.addVector(v2, new PVector(0, height)).play();

		// The above could also be written as
		// t = new Tween(100)
		// .add(new PVectorProperty(v1, new PVector(width, height)))
		// .add(new PVectorProperty(v1, new PVector(0, height))).play();
	}

	@Override
	public void draw() {
		background(255);

		noStroke();
		fill(0);
		rect(v1.x, v1.y, 25, 25);
		rect(v2.x, v2.y, 25, 25);

		fill(0);
		String time = t.getTime() + " / " + t.getDuration();
		text(time, width - textWidth(time) - 10, height - 10);
	}

	@Override
	public void keyPressed() {
		t.play();
	}
}
