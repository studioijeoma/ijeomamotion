package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;

public class Tween_Callback extends PApplet {
	Tween t;

	float w = 0;

	@Override
	public void setup() {
		size(400, 100);
		smooth();

		Motion.setup(this);

		t = new Tween(10).call(this, "test", 5).setTimeMode(Tween.SECONDS)
				.play();

		// The above could also be written as
		// t = new Tween(100).add(this, "w", width).play();
	}

	public void test() {
		println("test");
	}

	@Override
	public void draw() {
		background(255);

		noStroke();

		fill(255 / 2f);
		rect(0, 0, w, height);

		String time = (int) t.getTime() + " / " + (int) t.getDuration();

		fill(0);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	@Override
	public void keyPressed() {
		t.play();
	}
}
