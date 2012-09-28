package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PFont;

public class Tween_Numbers_1 extends PApplet {

	Tween t;
	PFont f;

	public float x = 0;
	public float y = 400;

	@Override
	public void setup() {
		size(400, 400);
		smooth();

		rectMode(CENTER);

		f = createFont("Arial", 12);

		Motion.setup(this);

		t = new Tween(100).add(this, "x", 400f).add(this, "y", 0f).repeat()
				.play();

		// The above could also be written as
		// t = new Tween(this, "x", 400, 100).add(this, "y", 0).repeat().play();
	}

	@Override
	public void draw() {
		background(255);

		noStroke();

		fill(255 / 2f);
		rect(x, y, 50, 50);

		String time = t.getTime() + " / " + t.getDuration();
		fill(0);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	@Override
	public void keyPressed() {
		t.play();
	}
}
