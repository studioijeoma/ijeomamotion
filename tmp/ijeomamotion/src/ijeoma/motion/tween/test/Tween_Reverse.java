package ijeoma.motion.tween.test;

import processing.core.*;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;

public class Tween_Reverse extends PApplet {
	Tween t;

	float w = 0;

	public void setup() {
		size(400, 100);
		smooth();

		Motion.setup(this);

		t = new Tween(this, "w", width, 100f).repeat().reverse().play();
	}

	public void draw() {
		background(255);

		noStroke();

		fill(255 / 2f);
		rect(0, 0, w, height);

		fill(0);
		text(t.getRepeatDuration(), 10, height - 10);

		String time = t.getTime() + " / " + t.getDuration();
		fill(0);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void keyPressed() {
		t.play();
	}
}
