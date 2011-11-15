package ijeoma.motion.tween.test;

import processing.core.*;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;

public class Tween_RectFlip extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	PFont font;

	Tween t;

	public void setup() {
		size(400, 200);

		rectMode(CENTER);
		smooth();

		font = createFont("Arial", 36);

		Motion.setup(this);
		t = new Tween("t", 1, -1, 100, 0, Tween.BOUNCE_OUT);
		t.repeat();
		t.play();
	}

	public void draw() {
		background(255);
		pushMatrix();
		translate(width / 2, height / 2);
		scale(t.getPosition(), 1);
		rect(0, 0, 100, 100);
		popMatrix();
	}

	public void keyPressed() {
		t.play();
	}
}
