package ijeoma.motion.tween.test;

import ijeoma.motion.Motion; 
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;

public class Tween_Basic_2 extends PApplet {
	Tween t;

	float w = 0;

	public void setup() {
		size(400, 400);
		smooth();

		Motion.setup(this);

		t = new Tween(100).add("x", 0, width / 2).add("y", 0, width).play();
	}

	public void draw() {
		background(255);

		noStroke();
		fill(0);
		rectMode(CENTER);
		rect(width / 4, height / 2, t.get(0).getValue(), t.get(1).getValue());
		rect((width / 4) * 3, height / 2, t.get("x").getValue(), t.get("y")
				.getValue());
	}

	public void keyPressed() {
		t.play();
	}

	public void mousePressed() {
		t.pause();
	}

	public void mouseReleased() {
		t.resume();
	}

	public void mouseDragged() {
		t.seek((float) mouseX / width);
	}
}
