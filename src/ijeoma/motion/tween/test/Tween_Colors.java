package ijeoma.motion.tween.test;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;
import processing.core.*;

public class Tween_Colors extends PApplet {
	Tween t;
	PFont f;

	int c1, c2;

	public void setup() {
		size(400, 400);
		smooth();

		f = createFont("Arial", 12);

		c1 = color(255, 0, 0);
		c2 = color(255, 255, 0);

		Motion.setup(this);

		t = new Tween(100)
				.add(this, "c1", color(random(255), random(255), random(255)))
				.add(this, "c2", color(random(255), random(255), random(255)))
				.play();

		// The above could also be written as
		// t = new Tween(100).addColor(this, "c1", color(0, 0, 255))
		// .addColor(this, "c2", color(0, 255, 255)).play();
		//
		// or
		//
		// t = new Tween(100).add(new ColorProperty(this, "c1", color(0, 0,
		// 255)))
		// .add(new ColorProperty(this, "c2", color(0, 255, 255))).play();
	}

	public void draw() {
		background(255);

		noStroke();
		fill(c1);
		rect(0, 0, width / 2, height);
		fill(c2);
		rect(width / 2, 0, width / 2, height);

		String time = (int) t.getTime() + " / " + (int) t.getDuration();
		fill(0);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void keyPressed() {
		t.play();
	}

	void tweenEnded(Tween t) {
		t.getColor("c1").setEnd(color(random(255), random(255), random(255)));
		t.getColor("c2").setEnd(color(random(255), random(255), random(255)));
		t.play();
	}

}
