package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.NumberProperty;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PFont;

public class Tween_Gradients extends PApplet {

	Tween t;
	PFont f;

	int c1, c2;

	@Override
	public void setup() {
		size(400, 400);
		smooth();

		f = createFont("Arial", 12);

		c1 = color(255, 0, 0);
		c2 = color(255, 255, 0);

		Motion.setup(this);

		t = new Tween(100)
				.addColor(this, "c1",
						color(random(255), random(255), random(255)))
				.addColor(this, "c2",
						color(random(255), random(255), random(255)))
				.setEasing(Tween.SINE_BOTH).play();
	}

	@Override
	public void draw() {
		background(255);

		for (int i = 0; i <= width; i++) {
			float j = map(i, 0, width, 0, 1);
			int c = lerpColor(c1, c2, j);
			stroke(c);
			line(i, 0, i, height);
		}

		String time = t.getTime() + " / " + t.getDuration();
		fill(0);
		text(time, width - textWidth(time) - 10, height - 10);
	}

	public void tweenEnded(Tween t) {
		t.getColor("c1").setEnd(color(random(255), random(255), random(255)));
		t.getColor("c2").setEnd(color(random(255), random(255), random(255)));
		t.play();
	}

	@Override
	public void keyPressed() {
		t.play();
	}
}
