package ijeoma.motion.tween.test;

import java.util.ArrayList;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Sequence;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

public class Sequence_Vectors extends PApplet {

	PFont f;

	PVector v;

	Sequence s;

	PVector[] points;

	@Override
	public void setup() {
		size(400, 400);
		smooth();

		rectMode(CENTER);

		f = createFont("Arial", 12);

		v = new PVector(0, 0);

		Motion.setup(this);

		setupSequence();
	}

	public void setupSequence() {
		int count = 10;

		points = new PVector[count];

		for (int i = 0; i < count; i++)
			points[i] = new PVector(random(width), random(height));

		v.set(new PVector(width / 2, height / 2));

		s = new Sequence();
		for (PVector p : points)
			s.add(new Tween(50).addVector(this, "v", p));
		s.play();
	}

	@Override
	public void draw() {
		background(255);

		int r = 25;

		for (int i = 0; i < points.length; i++) {
			PVector p = points[i];

			fill(255, 0, 0);
			ellipse(p.x, p.y, r, r);

			fill(255);
			textAlign(CENTER, CENTER);
			text(str(i + 1), p.x, p.y);
		}

		noStroke();
		fill(0);
		ellipse(v.x, v.y, r, r);
		fill(255);
		text(s.getCount() + 1, v.x, v.y);

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
