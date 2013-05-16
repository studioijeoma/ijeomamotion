package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;

public class Tween_Repeat extends PApplet {
	Tween t;

	float w = 0;

	@Override
	public void setup() {
		size(400, 100);
		smooth();

		Motion.setup(this);

		t = new Tween(this, "w", width, 100f).repeat(2).play();
	}

	@Override
	public void draw() {
		background(255);

		noStroke();

		fill(255 / 2f);
		rect(0, 0, w, height);

		fill(0);
		text(t.getRepeatCount(), 10, height - 10);

		String time = t.getTime() + " / " + t.getDuration();
		fill(0);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	@Override
	public void keyPressed() {
		// t = new Tween(this, "w", width, 100f).repeat(2).play();
		if (key == ' ')
			t.play();
		else if (key == 'n') {
			// w = width;
			new Tween(this, "w", 0, 100f).repeat(2).play();
		} else {
			w = width * (parseInt(str(key)) + 1) / 10f;
		}

	}
}
