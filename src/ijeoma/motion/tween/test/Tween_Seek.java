package ijeoma.motion.tween.test;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;
import processing.core.*;

public class Tween_Seek extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * click and drag from left to right to seek from beginning to end.
	 */

	PFont f;

	Tween t;

	public void setup() {
		size(400, 100);

		smooth();

		f = createFont("Arial", 12);
		textFont(f);

		Motion.setup(this);

		t = new Tween("t1", 0, width, 100);
		t.play();
	}

	public void draw() {
		background(255);

		noStroke();
		fill(0);
		rect(0, 0, t.getPosition(), height);

		String time = t.getTime() + " / " + t.getDuration();

		fill(lerpColor(0xFF00FF00, 0xFFFF0000, t.getSeekPosition()));
		text(time, width - textWidth(time) - 10, height - 10);
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
