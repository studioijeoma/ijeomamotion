package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;

public class Tween_Override extends PApplet {
	Tween t1, t2;

	float w = 0;

	public void setup() {
		size(400, 100);
		smooth();

		Motion.setup(this);

		t1 = new Tween(100).add(this, "w", width).onEnd("end");
		t2 = new Tween(100).add(this, "w", width / 2);
	}

	public void draw() {
		background(255);

		noStroke();
		fill(255 / 2f);
		rect(0, 0, w, height);

		String time = "";
		time += t1.getTime() + " / " + t1.getDuration() + " Ð ";
		time += t2.getTime() + " / " + t2.getDuration();

		fill(0);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void keyPressed() {
		if (key == '1') {
			t1.get(0).setEnd(25);
			t1.play().removeCalls().onBegin("begin");
		} else if (key == '2') {
			t1.get(0).setEnd(50);
			t1.play().removeCalls().onEnd("end");
		}
	}

	public void begin() {
		println("begin");
	}

	public void end() {
		println("end");
	}
}
