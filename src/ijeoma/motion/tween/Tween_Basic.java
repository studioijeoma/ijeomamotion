package ijeoma.motion.tween;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;

public class Tween_Basic extends PApplet {
	Tween t;

	float w = 0;

	@Override
	public void setup() {
		size(400, 100);
		smooth();

		Motion.setup(this);

		t = new Tween(this, "w", width, 100).play();

		// The above could also be written as
		// t = new Tween(100).add(this, "w", width).play();
	}

	@Override
	public void draw() {
		background(255);

		noStroke();

		fill(255 / 2f);
		rect(0, 0, w, height);

		String time = t.getTime() + " / " + t.getDuration();

		fill(0);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	@Override
	public void keyPressed() {
		t.play();
	}
}
