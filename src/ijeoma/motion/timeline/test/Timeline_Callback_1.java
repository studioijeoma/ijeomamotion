package ijeoma.motion.timeline.test;

import processing.core.PApplet;
import processing.core.PFont;

import ijeoma.motion.*;
import ijeoma.motion.timeline.*;

public class Timeline_Callback_1 extends PApplet {
	PFont font;

	Timeline t;

	String word = "";

	public void setup() {
		size(400, 200);

		ellipseMode(CORNER);
		smooth();

		font = createFont("Arial", 50);
		textFont(font);

		Motion.setup(this);

		t = new Timeline();
		// Timeline.addCallback(String _callbackObjectMethodName, float _time,
		// float _duration)
		t.insertCallback("zero", 0, 100);
		t.insertCallback("one", 100, 100);
		t.insertCallback("two", 200, 100);
		t.insertCallback("three", 300, 100);
		t.insertCallback("four", 400, 100);
		t.insertCallback("five", 500, 100);
		t.repeat();
		t.play();

		println(t.getChildren());
//		exit();
	}

	public void draw() {
		background(255);

		fill(0);
		textFont(font, 36);
		text(word, width / 2 - textWidth(word) / 2, height / 2 + 36 / 2);

		int seekColor = lerpColor(color(0, 255, 0), color(255, 0, 0),
				t.getSeekPosition());

		stroke(seekColor);
		line(t.getSeekPosition() * width, 0, t.getSeekPosition() * width,
				height);

		fill(seekColor);
		textFont(font, 12);
		text(t.getTimeCode(), width - textWidth(t.getTimeCode()) - 10,
				height - 10);
	}

	public void zero() {
		word = "ZERO";
	}

	public void one() {
		word = "ONE";
	}

	public void two() {
		word = "TWO";
	}

	public void three() {
		word = "THREE";
	}

	public void four() {
		word = "FOUR";
	}

	public void five() {
		word = "FIVE";
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
		t.seek(norm(mouseX, 0, width));
	}
}
