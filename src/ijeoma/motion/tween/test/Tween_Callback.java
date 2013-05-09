package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;

public class Tween_Callback extends PApplet {
	Tween t;

	float s = 0;

	public void setup() {
		smooth();

		Motion.setup(this);

		t = new Tween(100).add(this, "s", width).onBegin("onBegin")
				.onEnd("onEnd").onChange(this, "onChange").play();
	}

	public void draw() {
		background(255);

		String time = (int) t.getTime() + " / " + (int) t.getDuration();

		noStroke();
		fill(0);
		rectMode(CENTER);
		rect(width / 2, height / 2, s, s);

		fill(255, 0, 0);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void onBegin(Tween t) {
		println(t + " begin");
	}

	public void onEnd(Tween t) {
		println(t + " end");
	}

	public void onChange(Tween t) {
		// println(t + " change");
	}

	public void keyPressed() {
		t.play();
	}
}
