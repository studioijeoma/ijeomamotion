package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.event.MotionEvent;
import ijeoma.motion.event.MotionEventListener;
import ijeoma.motion.tween.Sequence;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PVector;

public class Line_Tween extends PApplet {
	Line[] lines;

	float rx, ry, rz;
	Tween ryt;

	boolean isRotating = true;

	public void setup() {
		size(500, 500, P3D);
		smooth();
		colorMode(HSB);

		Motion.setup(this);

		setupLines();

		ry = 0;
		ryt = new Tween(this, "ry", TWO_PI, 400).repeat().play();
	}

	public void setupLines() {
		lines = new Line[200];

		// for (int i = 0; i < lines.length; i++)
		// lines[i] = new Line(random(500));
		for (int i = 0; i < lines.length; i++)
			lines[i] = new Line(25 + i * 400f / lines.length);
	}

	public void draw() {
		background(0);
		// lights();

		pushMatrix();
		translate(width / 2, height / 2);

		if (isRotating) {
			rotateX(ry);
			rotateY(TWO_PI - ry);
		}

		for (Line l : lines)
			l.draw();

		stroke(0, 0, 360);
		noFill();
		box(400);

		popMatrix();
	}

	class Line implements MotionEventListener {
		float rx, ry, rz;
		Tween ryt;

		float l;
		Tween lt;

		float w;

		float y = 0;

		float s;
		int c;

		boolean wire = false;

		Line(float s) {
			this.s = s;
			y = random(-s, s);

			l = 0;
			lt = new Tween(this, "l", s, random(50, 250))
					.addEventListener(this).setDelay(random(250)).repeat()
					.play();
			lt.setEasing(Tween.CUBIC_BOTH);
			c = color(random(255), random(255), random(255));

			w = 25;

			rx = PI / 2 * (int) random(1, 4);
			ry = PI / 2 * (int) random(1, 4);

			wire = (random(100) > 50) ? true : false;
		}

		void draw() {
			pushMatrix();
			if (isRotating)
				rotateX(rx);
			rotateY(ry);
			translate(-s / 2, y / 2 - w / 2, s / 2);

			// fill(c, 255 - 255 * lt.getPosition());
			// rect(0, 0, l, w);

			// if (wire)
			// stroke(c, 255 - 255 * lt.getPosition());
			// else
			// fill(c, 255 - 255 * lt.getPosition());

			// if (wire)
			// stroke(c);
			// else
			// fill(c);

			colorMode(HSB);
			float h = map((lt.getRepeatCount() % 4) + lt.getPosition(), 0, 3,
					0, 360);
			// float a = 360 - 360 * map(
			// (lt.getRepeatCount() % 4) + lt.getPosition(), 0, 3, 0, 1);
			// float a = map(sin(PI * lt.getPosition()), -1, 1, 360, 0);
			float a = 360 - 360 * lt.getPosition();

			noStroke();
			fill(h, 360, 360, a);

			translate(l / 2, 0, 0);
			box(l, w, w / 10);
			popMatrix();

			// pushMatrix();
			// translate(0, 0, 0);
			// fill(0, 0, 360, (s / 400f) * 1.25f);
			// box(s);
			// popMatrix();
		}

		@Override
		public void onMotionEvent(MotionEvent te) {
			if (te.type == MotionEvent.TWEEN_REPEATED) {
				if (te.getSource().equals(lt)) {
					lt.setDelay(0);
					ry += PI / 2f;
				}
			} else if (te.type == MotionEvent.TWEEN_ENDED) {
				// if (te.getSource().equals(lt))
			}
		}
	}

	public void keyPressed() {
		if (key == 'r')
			isRotating = !isRotating;
		else
			setupLines();
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
