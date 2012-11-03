package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PFont;

public class Tween_Delay extends PApplet {
	PFont font;

	Tween t;
	float w = 0;

	@Override
	public void setup() {
		size(400, 100);
		smooth();

		font = createFont("Arial", 12);
		textFont(font);

		Motion.setup(this);

		// Tween(propertyObject, propertyName, begin, end, duration, delay);
		// t = new Tween(this, "w", width, 50, 50).play();
		t = new Tween(this, "w", width, 50).delay(50).repeat().repeatDelay().play();
	}

	@Override
	public void draw() {
		background(255);

		noStroke();
		fill(255 / 2f);
		rect(0, 0, w, height);

		String time = t.getTime() + " / " + t.getDuration();
		// String time = t.getDelayTime() + " / " + t.getDelayedDuration();
		// String time = t.getPosition() + "/" + t.getTime() + " / "
		// + t.getDuration();
		fill(0);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	@Override
	public void keyPressed() {
		t.play();
	}
}
