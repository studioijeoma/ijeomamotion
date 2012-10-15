package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;

public class Tween_Basic extends PApplet {
	Tween t;

	float w = 0;

	public void setup() {
		size(400, 100);
		smooth();

		Motion.setup(this);

		// t = new Tween(this, "w", (float) width,
		// 100).repeat().reverse().play();
		t = new Tween(this, "w", (float) width, 100).play();

		// The above could also be written as
		// t = new Tween(100).add(this, "w", width).play();
	}

	public void draw() {
		background(255);

		float s = map(mouseX, 0, width, 0, 1);
		// t.setTimeScale(s);

		noStroke();
		fill(255 / 2f);
		rect(0, 0, w, height); 

		String time = t.getTime() + " / " + t.getDuration();
		// String time = t.getPosition() + " / 1";

		fill(0);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void keyPressed() {
		// if (t.isPlaying())
		// t.pause();
		// else
		// t.resume();
		t.play();
		// w = 0;
		// t = new Tween(100).add(this, "w", (float) width).play();
	}

	public void mousePressed() {
		t.pause();
	}

	public void mouseReleased() {
		t.resume();
	}

	public void mouseDragged() {
		t.seek((float) mouseX / width);
	}
}
