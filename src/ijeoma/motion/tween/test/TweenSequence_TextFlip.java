package ijeoma.motion.tween.test;

import processing.core.PApplet;
import processing.core.PFont;
import ijeoma.motion.*;
import ijeoma.motion.timeline.*;
import ijeoma.motion.tween.*;

public class TweenSequence_TextFlip extends PApplet {
	PFont f;

	TweenSequence ts;
	Tween t;

	public void setup() {
		size(400, 200);
		smooth();

		f = createFont("Arial", 100);
		textFont(f, 100);

		Motion.setup(this);

		ts = new TweenSequence();
		ts.setName("FLIP");

		for (int i = 0; i < ts.getName().length(); i++) {
			Tween t = new Tween(str(ts.getName().charAt(i)), 1, -1,
					(i > 0) ? -10 : 0);
			// t.setEasing(Tween.BOUNCE_EASE_OUT);
			ts.appendChild(t);
		}

		ts.repeat();
		ts.play();
	}

	public void draw() {
		background(255);

		textFont(f, 100);

		float letterX = 0;
		float letterXOffset = width / 2 - textWidth(ts.getName()) / 2;
		float letterYOffset = height / 2 - 100 / 2;
		float letterHeight = textAscent();

		for (int i = 0; i < ts.getChildCount(); i++) {
			Tween t = ts.getTween(i);

			pushMatrix();
			translate(letterX + letterXOffset, letterYOffset);
			scale(1, t.getPosition());
			fill(0);
			text(t.getName(), 0, 0);
			popMatrix();

			letterX += textWidth(t.getName());
		}

		drawUI();
	}

	public void drawUI() {
		int red = color(255, 0, 0);
		int green = color(0, 255, 0);

		// This draws the seek for the ts TweenParallel object
		stroke(lerpColor(green, red, ts.getSeekPosition()));
		line(ts.getSeekPosition() * width, 0, ts.getSeekPosition() * width,
				height);

		// This draws the time for the ts TweenSequence object
		String time = (int) ts.getTime() + " / " + (int) ts.getDuration();

		fill(lerpColor(green, red, ts.getSeekPosition()));
		textFont(f, 10);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void keyPressed() {
		ts.play();
	}

	public void mousePressed() {
		ts.pause();
	}

	public void mouseReleased() {
		ts.resume();
	}

	public void mouseDragged() {
		ts.seek((float) mouseX / width);
	}
}
