package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;

public class Tween_Events_1 extends PApplet {
	Tween t;

	float w = 0;

	@Override
	public void setup() {
		size(400, 100);
		smooth();

		Motion.setup(this);

		t = new Tween(this, "w", width, 100).play();
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

	public void tweenStarted(Tween _t) {
		println(_t + " started");
	}

	public void tweenEnded(Tween _t) {
		println(_t + " ended");
	}

	// public void tweenChanged(Tween _t) {
	// println(_t + " changed");
	// }

	public void tweenRepeated(Tween _t) {
		println(_t + " repeated");
	}
}
