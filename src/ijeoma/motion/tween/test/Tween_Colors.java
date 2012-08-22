package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PFont;

public class Tween_Colors extends PApplet {

	Tween t;
	PFont f;

	int c1, c2;

	@Override
	public void setup() {
		size(400, 400);
		smooth();

		f = createFont("Arial", 12);

		c1 = color(255, 0, 0);
		c2 = color(255, 255, 0);

		Motion.setup(this);

		t = new Tween(100).addColor(this, "c1", color(0, 0, 255))
				.addColor(this, "c2", color(0, 255, 255)).play();

		// The above could also be written as
		// t = new Tween(100).add(new ColorProperty(this, "c1", color(0, 0,
		// 255)))
		// .add(new ColorProperty(this, "c2", color(0, 255, 255))).play();
	}

	@Override
	public void draw() {
		background(255);

		fill(c1);
		rect(0, 0, width / 2, height);
		fill(c2);
		rect(width / 2, 0, width / 2, height);

		String time = t.getTime() + " / " + t.getDuration();
		fill(0);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	@Override
	public void keyPressed() {
		t.play();
	}
}
