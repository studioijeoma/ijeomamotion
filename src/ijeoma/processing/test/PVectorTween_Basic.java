package ijeoma.processing.test;

import processing.core.*;

import ijeoma.motion.*;
import ijeoma.processing.tween.VectorTween;

public class PVectorTween_Basic extends PApplet {
	PFont font;

	PVector v;
	VectorTween t;

	public void setup() {
		size(400, 100);
		smooth();

		font = createFont("Arial", 12);
		textFont(font);

		Motion.setup(this);

		v = new PVector();
		t = new VectorTween("t", v, new PVector(0, 0, 0), new PVector(width,
				height, 0), 100);

		t.play();
	}

	public void draw() {
		background(255);
		t.update();

		noStroke();
		fill(0);
		rect(0, 0, v.x, v.y);

		fill(lerpColor(0xFF00FF00, 0xFFFF0000, t.getSeekPosition()));

		String time = t.getTime() + " / " + t.getDuration();
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void keyPressed() {
		t.play();
	}

}
