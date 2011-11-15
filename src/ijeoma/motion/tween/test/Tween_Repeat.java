package ijeoma.motion.tween.test;

import processing.core.*;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;

public class Tween_Repeat extends PApplet {
	PFont f;

	Tween t;

	public void setup() {
		size(400, 100);
		smooth();

		f = createFont("Arial", 12);
		textFont(f);

		Motion.setup(this);

		t = new Tween("t", 0f, width, 100f);
		t.repeat();

		// This sets the tween to reverse on repeat. The default mode is
		// Tween.NO_REVERSE
		t.setRepeatMode(Tween.REVERSE);

		// When the repeatMode is set to REVERSE this will reverse the tween's
		// time from 0 to 1 and vice versa on repeat. The default is
		// REVERSE_TIME
		// t.setReverseMode(Tween.REVERSE_TIME);

		// When the repeatMode is set to REVERSE this will reverse the tween's
		// begin and end values on repeat
		// t.setReverseMode(Tween.REVERSE_POSITION);

		// The repeat mode can also be set with
		// Tween.repeat(String _repeatMode)
		// t.repeat(Tween.REVERSE_TIME);

		// When repeat is set this sets the tweens repeat count
		// Note: Has a bug when in REPEAT reverse mode
		t.setRepeatCount(2);

		// The repeat count can also be set with
		// Tween.repeat(int _repeatCount)
		// t.repeat(1);

		t.play();
	}

	public void draw() {
		background(255);

		noStroke();
		fill(0);
		rect(0, 0, t.getPosition(), height);

		int timeColor = lerpColor(0xFF00FF00, 0xFFFF0000, t.getSeekPosition());
		stroke(timeColor);
		fill(timeColor);

		line(t.getSeekPosition() * width, 0, t.getSeekPosition() * width,
				height);

		String time = t.getTime() + " / " + t.getDuration();
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void keyPressed() {
		t.play();
	}
}
