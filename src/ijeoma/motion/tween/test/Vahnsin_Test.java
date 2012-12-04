package ijeoma.motion.tween.test;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;

import processing.core.*;

//https://forum.processing.org/topic/ijeomamotion-tweening-both-vector-and-number-properties
public class Vahnsin_Test extends PApplet {
	Tween t;
	PFont f;
	Box b;

	public void setup() {
		size(400, 400);
		rectMode(CENTER);
		f = createFont("Arial", 12);
		Motion.setup(this);
		b = new Box();
		t = new Tween(1000).addPVector(b.pos, new PVector(width, height))
				.addNumber(b, "opacity", 0).play();

		// t = new Tween(100).addNumber(b.pos, "x", width)
		// .addNumber(b.pos, "y", height).addNumber(b, "opacity", 0)
		// .play();
	}

	public void draw() {
		background(255);
		noStroke();
		fill(0, b.opacity);
		rect(b.pos.x, b.pos.y, 25, 25);

		text("Opacity: " + b.opacity, 10, height - 10);
		text("% Done: " + t.getPosition(), 10, height - 30);
	}

	public void keyPressed() {
		t.play();
	}

	private class Box {
		PVector pos = new PVector(0, 0);
		float opacity = 255;
	}
}
