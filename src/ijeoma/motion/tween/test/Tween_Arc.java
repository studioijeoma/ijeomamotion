package ijeoma.motion.tween.test;

import ijeoma.geom.Path;
import ijeoma.motion.Motion;
import ijeoma.motion.tween.ArcProperty;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PVector;

public class Tween_Arc extends PApplet {
	Tween t;

	float w = 0;
	float x = 0;

	ArcProperty ap;
	PVector v, end;

	public void setup() {
		size(400, 400);
		smooth();

		Motion.setup(this);

		setupTween();
	}

	public void setupTween() {
		v = new PVector(width / 2, height / 2);
		end = new PVector(random(width), random(height));
		ap = new ArcProperty(v, end);
		t = new Tween(100).add(ap).play();
	}

	public void draw() {
		background(255);

		// for (int i = 0; i < 100; i++) {
		// float t = (float) i / 100;
		// ap.setPosition(t);
		// fill(255, 0, 0);
		ellipse(v.x, v.y, 10, 10);
		// }

	}

	public void keyPressed() {
		// t.play();
		setupTween();
	}

	public void mousePressed() {
		// t.pause();
	}

	public void mouseReleased() {
		// t.resume();
	}

	public void mouseDragged() {
		// t.seek((float) mouseX / width);
	}
}
