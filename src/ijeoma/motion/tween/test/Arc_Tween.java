package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PVector;

public class Arc_Tween extends PApplet {
	int arcCount = 100;
	Arc[] arcs;

	float rx, ry, rz;
	Tween ryt;

	PVector p;
	Tween pt;

	boolean isFlashing = false;

	public void setup() {
		size(500, 500, P3D);
		smooth();

		Motion.setup(this);

		setupArcs();

		ryt = new Tween(100).add(this, "ry", TWO_PI).repeat().play();

		p = new PVector(width / 2, height / 2);
		pt = new Tween(50).add(p, "x", random(200, width - 200))
				.add(p, "y", random(200, height - 200))
				.setEasing(Tween.CIRC_OUT).play();

	}

	public void setupArcs() {
		arcCount = 100;

		arcs = new Arc[arcCount];

		for (int i = 0; i < arcCount; i++)
			arcs[i] = new Arc(i * 400f / arcCount);
	}

	public void draw() {
		background(0);
		lights();
		smooth();

		pushMatrix();
		translate(width / 2, height / 2);
		// translate(mouseX, mouseY);
		// translate(p.x, p.y);
		rotateY(ry);
		rotateZ(TWO_PI - ry);

		colorMode(RGB);
		strokeWeight(2);
		stroke(255, 100);
		noFill();
		box(400);

		for (Arc a : arcs)
			a.draw();
		popMatrix();

		isFlashing = false;

		// String time = pt.getTime() + " / " + pt.getDuration();
		//
		// fill(255);
		// text(time, width - textWidth(time) - 10, height - 10);
	}

	class Arc {
		float r;
		float b, e, p;
		int w, c;
		float rx, ry, rz;

		Tween rzt, pt;
		float rztd;

		Arc(float r) {
			this.r = r;
			c = color(random(255), random(255), random(255));
			w = (int) random(1, 10);
			rx = random(TWO_PI);
			ry = random(TWO_PI);

			rz = 0;
			rztd = random(25,50);
			rzt = new Tween(rztd).add(this, "rz", TWO_PI).repeat().play();

			b = random(TWO_PI);
			e = b + random(PI / 6, PI / 2);

			p = b;
			pt = new Tween(50).add(this, "p", e).play();
		}

		void draw() {
			colorMode(HSB);
			strokeWeight(w);
			if (isFlashing)
				stroke(0, 0, 360);
			else
				stroke(360 * rzt.getPosition(), 360, 360,
						360 - 360 * rzt.getPosition());
			noFill();
			pushMatrix();
			rotateX(rx);
			rotateY(ry);
			rotateZ(rz);
			arc(0, 0, r, r, b, p);
			popMatrix();
		}
	}

	public void tweenRepeated(Tween t) {

	}

	public void tweenEnded(Tween t) {
		// isFlashing = true;

		t.getNumber("x").setEnd(random(200, width - 200));
		t.getNumber("y").setEnd(random(200, height - 200));
		t.setEasing(Tween.EXPO_BOTH);
		t.play();
	}

	public void keyPressed() {
		setupArcs();
	}

	public void mousePressed() {
		ryt.pause();
	}

	public void mouseReleased() {
		ryt.resume();
	}

	public void mouseDragged() {
		ryt.seek((float) mouseX / width);
	}
}
