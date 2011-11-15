package ijeoma.motion.timeline.test;

import processing.core.PApplet;
import processing.core.PFont;
import ijeoma.motion.*;
import ijeoma.motion.timeline.*;
import ijeoma.motion.tween.*;

public class Timeline_TextFlip extends PApplet {
	PFont f;

	Timeline tl;

	public void setup() {
		size(400, 200, P3D);
		// smooth();

		f = createFont("Arial", 100);
		textFont(f, 100);

		Motion.setup(this);

		tl = new Timeline();
		tl.setName("FLIP");

		for (int i = 0; i < tl.getName().length(); i++) {
			Tween t = new Tween(str(tl.getName().charAt(i)), 1, -1, 100);
			t.setEasing(Tween.CIRC_BOTH);
			tl.insertChild(t, (i > 0) ? 10 * i : 0);
		}

		tl.repeat(Timeline.REVERSE);
		tl.play();
	}

	public void draw() {
		background(255);

		textFont(f, 100);

		float letterX = 0;
		float letterHeight = textAscent();
		float letterXOffset = width / 2 - textWidth(tl.getName()) / 2;
		float letterYOffset = height / 2 + letterHeight / 2;

		line(0, 100, width, 100);

		for (int i = 0; i < tl.getChildCount(); i++) {
			KeyFrame kf = (KeyFrame) tl.getChild(i);

			for (int j = 0; j < kf.getChildCount(); j++) {
				Tween t = kf.getTween(j);

				pushMatrix();
				translate(letterX + letterXOffset, letterYOffset
						- (letterHeight * t.getSeekPosition()));
				scale(1, t.getPosition());
				fill(0);
				text(t.getName(), 0, 0);
				popMatrix();

				letterX += textWidth(t.getName());
			}
		}

		drawUI();
	}

	public void drawUI() {
		int red = color(255, 0, 0);
		int green = color(0, 255, 0);

		// This draws the seek for the ts TweenParallel object
		stroke(lerpColor(green, red, tl.getSeekPosition()));
		line(tl.getSeekPosition() * width, 0, tl.getSeekPosition() * width,
				height);

		// This draws the time for the ts TweenSequence object
		String time = (int) tl.getTime() + " / " + (int) tl.getDuration();

		fill(lerpColor(green, red, tl.getSeekPosition()));
		textFont(f, 10);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void keyPressed() {
		tl.play();
	}

	public void mousePressed() {
		tl.pause();
	}

	public void mouseReleased() {
		tl.resume();
	}

	public void mouseDragged() {
		tl.seek((float) mouseX / width);
	}
}
