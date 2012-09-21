package ijeoma.motion.tween.test;

import java.util.ArrayList;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;
import processing.core.PApplet;

//based on Tween.js 01_bars.html example: http://sole.github.com/tween.js/examples/01_bars.html
public class Tween_Bars extends PApplet {
	Tween t;

	float w = 0;

	ArrayList<Rect> rects = new ArrayList<Rect>();

	public void setup() {
		size(800, 600);
		smooth();

		Motion.setup(this);

		for (int i = 0; i < 1000; i++)
			rects.add(new Rect(random(width), random(height), 100, 1));

		for (Rect r : rects)
			t = new Tween(100)
					.add(r, "x", random(width))
					.add(r, "c",
							(int) color(random(255), random(255), random(255)))
					.play();
	}

	public void draw() {
		background(255);

		noStroke();

		for (Rect r : rects)
			r.draw();

		fill(0);
		text((int)frameRate, width - textWidth(str((int)frameRate)) - 25, height - 25);
	}

	public void keyPressed() {
	}

	public void tweenEnded(Tween t) {
		t.get("x").setEnd(random(width));
		t.get("c").setEnd(color(random(255), random(255), random(255)));
		t.play();
	}

	class Rect {
		float x, y, w, h;
		int c;

		Rect(float x, float y, float w, float h) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			c = color(random(255), random(255), random(255));
		}

		void draw() {
			noStroke();
			fill(c);
			rect(x, y, w, h);
		}
	}
}
