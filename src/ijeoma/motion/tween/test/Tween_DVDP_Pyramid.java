package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Parallel;
import ijeoma.motion.tween.Sequence;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PVector;

public class Tween_DVDP_Pyramid extends PApplet {

	PFont f;
	Pyramid1 p1;
	Pyramid1 p2;
	Sequence ps;

	@Override
	public void setup() {
		size(400, 400, P3D);

		Motion.setup(this);
		setupPyramids();
	}

	public void setupPyramids() {
		float duration = 200;
		p1 = new Pyramid1(width / 2, height / 2, 200, 400, 200, false,
				duration, duration);
		p2 = new Pyramid1(width / 2, height / 2, 200, -400, 200, false,
				duration, duration * 2);
//		ps = new Sequence().add(p1.pp).add(p2.pp).repeat().setTimeScale(2)
//				.play();
	}

	@Override
	public void draw() {
		background(255);

		pushMatrix();
		rotateY(radians(45));
		noFill();
		stroke(0);
		p1.draw();
		p2.draw();
		popMatrix();

		fill(0);
		String time = p1.fs.getTime() + "/" + p1.fs.getDuration();
		text(time, width - textWidth(time) - 25, height - 50);

		time = p2.fs.getTime() + "/" + p2.fs.getDuration();
		text(time, width - textWidth(time) - 25, height - 25);
	}

	public void keyPressed() {
		setupPyramids();
	}

	public void mousePressed() {
		// tl.play();
		ps.pause();
	}

	public void mouseReleased() {
		ps.resume();
	}

	public void mouseDragged() {
		ps.seek(norm(mouseX, 0, width));
	}

	class Pyramid1 {
		float x, y, w, h, d;
		boolean flipped = false;

		Face f1, f2, f3, f4;

		Sequence fs = new Sequence();
		Parallel pp = new Parallel();

		Tween ty;

		Pyramid1(float x, float y, float w, float h, float d, boolean flipped,
				float duration, float delay) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.d = d;

			this.flipped = flipped;

			f1 = new Face();
			f1.a = new PVector(0, -h / 4, 0);
			f1.b = new PVector(-w / 2, h / 2 - h / 4, d / 2);
			f1.c = new PVector(w / 2, h / 2 - h / 4, d / 2);

			f2 = new Face();
			f2.a = new PVector(0, -h / 4, 0);
			f2.b = new PVector(w / 2, h / 2 - h / 4, -d / 2);
			f2.c = new PVector(w / 2, h / 2 - h / 4, d / 2);

			f3 = new Face();
			f3.a = new PVector(0, -h / 4, 0);
			f3.b = new PVector(w / 2, h / 2 - h / 4, -d / 2);
			f3.c = new PVector(-w / 2, h / 2 - h / 4, -d / 2);

			f4 = new Face();
			f4.a = new PVector(0, -h / 4, 0);
			f4.b = new PVector(-w / 2, h / 2 - h / 4, d / 2);
			f4.c = new PVector(-w / 2, h / 2 - h / 4, -d / 2);

			float fd = duration / 4;

			Tween t1, t2, t3, t4;

			if (h > 0) {
				t1 = new Tween(fd).add(f1.b, "y", f1.b.y - h).add(f1.c, "y",
						f1.c.y - h);
				t2 = new Tween(fd).add(f2.b, "y", f2.b.y - h).add(f2.c, "y",
						f1.c.y - h);
				t3 = new Tween(fd).add(f3.b, "y", f3.b.y - h).add(f3.c, "y",
						f3.c.y - h);
				t4 = new Tween(fd).add(f4.b, "y", f4.b.y - h).add(f4.c, "y",
						f4.c.y - h);
			} else {
				t1 = new Tween(fd).add(f1.a, "y", f1.a.y + h);
				t2 = new Tween(fd).add(f2.a, "y", f2.a.y + h);
				t3 = new Tween(fd).add(f3.a, "y", f3.a.y + h);
				t4 = new Tween(fd).add(f4.a, "y", f4.a.y + h);
			}

			fs.add(t1).add(t2).add(t3).add(t4);
			// fs.delay(delay).repeat().repeatDelay().play();

			ty = new Tween(duration).add(this, "y", y + 100).delay(delay)
					.repeat().repeatDelay().play();

			pp.add(fs).add(ty);
		}

		void draw() {
			if (fs.isPlaying() && !fs.isDelaying()) {
				pushMatrix();
				translate(x, y);
				if (flipped)
					rotateX(radians(180));
				fill(255, 0, 0, 100);
				f1.draw();
				fill(255, 255, 0, 100);
				f2.draw();
				fill(0, 255, 255, 100);
				f3.draw();
				fill(0, 255, 0, 100);
				f4.draw();
				popMatrix();
			}
		}
	}

	class Face {
		PVector a, b, c;

		void draw() {
			beginShape(TRIANGLES);
			vertex(a.x, a.y, a.z);
			vertex(b.x, b.y, b.z);
			vertex(c.x, c.y, c.z);
			endShape();
		}
	}
}
