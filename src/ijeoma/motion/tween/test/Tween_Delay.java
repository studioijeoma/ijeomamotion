package ijeoma.motion.tween.test;

import processing.core.*;

import ijeoma.motion.Motion;
import ijeoma.motion.MotionConstant;
import ijeoma.motion.tween.Tween;

public class Tween_Delay extends PApplet {
	PFont font;

	Tween t;

	@Override
	public void setup() {
		size(400, 100);
		smooth();

		font = createFont("Arial", 12);
		textFont(font);

		Motion.setup(this);

		// Tween(begin, end, duration, delay);

		// This creates a tween that begins at 0, ends at 400, delays for 100
		// frames, and plays for 100 frames
		t = new Tween("t", 0f, width, 100f, 100f);
		t.play();
	}

	public void draw() {
		background(255);

		noStroke();
		fill(0);
		rect(0, 0, t.getPosition(), height);

		int seekColor = lerpColor(0xFF00FF00, 0xFFFF0000, t.getSeekPosition());
		stroke(seekColor);
		fill(seekColor);
		
		line(t.getSeekPosition() * width, 0, t.getSeekPosition() * width,
				height);

		String timeAsString = t.getDelayTime() + " / " + t.getDelayDuration();
		text(timeAsString, width - textWidth(timeAsString) - 10, height - 10);
	}

	public void keyPressed() {
		t.play();
	}
}
