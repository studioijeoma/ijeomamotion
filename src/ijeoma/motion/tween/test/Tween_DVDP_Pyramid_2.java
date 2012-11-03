package ijeoma.motion.tween.test;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.ArcProperty;
import ijeoma.motion.tween.Parallel;
import ijeoma.motion.tween.Sequence;
import ijeoma.motion.tween.Timeline;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PVector;

public class Tween_DVDP_Pyramid_2 extends PApplet {
	// http://24.media.tumblr.com/tumblr_maitd7nZSZ1qzt4vjo1_r3_500.gif
	// http://gifctrl.com/Zua

	PFont f;
	Pyramid p1;
	Pyramid p2;

	Sequence ps;

	@Override
	public void setup() {
		size(400, 400, P3D);

		Motion.setup(this);
		setupPyramids();
	}

	public void setupPyramids() {
		float w = 200, h = 400, d = 200, duration = 200;

		p1 = new Pyramid(width / 2, height / 2, w, h, d, duration, 0);
		p2 = new Pyramid(width / 2, height / 2, w, -h, d, duration, duration);

		ps = new Sequence().add(p1.pp).add(p2.pp).repeat().play()
				.setTimeScale(2);
	}

	@Override
	public void draw() {
		background(0);

		pushMatrix();
		rotateY(radians(45));
		rotateX(radians(map(mouseX, 0, width, -45, 45)));
		p1.draw();
		p2.draw();
		popMatrix();
	}

	public void keyPressed() {
		setupPyramids();
	}

	public void mousePressed() {
		ps.pause();
	}

	public void mouseReleased() {
		ps.resume();
	}

	public void mouseDragged() {
		ps.seek(norm(mouseX, 0, width));
	}

	class Pyramid {
		float x, y, w, h, d;
		float a = 255;

		Face f1, f2, f3, f4;

		Sequence fs = new Sequence();
		Parallel pp = new Parallel();

		Tween ty;

		Pyramid(float x, float y, float w, float h, float d, float duration,
				float delay) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.d = d;

			f1 = new Face();
			f1.a = new PVector(0, -h / 4, 0);
			f1.b = new PVector(-w / 2, h / 2 - h / 4, d / 2);
			f1.c = new PVector(-w / 2, h / 2 - h / 4, -d / 2);

			f2 = new Face();
			f2.a = new PVector(0, -h / 4, 0);
			f2.b = new PVector(w / 2, h / 2 - h / 4, -d / 2);
			f2.c = new PVector(-w / 2, h / 2 - h / 4, -d / 2);

			f3 = new Face();
			f3.a = new PVector(0, -h / 4, 0);
			f3.b = new PVector(w / 2, h / 2 - h / 4, -d / 2);
			f3.c = new PVector(w / 2, h / 2 - h / 4, d / 2);

			f4 = new Face();
			f4.a = new PVector(0, -h / 4, 0);
			f4.b = new PVector(-w / 2, h / 2 - h / 4, d / 2);
			f4.c = new PVector(w / 2, h / 2 - h / 4, d / 2);

			float fd = duration / 4;

			// if (h > 0) {
			// fs.add(new Tween(fd).addArc(f1.b,
			// new PVector(f1.b.x, f1.b.y - h)).addArc(f1.c,
			// new PVector(f1.c.x, f1.c.y - h)));
			// fs.add(new Tween(fd).addArc(f2.b,
			// new PVector(f2.b.x, f2.b.y - h)).addArc(f2.c,
			// new PVector(f2.c.x, f2.c.y - h)));
			// fs.add(new Tween(fd).addArc(f3.b,
			// new PVector(f3.b.x, f3.b.y - h)).addArc(f3.c,
			// new PVector(f3.c.x, f3.c.y - h)));
			// fs.add(new Tween(fd).addArc(f4.b,
			// new PVector(f4.b.x, f4.b.y - h)).addArc(f4.c,
			// new PVector(f4.c.x, f4.c.y - h)));
			//
			// } else {
			// fs.add(new Tween(fd).addArc(f1.a, new PVector(f1.a.x, f1.a.y
			// + h)))
			// .add(new Tween(fd).addArc(f2.a, new PVector(f2.a.x,
			// f2.a.y + h)))
			// .add(new Tween(fd).addArc(f3.a, new PVector(f3.a.x,
			// f3.a.y + h)))
			// .add(new Tween(fd).addArc(f4.a, new PVector(f4.a.x,
			// f4.a.y + h)));
			// }

			if (h > 0) {
				fs.add(new Tween(fd).add(f1.b, "y", f1.b.y - h).add(f1.c, "y",
						f1.c.y - h))
						.add(new Tween(fd).add(f2.b, "y", f2.b.y - h).add(f2.c,
								"y", f1.c.y - h))
						.add(new Tween(fd).add(f3.b, "y", f3.b.y - h).add(f3.c,
								"y", f3.c.y - h))
						.add(new Tween(fd).add(f4.b, "y", f4.b.y - h).add(f4.c,
								"y", f4.c.y - h));
			} else {
				fs.add(new Tween(fd).add(f1.a, "y", f1.a.y + h))
						.add(new Tween(fd).add(f2.a, "y", f2.a.y + h))
						.add(new Tween(fd).add(f3.a, "y", f3.a.y + h))
						.add(new Tween(fd).add(f4.a, "y", f4.a.y + h));
			}

			ty = new Tween(duration).add(this, "y", y + abs(h / 2));

			pp.add(fs).add(ty);
		}

		void draw() {
			if (fs.isPlaying() && !fs.isDelaying()) {
				pushMatrix();
				translate(x, y);
				noStroke();
				fill(255, 0, 0, a);
				f1.draw();
				fill(255, 255, 0, a);
				f2.draw();
				fill(0, 255, 255, a);
				f3.draw();
				fill(0, 255, 0, a);
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
