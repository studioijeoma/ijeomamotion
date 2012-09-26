package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Parallel;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;

public class Arc_Tween extends PApplet {
	Tween t;

	int arcCount = 100;
	Arc[] arcs;
	Parallel arcTweens;

	Tween rotationTween;
	float rx, ry, rz;

	public void setup() {
		size(400, 400, P3D);
		smooth();

		Motion.setup(this);

		setupArcs();

		rotationTween = new Tween(300).add(this, "ry", TWO_PI).repeat().play();
	}

	public void setupArcs() {
		arcCount = 10;
		float arcRMax = 400;

		arcs = new Arc[arcCount];
		arcTweens = new Parallel();

		for (int i = 0; i < arcCount; i++) {
			arcs[i] = new Arc();
			arcs[i].r = i * arcRMax / arcCount;
			arcs[i].b = random(TWO_PI);
			arcs[i].e = arcs[i].b + random(PI / 4, PI);
			arcs[i].p = arcs[i].b;

			arcTweens.add(new Tween(100).add(arcs[i], "p", arcs[i].e));
		}

		arcTweens.play();
	}

	public void draw() {
		background(0);
		// lights();
		// smooth();

		translate(width / 2, height / 2);
		// rotateY(ry);
		for (int i = 0; i < arcs.length; i++)
			arcs[i].draw();
	}

	class Arc {
		float r, w;
		float b, e, p;
		int c;
		float rx, ry;

		Arc() {
			c = color(random(255), random(255), random(255));
			w = random(1, 10);
			rx = random(TWO_PI);
			ry = random(TWO_PI);
		}

		void draw() {
			strokeWeight(w);
			stroke(c);
			noFill();
			pushMatrix();
			// rotateX(rx);
			// rotateY(ry);
			arc(0, 0, r, r, b, p);
			popMatrix();
		}
	}

	public void tweenEnded(Tween t) {
		// t.setDelay(random(100));
		// t.setDelay(0);
		// t.setDuration(random(10, 100));

		// float b = t.getNumber("p").getEnd();
		// float e = random(PI / 4, PI);

		//
		// t.getNumber("p").setBegin(b);
		// t.getNumber("p").setEnd(e);

		arcTweens.play();
	}

	public void keyPressed() {
		setupArcs();
		// rotationTween.play();
	}

	public void mousePressed() {
		arcTweens.pause();
		rotationTween.pause();
	}

	public void mouseReleased() {
		arcTweens.resume();
		rotationTween.resume();
	}

	public void mouseDragged() {
		// arcTweens.seek((float) mouseX / width);
		rotationTween.seek((float) mouseX / width);
	}
}
