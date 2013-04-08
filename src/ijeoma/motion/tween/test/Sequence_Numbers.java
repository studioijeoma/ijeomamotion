package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Sequence;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

public class Sequence_Numbers extends PApplet {

	PFont f;

	float x;

	Sequence s;

	float[] xs;

	@Override
	public void setup() {
		size(400, 400);
		smooth();

		rectMode(CENTER);

		f = createFont("Arial", 12);

		Motion.setup(this);

		setupSequence();
	}

	public void setupSequence() {
		int count = 2;

		xs = new float[count];

		for (int i = 0; i < count; i++)
			xs[i] = random(width);
		// xs[0] = 25;
		// xs[1] = 75;

		x = width / 2;

		s = new Sequence();
		for (int i = 0; i < xs.length; i++)
			s.add(new Tween(100).add(this, "x", xs[i]));
		// s.getTween(0).get("x").setBegin(x);
		s.play();
	}

	@Override
	public void draw() {
		background(255);

		for (int i = 0; i < xs.length; i++) {
			float x = xs[i];

			fill(255, 0, 0);
			ellipse(x, height / 2, 20, 20);

			fill(255);
			textAlign(CENTER, CENTER);
			text(str(i + 1), x, height / 2);
		}

		noStroke();
		fill(0);
		ellipse(x, height / 2, 25, 25);

		String time = s.getTime() + " / " + s.getDuration();
		time += " Ð " + s.getPosition() + " / 1";

		fill(0);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void keyPressed() {
		setupSequence();
	}

	public void mousePressed() {
		s.pause();
	}

	public void mouseReleased() {
		s.resume();
	}

	public void mouseDragged() {
		s.seek((float) mouseX / width);
	}
}
